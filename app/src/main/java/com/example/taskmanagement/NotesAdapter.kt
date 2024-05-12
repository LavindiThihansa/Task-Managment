package com.example.taskmanagement


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val noteTextView: TextView = itemView.findViewById(R.id.textViewContent)
        val editButton: ImageView = itemView.findViewById(R.id.updateIcon)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notesview, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentDiary = notes[position]
        holder.titleTextView.text = currentDiary.title
        holder.noteTextView.text = currentDiary.note

        holder.editButton.setOnClickListener{
            val intent =Intent(holder.itemView.context, UpdateNotes::class.java).apply {
                putExtra("diary_id", currentDiary.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNote(currentDiary.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun refreshData(newnote : List<Note>){
        notes = newnote
        notifyDataSetChanged()

    }
}