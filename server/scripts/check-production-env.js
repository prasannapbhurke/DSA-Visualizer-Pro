const required = [
  'GOOGLE_CLIENT_ID',
  'DATABASE_URL',
  'SUPABASE_URL',
  'SUPABASE_SERVICE_ROLE_KEY',
  'SUPABASE_STORAGE_BUCKET',
];

const optional = [
  'PORT',
  'PGSSLMODE',
  'UPLOAD_DIR',
];

const missing = required.filter((key) => !String(process.env[key] || '').trim());

console.log('Production environment check');
console.log('');

for (const key of required) {
  const value = String(process.env[key] || '').trim();
  console.log(`${value ? 'OK' : 'MISSING'} ${key}`);
}

for (const key of optional) {
  const value = String(process.env[key] || '').trim();
  if (value) {
    console.log(`OK optional ${key}`);
  }
}

if (missing.length > 0) {
  console.log('');
  console.log(`Missing required env vars: ${missing.join(', ')}`);
  process.exit(1);
}

console.log('');
console.log('All required production env vars are present.');
