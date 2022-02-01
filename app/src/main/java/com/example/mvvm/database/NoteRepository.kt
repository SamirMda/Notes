package com.example.mvvm.database

import androidx.lifecycle.LiveData
import com.example.mvvm.database.Note
import com.example.mvvm.database.NoteDao

class NoteRepository(private val notesDao: NoteDao) {
    val allNotes: LiveData<List<Note>> = notesDao.getAll()

    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    suspend fun delete(note: Note) {
        notesDao.delete(note)
    }

    suspend fun update(note: Note) {
        notesDao.update(note)
    }
}