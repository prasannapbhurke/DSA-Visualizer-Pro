const fs = require('fs');
const path = require('path');

const { Pool } = require('pg');

const REQUIRED_ENV = [
  'SUPABASE_URL',
  'SUPABASE_SERVICE_ROLE_KEY',
  'SUPABASE_STORAGE_BUCKET',
];

const ROOT_DIR = path.resolve(__dirname, '..', '..');
const APP_TABLES = [
  'app_users',
  'classes',
  'enrollments',
  'progress_entries',
  'practice_sessions',
  'mistakes',
  'feedback_entries',
  'shared_visualizations',
  'legacy_sessions',
  'app_preferences',
  'teacher_notes',
  'class_resources',
  'audit_logs',
];

const PUBLIC_DB_ROLES = ['anon', 'authenticated'];
const SKIP_DIRS = new Set([
  '.git',
  '.gradle',
  '.gradle-user-home',
  '.idea',
  'build',
  'node_modules',
]);

let failures = 0;
let warnings = 0;

const line = (message = '') => console.log(message);
const pass = (message) => line(`OK ${message}`);
const warn = (message) => {
  warnings += 1;
  line(`WARN ${message}`);
};
const fail = (message) => {
  failures += 1;
  line(`FAIL ${message}`);
};

const trimEnv = (key) => String(process.env[key] || '').trim();

const getSupabaseHeaders = () => ({
  apikey: trimEnv('SUPABASE_SERVICE_ROLE_KEY'),
  Authorization: `Bearer ${trimEnv('SUPABASE_SERVICE_ROLE_KEY')}`,
});

const normalizeSupabaseUrl = () => trimEnv('SUPABASE_URL').replace(/\/+$/, '');

const checkRequiredEnv = () => {
  line('Environment');
  for (const key of REQUIRED_ENV) {
    if (trimEnv(key)) {
      pass(`${key} is set`);
    } else {
      fail(`${key} is missing`);
    }
  }

  const expires = Number(trimEnv('SUPABASE_SIGNED_DOWNLOAD_EXPIRES_IN_SECONDS') || 600);
  if (!Number.isFinite(expires) || expires < 60) {
    fail('SUPABASE_SIGNED_DOWNLOAD_EXPIRES_IN_SECONDS must be at least 60 seconds');
  } else if (expires > 900) {
    warn('signed download expiry is above 900 seconds; prefer 300-900 seconds for class resources');
  } else {
    pass(`signed download expiry is ${expires} seconds`);
  }

  if (!trimEnv('DATABASE_URL')) {
    warn('DATABASE_URL is missing, so database policy checks will be skipped');
  }

  line('');
};

const checkBucketPrivacy = async () => {
  if (REQUIRED_ENV.some((key) => !trimEnv(key))) {
    warn('skipping live bucket check because Supabase env vars are incomplete');
    line('');
    return;
  }

  line('Storage bucket');
  const endpoint = `${normalizeSupabaseUrl()}/storage/v1/bucket/${encodeURIComponent(trimEnv('SUPABASE_STORAGE_BUCKET'))}`;

  try {
    const response = await fetch(endpoint, {
      headers: getSupabaseHeaders(),
    });

    if (!response.ok) {
      const text = await response.text();
      fail(`could not read bucket settings (${response.status}): ${text.slice(0, 180)}`);
      line('');
      return;
    }

    const bucket = await response.json();
    if (bucket.public === true) {
      fail('Supabase class resource bucket is PUBLIC. Turn Public bucket off before production.');
    } else {
      pass('Supabase class resource bucket is private');
    }

    if (bucket.file_size_limit) {
      pass(`bucket file size limit: ${bucket.file_size_limit}`);
    } else {
      warn('bucket file size limit was not returned; verify it manually in Supabase Storage settings');
    }
  } catch (error) {
    fail(`bucket privacy check failed: ${error.message}`);
  }

  line('');
};

const isBroadExpression = (expression) => {
  const text = String(expression || '').toLowerCase();
  return !text || text === 'true' || text === '(true)';
};


