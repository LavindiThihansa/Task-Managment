package com.example.taskmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.taskmanagement.databinding.ActivityAddTasksBinding

class AddTasks : AppCompatActivity() {

    private lateinit var binding: ActivityAddTasksBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.editTextTitle.text.toString()
            val diaryNote = binding.editTextContent.text.toString()
            val diary = Note(0, title, diaryNote)
            db.insertNotes(diary)
            finish()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }
    }
}