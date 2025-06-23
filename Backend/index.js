const express = require('express');
const cors = require('cors');
const notesRoutes = require('./routes/notes');

const app = express();
const PORT = 3000;

app.use(cors());
app.use(express.json());
app.use('/notes', notesRoutes);

app.listen(PORT, () => {
  console.log(`🚀 Server running on http://localhost:${PORT}`);
});
