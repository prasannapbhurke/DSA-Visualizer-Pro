const { performance } = require('perf_hooks');

const baseUrl = (process.env.LOAD_TEST_BASE_URL || 'https://dsa-visualizer-api-latest.onrender.com').replace(/\/+$/, '');
const totalUsers = Number.parseInt(process.env.LOAD_TEST_USERS || '500', 10);
const concurrency = Number.parseInt(process.env.LOAD_TEST_CONCURRENCY || '50', 10);
const token = process.env.LOAD_TEST_TOKEN || '';
const includeAuthenticated = process.env.LOAD_TEST_AUTHENTICATED === 'true' && token;

const percentile = (values, pct) => {
  if (values.length === 0) {
    return 0;
  }
  const sorted = [...values].sort((left, right) => left - right);
  const index = Math.min(sorted.length - 1, Math.ceil((pct / 100) * sorted.length) - 1);
  return Math.round(sorted[index]);
};

const clientIpFor = (index) => `10.${Math.floor(index / 65000) % 255}.${Math.floor(index / 255) % 255}.${(index % 254) + 1}`;

const requestJson = async (path, options = {}) => {
  const startedAt = performance.now();
  const response = await fetch(`${baseUrl}${path}`, {
    ...options,
    headers: {
      Accept: 'application/json',
      'X-Forwarded-For': options.clientIp || clientIpFor(0),
      ...(options.headers || {}),
    },
  });
  const body = await response.text();
  return {
    path,
    status: response.status,
    ok: response.ok,
    ms: performance.now() - startedAt,
    body: body.slice(0, 200),
  };
};

const runVirtualUser = async (index) => {
  const results = [];
  const clientIp = clientIpFor(index);
  results.push(await requestJson('/status', { clientIp }));

  if (includeAuthenticated) {
    const headers = { Authorization: `Bearer ${token}` };
    results.push(await requestJson('/api/me', { headers, clientIp }));
    results.push(await requestJson('/api/dashboard', { headers, clientIp }));
    results.push(await requestJson('/api/classes', { headers, clientIp }));
  }

  return {
    index,
    results,
    failed: results.filter((result) => !result.ok).length,
  };
};

const main = async () => {
  console.log(`Load test target: ${baseUrl}`);
  console.log(`Virtual users: ${totalUsers}`);
  console.log(`Concurrency: ${concurrency}`);
  console.log(`Authenticated flow: ${includeAuthenticated ? 'yes' : 'no'}`);

  const allResults = [];
  let nextUser = 0;
  const startedAt = performance.now();

  const worker = async () => {
    while (nextUser < totalUsers) {
      const current = nextUser;
      nextUser += 1;
      const result = await runVirtualUser(current);
      allResults.push(...result.results);
    }
  };

  await Promise.all(Array.from({ length: Math.min(concurrency, totalUsers) }, worker));

  const elapsedMs = performance.now() - startedAt;
  const timings = allResults.map((result) => result.ms);
  const failures = allResults.filter((result) => !result.ok);
  const byStatus = allResults.reduce((acc, result) => {
    acc[result.status] = (acc[result.status] || 0) + 1;
    return acc;
  }, {});

  console.log('');
  console.log('Load test result');
  console.log(JSON.stringify({
    baseUrl,
    virtualUsers: totalUsers,
    totalRequests: allResults.length,
    elapsedMs: Math.round(elapsedMs),
    requestsPerSecond: Number((allResults.length / (elapsedMs / 1000)).toFixed(2)),
    statusCounts: byStatus,
    failures: failures.length,
    latencyMs: {
      min: Math.round(Math.min(...timings)),
      p50: percentile(timings, 50),
      p95: percentile(timings, 95),
      p99: percentile(timings, 99),
      max: Math.round(Math.max(...timings)),
    },
  }, null, 2));

  if (failures.length > 0) {
    console.log('');
    console.log('Sample failures');
    for (const failure of failures.slice(0, 5)) {
      console.log(`${failure.status} ${failure.path}: ${failure.body}`);
    }
    process.exitCode = 1;
  }
};

main().catch((error) => {
  console.error(`Load test failed: ${error.message}`);
  process.exitCode = 1;
});