const checkAppTableRls = async () => {
  const databaseUrl = trimEnv('DATABASE_URL');
  if (!databaseUrl) {
    return;
  }

  line('App table RLS');
  const pool = new Pool({
    connectionString: databaseUrl,
    ssl: databaseUrl.includes('sslmode=disable') ? false : { rejectUnauthorized: false },
  });

  try {
    const { rows } = await pool.query(
      `select c.relname as table_name,
              c.relrowsecurity as rls_enabled,
              c.relforcerowsecurity as force_rls
       from pg_class c
       join pg_namespace n on n.oid = c.relnamespace
       where n.nspname = 'public'
         and c.relkind = 'r'
         and c.relname = any($1::text[])
       order by c.relname`,
      [APP_TABLES]
    );

    const seen = new Set(rows.map((row) => row.table_name));
    for (const tableName of APP_TABLES) {
      if (!seen.has(tableName)) {
        warn(`app table is missing from database: public.${tableName}`);
      }
    }

    for (const row of rows) {
      if (row.rls_enabled) {
        pass(`RLS enabled on public.${row.table_name}`);
      } else {
        fail(`RLS is disabled on public.${row.table_name}; Supabase REST API can expose this table.`);
      }
    }

    const grants = await pool.query(
      `select table_name,
              grantee,
              privilege_type
       from information_schema.role_table_grants
       where table_schema = 'public'
         and table_name = any($1::text[])
         and grantee = any($2::text[])
       order by table_name, grantee, privilege_type`,
      [APP_TABLES, PUBLIC_DB_ROLES]
    );

    if (grants.rows.length === 0) {
      pass('anon/authenticated have no direct table grants on app tables');
    } else {
      for (const grant of grants.rows) {
        fail(`public API role ${grant.grantee} still has ${grant.privilege_type} on public.${grant.table_name}`);
      }
    }

    const policies = await pool.query(
      `select schemaname, tablename, policyname, cmd, roles, qual, with_check
       from pg_policies
       where schemaname = 'public'
         and tablename = any($1::text[])
       order by tablename, policyname`,
      [APP_TABLES]
    );

    if (policies.rows.length === 0) {
      pass('no direct public table policies exist for app tables; backend remains the authorization gate');
    }

    for (const policy of policies.rows) {
      const roles = Array.isArray(policy.roles) ? policy.roles.map(String) : [];
      const appliesToPublicRole = roles.includes('public') || roles.some((role) => PUBLIC_DB_ROLES.includes(role));
      const broadRead = String(policy.cmd || '').toUpperCase() === 'SELECT' && isBroadExpression(policy.qual);
      const broadWrite = ['INSERT', 'UPDATE', 'DELETE', 'ALL'].includes(String(policy.cmd || '').toUpperCase())
        && (isBroadExpression(policy.qual) || isBroadExpression(policy.with_check));

      if (appliesToPublicRole && (broadRead || broadWrite)) {
        fail(`broad policy ${policy.policyname} exposes public.${policy.tablename} to ${roles.join(', ')}`);
      } else {
        warn(`manual review needed for policy ${policy.policyname} on public.${policy.tablename}`);
      }
    }
  } catch (error) {
    fail(`app table RLS check failed: ${error.message}`);
  } finally {
    await pool.end();
  }

  line('');
};
const checkStoragePolicies = async () => {
  const databaseUrl = trimEnv('DATABASE_URL');
  if (!databaseUrl) {
    return;
  }

  line('Storage RLS policies');
  const pool = new Pool({
    connectionString: databaseUrl,
    ssl: databaseUrl.includes('sslmode=disable') ? false : { rejectUnauthorized: false },
  });

  try {
    const { rows } = await pool.query(`
      select policyname, cmd, roles, qual, with_check
      from pg_policies
      where schemaname = 'storage'
        and tablename = 'objects'
      order by policyname
    `);

    if (rows.length === 0) {
      warn('no storage.objects policies found; signed uploads may fail unless Supabase Storage has another allowed path');
      return;
    }

    let foundInsertForBucket = false;

    for (const policy of rows) {
      const cmd = String(policy.cmd || '').toUpperCase();
      const qual = String(policy.qual || '');
      const withCheck = String(policy.with_check || '');
      const combined = `${qual}\n${withCheck}`.toLowerCase();

      if (combined.includes(trimEnv('SUPABASE_STORAGE_BUCKET').toLowerCase())) {
        pass(`policy references resource bucket: ${policy.policyname}`);
      }

      if (cmd === 'INSERT' && combined.includes(trimEnv('SUPABASE_STORAGE_BUCKET').toLowerCase())) {
        foundInsertForBucket = true;
        if (!withCheck.toLowerCase().includes('bucket_id')) {
          warn(`insert policy ${policy.policyname} does not clearly restrict bucket_id`);
        }
        if (!withCheck.toLowerCase().includes('name')) {
          warn(`insert policy ${policy.policyname} does not restrict object path/name`);
        }
      }

      if (cmd === 'SELECT' && combined.includes(trimEnv('SUPABASE_STORAGE_BUCKET').toLowerCase())) {
        fail(`class-resource bucket has a direct SELECT/read policy: ${policy.policyname}. Use backend signed URLs instead.`);
      }

      if (cmd === 'SELECT' && isBroadExpression(qual)) {
        fail(`broad public-style SELECT policy found on storage.objects: ${policy.policyname}`);
      }
    }

    if (!foundInsertForBucket) {
      warn('no bucket-specific INSERT policy found for class resources; Android direct uploads may hit RLS errors');
    }
  } catch (error) {
    fail(`storage policy check failed: ${error.message}`);
  } finally {
    await pool.end();
  }

  line('');
};

