const mysql = require('mysql2');

const db = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: 'root',
  database: 'notes_app'
});

db.connect((err) => {
  if (err) throw err;
  console.log('✅ Connected to MySQL');
});

module.exports = db;
