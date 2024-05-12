package com.example.taskmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanagement.databinding.ActivityAddTasksBinding
import com.example.taskmanagement.databinding.ActivityReadTasksBinding

class ReadTasks : AppCompatActivity() {


    private lateinit var binding: ActivityReadTasksBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        binding.recyclerViewDiary.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDiary.adapter = notesAdapter

        binding.Addbutton.setOnClickListener{
            val intent = Intent(this, AddTasks::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload diary notes when the activity is resumed
        notesAdapter.refreshData(db.getAllNotes())
    }

}