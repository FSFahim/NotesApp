const express = require('express');
const router = express.Router();
const db = require('../db');

// 🟢 GET all notes
router.get('/', (req, res) => {
  db.query('SELECT * FROM notes', (err, results) => {
    if (err) return res.status(500).json({ error: err });
    res.json(results);
  });
});

// 🟢 GET single note by ID
router.get('/:id', (req, res) => {
  const noteId = req.params.id;
  db.query('SELECT * FROM notes WHERE id = ?', [noteId], (err, results) => {
    if (err) return res.status(500).json({ error: err });
    if (results.length === 0) return res.status(404).json({ error: 'Note not found' });
    res.json(results[0]);
  });
});

// 🟡 CREATE a new note
router.post('/', (req, res) => {
  const { title, content } = req.body;
  db.query('INSERT INTO notes (title, content) VALUES (?, ?)', [title, content], (err, result) => {
    if (err) return res.status(500).json({ error: err });
    res.status(201).json({ message: 'Note added', id: result.insertId });
  });
});

// 🟠 UPDATE a note
router.put('/:id', (req, res) => {
  const noteId = req.params.id;
  const { title, content } = req.body;
  db.query('UPDATE notes SET title = ?, content = ? WHERE id = ?', [title, content, noteId], (err) => {
    if (err) return res.status(500).json({ error: err });
    res.json({ message: 'Note updated' });
  });
});

// 🔴 DELETE a note
router.delete('/:id', (req, res) => {
  const noteId = req.params.id;
  db.query('DELETE FROM notes WHERE id = ?', [noteId], (err) => {
    if (err) return res.status(500).json({ error: err });
    res.json({ message: 'Note deleted' });
  });
});

module.exports = router;
