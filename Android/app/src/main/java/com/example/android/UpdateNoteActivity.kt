package com.example.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.android.databinding.ActivityUpdateNoteBinding
import com.example.android.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    //private lateinit var db: NotesDatabaseHelper
    private var noteId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        //db = NotesDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if(noteId == -1){
            Toast.makeText(this, "Invalid Note ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        //val note = db.getNoteById(noteId)
        //binding.updateTitleEditText.setText(note.title)
        //binding.updateContentEditText.setText(note.content)

        ApiClient.notesService.getNoteById(noteId).enqueue(object : Callback<Note>{
            override fun onResponse(
                call: Call<Note?>,
                response: Response<Note?>
            ) {
                if(response.isSuccessful){
                    val note = response.body()
                    if(note != null){
                        binding.updateTitleEditText.setText(note.title)
                        binding.updateContentEditText.setText(note.content)
                    }
                }else{
                    Toast.makeText(this@UpdateNoteActivity, "Failed to load note", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(
                call: Call<Note?>,
                t: Throwable
            ) {
                Toast.makeText(this@UpdateNoteActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                finish()
            }

        })

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()

            if (newTitle.isBlank() || newContent.isBlank()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedNote = Note(noteId, newTitle, newContent)
            //db.updateNote(updatedNote)
            //finish()
            //Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            ApiClient.notesService.updateNote(noteId, updatedNote).enqueue(object: Callback<Void>{
                override fun onResponse(
                    call: Call<Void?>,
                    response: Response<Void?>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(this@UpdateNoteActivity, "Changes Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this@UpdateNoteActivity, "Update failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(this@UpdateNoteActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

}