const { Client } = require('pg');
const fs = require('fs');
const path = require('path');

const connectionString = 'postgresql://postgres:Prasannapb%201%21@db.pgwstcmupssccqiymdla.supabase.co:5432/postgres';

async function initDB() {
  console.log('Connecting to Supabase Direct...');
  const client = new Client({ connectionString });
  
  try {
    await client.connect();
    console.log('Connected!');
    
    const schemaSql = fs.readFileSync(path.join(__dirname, 'postgres_schema.sql'), 'utf8');
    
    console.log('Executing schema...');
    await client.query(schemaSql);
    
    console.log('Schema executed successfully!');
  } catch (error) {
    console.error('Error executing schema:', error.message);
  } finally {
    await client.end();
  }
}

initDB();
