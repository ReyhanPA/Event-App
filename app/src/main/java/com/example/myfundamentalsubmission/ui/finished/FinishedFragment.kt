package com.example.myfundamentalsubmission.ui.finished

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfundamentalsubmission.R
import com.example.myfundamentalsubmission.data.Result
import com.example.myfundamentalsubmission.data.local.entity.EventFinishedEntity
import com.example.myfundamentalsubmission.databinding.FragmentFinishedBinding
import com.example.myfundamentalsubmission.ui.EventViewModelFactory
import com.example.myfundamentalsubmission.util.toListEventsItem

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private lateinit var adapter: FinishedAdapter
    private lateinit var finishedViewModel: FinishedViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FinishedAdapter()
        binding?.rvFinished?.adapter = adapter

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFinished?.layoutManager = layoutManager


        val factory: EventViewModelFactory = EventViewModelFactory.getInstance(requireActivity())
        finishedViewModel = viewModels<FinishedViewModel> { factory }.value

        showFinishedEvents()

        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.option_menu, menu)

                val searchItem = menu.findItem(R.id.menu1)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnCloseListener {
                    showFinishedEvents()
                    false
                }

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(keyword: String?): Boolean {
                        if (keyword != null) {
                            performSearch(keyword)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText.isNullOrEmpty()) {
                            showFinishedEvents()
                        }
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu1 -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun performSearch(keyword: String) {
        finishedViewModel.searchFinishedEvents(keyword).observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        setEventsData(result.data)
                    }
                    is Result.Error -> {
                        Toast.makeText(
                            context,
                            "Gagal memuat data/anda sedang offline: ${result.error}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun showFinishedEvents() {
        finishedViewModel.getAllFinishedEvents().observe(viewLifecycleOwner) { result ->
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

    private fun setEventsData(events: List<EventFinishedEntity>) {
        val eventsList = events.map { it.toListEventsItem() }
        adapter.submitList(eventsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
