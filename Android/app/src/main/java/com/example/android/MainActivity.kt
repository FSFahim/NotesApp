package com.example.android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesAdapter = NotesAdapter(listOf(), this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        loadNotes()
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes(){
        ApiClient.notesService.getAllNotes().enqueue(object: Callback<List<Note>>{
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>){
                if(response.isSuccessful){
                    val notes = response.body() ?: emptyList()
                    notesAdapter.refreshData(notes)
                }else{
                    Toast.makeText(this@MainActivity, "Failed to load notes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<List<Note>?>,
                t: Throwable
            ) {
                Toast.makeText(this@MainActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

