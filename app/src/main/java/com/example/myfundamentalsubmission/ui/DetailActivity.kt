package com.example.myfundamentalsubmission.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.myfundamentalsubmission.R
import com.example.myfundamentalsubmission.data.local.entity.FavoriteEventEntity
import com.example.myfundamentalsubmission.data.remote.response.DetailEvent
import com.example.myfundamentalsubmission.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private lateinit var currentEvent: DetailEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(application)
        val viewModel: DetailActivityViewModel by viewModels {
            factory
        }

        viewModel.findEventById(args.id)

        viewModel.event.observe(this) { event ->
            setEventData(event)
            currentEvent = event
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.errorMessage.observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showError(message)
            }
        }

        viewModel.getFavoriteEventById(args.id.toString()).observe(this) { favorite ->
            if (favorite != null) {
                binding.fab.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.fab.setImageResource(R.drawable.ic_favorite_border)
            }

            binding.fab.setOnClickListener {
                val favoriteEvent = FavoriteEventEntity().apply {
                    id = currentEvent.id
                    name = currentEvent.name
                    imageLogo = currentEvent.imageLogo
                    summary = currentEvent.summary
                }

                if (favorite != null) {
                    viewModel.deleteFavoriteEvent(favoriteEvent)
                } else {
                    lifecycleScope.launch {
                        viewModel.insertFavoriteEvent(favoriteEvent)
                    }
                }
            }
        }
    }

    private fun setEventData(event: DetailEvent) {
        Glide.with(this)
            .load(event.mediaCover)
            .into(binding.imgDetailPhoto)
        binding.tvDetailName.text = event.name
        binding.tvDetailOwner.text = event.ownerName
        binding.tvWaktuAcaraDetail.text = getString(
            R.string.event_time_format,
            event.beginTime,
            event.endTime
        )

        val quota = event.quota
        val registrants = event.registrants
        val remainingQuota = quota - registrants

        binding.tvSisaKuotaDetail.text = getString(
            R.string.event_quota_info_format,
            quota,
            registrants,
            remainingQuota
        )

        binding.tvDeskripsiAcaraDetail.text = Html.fromHtml(
            event.description,
            Html.FROM_HTML_MODE_COMPACT
        )

        binding.actionRegister.setOnClickListener {
            val webPage : Uri = Uri.parse(event.link)
            val intentRegister = Intent(Intent.ACTION_VIEW, webPage)
            startActivity(intentRegister)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}