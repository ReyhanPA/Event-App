package com.example.myfundamentalsubmission.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfundamentalsubmission.R
import com.example.myfundamentalsubmission.data.Result
import com.example.myfundamentalsubmission.data.local.entity.EventFinishedEntity
import com.example.myfundamentalsubmission.data.local.entity.EventUpcomingEntity
import com.example.myfundamentalsubmission.databinding.FragmentHomeBinding
import com.example.myfundamentalsubmission.ui.EventViewModelFactory
import com.example.myfundamentalsubmission.util.toListEventsItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapterHomeUpcoming: HomeUpcomingAdapter
    private lateinit var adapterHomeFinished: HomeFinishedAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory: EventViewModelFactory = EventViewModelFactory.getInstance(requireActivity())

        // Home Upcoming Section
        adapterHomeUpcoming = HomeUpcomingAdapter()
        binding?.rvHomeUpcoming?.adapter = adapterHomeUpcoming

        val layoutManagerHomeUpcoming = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding?.rvHomeUpcoming?.layoutManager = layoutManagerHomeUpcoming

        val homeUpcomingViewModel: HomeUpcomingViewModel by viewModels {
            factory
        }

        homeUpcomingViewModel.getAllUpcomingEvents().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val eventData = result.data.take(5)
                        setEventsDataUpcoming(eventData)
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

        binding?.tvUpcomingEvents?.text = getString(R.string.title_upcoming_events)

        // Home Finished Section
        adapterHomeFinished = HomeFinishedAdapter()
        binding?.rvHomeFinished?.adapter = adapterHomeFinished

        val layoutManagerHomeFinished = LinearLayoutManager(requireActivity())
        binding?.rvHomeFinished?.layoutManager = layoutManagerHomeFinished

        val homeFinishedViewModel: HomeFinishedViewModel by viewModels {
            factory
        }

        homeFinishedViewModel.getAllFinishedEvents().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val eventData = result.data.take(5)
                        setEventsDataFinished(eventData)
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

        binding?.tvFinishedEvents?.text = getString(R.string.title_finished_events)
    }

    // Home Upcoming Section Function
    private fun setEventsDataUpcoming(events: List<EventUpcomingEntity>) {
        val eventsList = events.map { it.toListEventsItem() }
        adapterHomeUpcoming.submitList(eventsList)
    }

    // Home Finished Section Function
    private fun setEventsDataFinished(events: List<EventFinishedEntity>) {
        val eventsList = events.map { it.toListEventsItem() }
        adapterHomeFinished.submitList(eventsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}