package com.example.mvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.*
import com.example.mvvm.database.Note
import com.example.mvvm.model.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {
    lateinit var notesRV: RecyclerView
    lateinit var addFAB: FloatingActionButton
    lateinit var viewModal: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRV = findViewById(R.id.idRVNotes)
        addFAB = findViewById(R.id.idFABAddNote)
        notesRV.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteRVAdapter(this, this, this)
        notesRV.adapter = noteRVAdapter
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModal.allNotes.observe(this, { list->
            list?.let {
                noteRVAdapter.updateList(list)
            }
        })
        addFAB.setOnClickListener{
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteID", note.id)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        viewModal.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}