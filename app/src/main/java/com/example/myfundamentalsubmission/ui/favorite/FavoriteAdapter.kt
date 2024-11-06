package com.example.myfundamentalsubmission.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfundamentalsubmission.data.remote.response.ListEventsItem
import com.example.myfundamentalsubmission.databinding.ItemEventVerticalBinding
import com.example.myfundamentalsubmission.util.DiffUtilCallback

class FavoriteAdapter : ListAdapter<ListEventsItem, FavoriteAdapter.FavoriteViewHolder>(
    DiffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemEventVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    class FavoriteViewHolder(private val binding: ItemEventVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem){
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = event.name
            binding.tvItemSummary.text = event.summary
            binding.root.setOnClickListener {
                val toDetailActivity = FavoriteFragmentDirections.actionNavigationFavoriteToDetailActivity()
                toDetailActivity.name = event.name.toString()
                toDetailActivity.id = event.id ?: 0
                it.findNavController().navigate(toDetailActivity)
            }
        }
    }
}