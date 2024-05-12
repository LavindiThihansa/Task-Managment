package com.example.taskmanagement


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Calendar

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DatbaseName, null, DatabaseVersion) {

    companion object {

        private const val DatbaseName = "Notes.db"
        private const val DatabaseVersion = 1
        private const val TableName = "TaskNotes"
        private const val Colomid = "id"
        private const val ColomnTitle = "Title"
        private const val ColomnNotes = "Notes"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TableName ($Colomid INTEGER PRIMARY KEY, $ColomnTitle TEXT,$ColomnNotes TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TableName")
        onCreate(db)
    }

    fun insertNotes(notes: Note): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ColomnTitle, notes.title)
        values.put(ColomnNotes, notes.note)
        return db.insert(TableName, null, values)
    }

    fun getAllNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TableName", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Colomid))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(ColomnTitle))
                val content = cursor.getString(cursor.getColumnIndexOrThrow(ColomnNotes))
                val note = Note(id, title, content)
                notes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return notes
    }

    fun  updateNote(note: Note){
        val db = writableDatabase
        val Values = ContentValues().apply {
            put(ColomnTitle, note.title)
            put(ColomnNotes, note.note)
        }
        val whereClause = "$Colomid = ?"
        val whereArds = arrayOf(note.id.toString())
        db.update(TableName, Values, whereClause, whereArds)
        db.close()
    }

    fun getNoteById(noteID: Int): Note{
        val db =readableDatabase
        val query = "SELECT * FROM $TableName WHERE $Colomid = $noteID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(Colomid))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(ColomnTitle))
        val contetnt = cursor.getString(cursor.getColumnIndexOrThrow(ColomnNotes))

        cursor.close()
        db.close()
        return Note(id, title, contetnt)
    }

    fun deleteNote(noteID : Int){
        val db = writableDatabase
        val whereCluase = "$Colomid = ?"
        val whereArgs = arrayOf(noteID.toString())
        db.delete(TableName, whereCluase, whereArgs)
        db.close()
    }
}