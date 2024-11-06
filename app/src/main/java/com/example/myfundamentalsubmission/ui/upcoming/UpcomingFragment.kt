package com.example.myfundamentalsubmission.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfundamentalsubmission.data.Result
import com.example.myfundamentalsubmission.data.local.entity.EventUpcomingEntity
import com.example.myfundamentalsubmission.databinding.FragmentUpcomingBinding
import com.example.myfundamentalsubmission.ui.EventViewModelFactory
import com.example.myfundamentalsubmission.util.toListEventsItem

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private lateinit var adapter: UpcomingAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UpcomingAdapter()
        binding?.rvUpcoming?.adapter = adapter

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvUpcoming?.layoutManager = layoutManager

        val factory: EventViewModelFactory = EventViewModelFactory.getInstance(requireActivity())
        val upcomingViewModel: UpcomingViewModel by viewModels {
            factory
        }

        upcomingViewModel.getAllUpcomingEvents().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val eventData = result.data
                        setEventsData(eventData)
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Gagal memuat data/anda sedang offline " + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setEventsData(events: List<EventUpcomingEntity>) {
        val eventsList = events.map { it.toListEventsItem() }
        adapter.submitList(eventsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}