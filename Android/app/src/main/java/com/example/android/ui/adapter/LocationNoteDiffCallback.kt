package com.example.android.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.android.data.model.LocationNote

class LocationNoteDiffCallback : DiffUtil.ItemCallback<LocationNote>() {
    override fun areItemsTheSame(
        oldItem: LocationNote,
        newItem: LocationNote
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: LocationNote,
        newItem: LocationNote
    ): Boolean {
        return oldItem == newItem
    }

}