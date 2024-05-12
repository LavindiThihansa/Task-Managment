package com.example.taskmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.taskmanagement.databinding.ActivityUpdateNotesBinding

class UpdateNotes : AppCompatActivity() {


    private lateinit var binding: ActivityUpdateNotesBinding
    private lateinit var db : NotesDatabaseHelper
    private var NotesID: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)

        NotesID = intent.getIntExtra("diary_id", -1)
        if(NotesID == -1){
            finish()
            return
        }

        val diary = db.getNoteById(NotesID)
        binding.editTextTitle.setText(diary.title)
        binding.editTextContent.setText(diary.note)

        binding.saveButton.setOnClickListener{
            val newTitle = binding.editTextTitle.text.toString()
            val newContext = binding.editTextContent.text.toString()
            val updateDiary = Note(NotesID, newTitle,newContext)
            db.updateNote(updateDiary)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}