const shouldScanFile = (filePath) => {
  const name = path.basename(filePath).toLowerCase();
  return (
    name.endsWith('.js') ||
    name.endsWith('.json') ||
    name.endsWith('.kt') ||
    name.endsWith('.java') ||
    name.endsWith('.xml') ||
    name.endsWith('.md') ||
    name.endsWith('.properties') ||
    name.startsWith('.env')
  );
};

const scanDirectory = (dir, findings) => {
  const entries = fs.readdirSync(dir, { withFileTypes: true });
  for (const entry of entries) {
    const fullPath = path.join(dir, entry.name);
    if (entry.isDirectory()) {
      if (!SKIP_DIRS.has(entry.name)) {
        scanDirectory(fullPath, findings);
      }
      continue;
    }

    if (!entry.isFile() || !shouldScanFile(fullPath)) {
      continue;
    }

    const text = fs.readFileSync(fullPath, 'utf8');
    if (text.includes('sb_secret_') || text.includes('service_role')) {
      findings.push(path.relative(ROOT_DIR, fullPath));
    }
  }
};

const checkLocalSecretLeaks = () => {
  line('Local secret scan');
  const findings = [];
  scanDirectory(ROOT_DIR, findings);

  const allowedDocs = new Set([
    path.normalize('docs/SUPABASE_SECURITY_AUDIT.md'),
    path.normalize('docs/SUPABASE_STORAGE_RLS_FIX.md'),
    path.normalize('SUPABASE_SETUP_GUIDE.md'),
    path.normalize('server/scripts/check-production-env.js'),
    path.normalize('server/scripts/check-supabase-security.js'),
  ]);

  const unexpected = findings.filter((file) => !allowedDocs.has(path.normalize(file)));
  if (unexpected.length === 0) {
    pass('no obvious Supabase service-role key leak found in scanned app files');
  } else {
    for (const file of unexpected) {
      fail(`possible secret/service-role reference found in ${file}`);
    }
  }

  line('');
};

const main = async () => {
  line('Supabase security check');
  line('');

  checkRequiredEnv();
  checkLocalSecretLeaks();
  await checkBucketPrivacy();
  await checkAppTableRls();
  await checkStoragePolicies();

  if (failures > 0) {
    line(`Security check failed with ${failures} failure(s) and ${warnings} warning(s).`);
    process.exit(1);
  }

  line(`Security check completed with ${warnings} warning(s).`);
};

main().catch((error) => {
  fail(error.stack || error.message);
  process.exit(1);
});



