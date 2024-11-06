package com.example.myfundamentalsubmission.util

import androidx.recyclerview.widget.DiffUtil
import com.example.myfundamentalsubmission.data.remote.response.ListEventsItem

object DiffUtilCallback : DiffUtil.ItemCallback<ListEventsItem>() {
    override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
        return oldItem == newItem
    }
}