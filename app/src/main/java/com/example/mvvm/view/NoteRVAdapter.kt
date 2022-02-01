package com.example.mvvm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.database.Note

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {
    private val allNote = ArrayList<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val noteTV = itemView.findViewById<TextView>(R.id.idTVNoteTitle)
        val timeTV = itemView.findViewById<TextView>(R.id.idTVTimeStamp)
        val deleteTV = itemView.findViewById<ImageView>(R.id.idTVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.note_rv_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTV.setText(allNote.get(position).noteTitle)
        holder.timeTV.setText("Dernière mise à jours : " + allNote.get(position).timeStamp)

        holder.deleteTV.setOnClickListener{
            noteClickDeleteInterface.onDeleteIconClick(allNote.get(position))
        }

        holder.itemView.setOnClickListener {
            noteClickInterface.onNoteClick(allNote.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allNote.size
    }

    fun updateList(newList : List<Note>) {
        allNote.clear()
        allNote.addAll(newList)
        notifyDataSetChanged()
    }

}

interface NoteClickDeleteInterface {
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface {
    fun onNoteClick(note: Note)
}