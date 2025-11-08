package com.mehran.assignmentoraxtech.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mehran.assignmentoraxtech.R
import com.mehran.assignmentoraxtech.activities.NoteViewActivity
import com.mehran.assignmentoraxtech.models.Note

class NotesAdapter(private var notes: ArrayList<Note>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_notes_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        holder.textTitle.text = note.title
        holder.textDesc.text = note.description
        holder.itemView.setOnClickListener {
            val openSpecificNote = Intent(holder.itemView.context, NoteViewActivity::class.java)
            openSpecificNote.putExtra("title", note.title)
            openSpecificNote.putExtra("desc", note.description)
            holder.itemView.context.startActivity(openSpecificNote)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val textDesc: TextView = itemView.findViewById(R.id.tvDesc)
    }
}