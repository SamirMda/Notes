package com.example.mvvm.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.database.Note
import com.example.mvvm.model.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdt: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addUptButton: Button
    lateinit var viewModal: NoteViewModel
    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdt = findViewById(R.id.idEditNoteTitle)
        noteDescriptionEdit = findViewById(R.id.idEditNoteDescription)
        addUptButton = findViewById(R.id.idBtnAddUpdate)
        viewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")) {
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)

            noteTitleEdt.setText(noteTitle)
            noteDescriptionEdit.setText(noteDescription)
        }

        addUptButton.setOnClickListener {
            val noteTitle = noteTitleEdt.text.toString()
            val noteDesciption = noteDescriptionEdit.text.toString()

            if (noteType.equals("Edit")) {
                if (noteTitle.isNotEmpty() && noteDesciption.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    val updateNote = Note(noteTitle, noteDesciption, currentDate)
                    updateNote.id = noteID
                    viewModal.updateNote(updateNote)
                    Toast.makeText(this, "Mise à jours effectuée", Toast.LENGTH_LONG).show()
                }
            } else {
                if (noteTitle.isNotEmpty() && noteDesciption.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    viewModal.addNote(Note(noteTitle, noteDesciption, currentDate))
                    Toast.makeText(this, "Note enregistrée", Toast.LENGTH_LONG).show()
                }
            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}