package com.example.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.android.databinding.ActivityViewNoteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewNoteBinding
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityViewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteId = intent.getIntExtra("note_id", -1)
        if(noteId == -1){
            Toast.makeText(this, "Invalid Note Id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        ApiClient.notesService.getNoteById(noteId).enqueue(object: Callback<Note>{
            override fun onResponse(
                call: Call<Note?>,
                response: Response<Note?>
            ) {
                if(response.isSuccessful){
                    val note = response.body()
                    if(note != null){
                        binding.viewTitleTextView.text = note.title
                        binding.viewContentTextView.text = note.content
                    }
                }else{
                    Toast.makeText(this@ViewNoteActivity, "Note not found", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(
                call: Call<Note?>,
                t: Throwable
            ) {
                Toast.makeText(this@ViewNoteActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                finish()
            }

        })
    }
}