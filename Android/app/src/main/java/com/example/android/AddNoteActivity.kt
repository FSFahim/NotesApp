package com.example.android

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.android.databinding.ActivityAddNoteBinding
import com.example.android.model.Note
import retrofit2.Callback

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    //private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            if(title.isBlank() || content.isBlank()){
                Toast.makeText(this, "Please enter title and content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //val note = Note(0, title, content)
            //db.insertNote(note)
            //finish()
            //Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()

            val note = Note(title = title, content = content)

            ApiClient.notesService.addNote(note).enqueue(object : Callback<Void>{
                override fun onResponse(
                    call: retrofit2.Call<Void?>,
                    response: retrofit2.Response<Void?>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(this@AddNoteActivity, "Note Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this@AddNoteActivity, "Save Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: retrofit2.Call<Void?>, t: Throwable) {
                    Toast.makeText(this@AddNoteActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}