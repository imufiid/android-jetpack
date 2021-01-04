package com.mufiid.dicodingbajp.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mufiid.dicodingbajp.data.source.local.entity.Note
import com.mufiid.dicodingbajp.databinding.ItemNoteBinding
import com.mufiid.dicodingbajp.ui.insert.NoteAddUpdateActivity

class NotePagedListAdapter(private val activity: Activity) :
    PagedListAdapter<Note, NotePagedListAdapter.NoteViewHolder>(
        DIFF_CALLBACK
    ) {
    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> =
            object : DiffUtil.ItemCallback<Note>() {
                override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem.title == newItem.title && oldItem.description == newItem.description
                }

                override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem == newItem
                }

            }
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(activity, NoteAddUpdateActivity::class.java).apply {
                        putExtra(NoteAddUpdateActivity.EXTRA_POSITION, adapterPosition)
                        putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                    }
                    activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position) as Note)
    }
}