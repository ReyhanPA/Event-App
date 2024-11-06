package com.example.myfundamentalsubmission.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfundamentalsubmission.data.remote.response.ListEventsItem
import com.example.myfundamentalsubmission.databinding.FragmentFavoriteBinding
import com.example.myfundamentalsubmission.ui.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var adapter: FavoriteAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FavoriteAdapter()
        binding?.rvFavorite?.adapter = adapter

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvFavorite?.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity().application)
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        viewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEvents ->
            if (favoriteEvents != null) {
                val listEvents = favoriteEvents.map { entity ->
                    ListEventsItem(
                        id = entity.id,
                        name = entity.name,
                        imageLogo = entity.imageLogo,
                        summary = entity.summary
                    )
                }
                adapter.submitList(listEvents)
            } else {
                showError("Tidak ada data favorit")
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}