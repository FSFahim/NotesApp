package com.example.android.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.android.domain.model.Note

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(
        oldItem: Note,
        newItem: Note
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Note,
        newItem: Note
    ): Boolean {
        return oldItem == newItem
    }

}