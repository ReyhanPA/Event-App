package com.example.myfundamentalsubmission.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfundamentalsubmission.data.remote.response.ListEventsItem
import com.example.myfundamentalsubmission.databinding.ItemEventHorizontalBinding
import com.example.myfundamentalsubmission.util.DiffUtilCallback

class HomeUpcomingAdapter : ListAdapter<ListEventsItem, HomeUpcomingAdapter.HomeUpcomingViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeUpcomingViewHolder {
        val binding = ItemEventHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeUpcomingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeUpcomingViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class HomeUpcomingViewHolder(private val binding: ItemEventHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem){
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = event.name
            binding.tvItemSummary.text = event.summary
            binding.root.setOnClickListener {
                val toDetailActivity = HomeFragmentDirections.actionNavigationHomeToDetailActivity()
                toDetailActivity.name = event.name.toString()
                toDetailActivity.id = event.id ?: 0
                it.findNavController().navigate(toDetailActivity)
            }
        }
    }
}