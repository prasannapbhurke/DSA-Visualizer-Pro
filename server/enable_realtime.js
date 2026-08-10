
const { Client } = require('pg');

async function enableRealtime() {
  const client = new Client({
    connectionString: process.env.DATABASE_URL,
  });

  try {
    await client.connect();
    console.log('Connected to database.');

    const tables = ['users', 'classes', 'progress_entries', 'practice_sessions', 'feedback_entries'];

    for (const table of tables) {
      try {
        await client.query(`ALTER PUBLICATION supabase_realtime ADD TABLE ${table};`);
        console.log(`Enabled realtime for table: ${table}`);
      } catch (err) {
        if (err.code === '42704') {
          console.warn(`Publication supabase_realtime does not exist!`);
        } else if (err.message.includes('already in publication')) {
          console.log(`Table ${table} is already in realtime publication.`);
        } else {
          console.error(`Error enabling realtime for ${table}:`, err.message);
        }
      }
    }

    console.log('Realtime setup complete.');
  } catch (err) {
    console.error('Connection error:', err);
  } finally {
    await client.end();
  }
}

enableRealtime();
