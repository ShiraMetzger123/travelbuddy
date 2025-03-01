package com.project.travelbuddy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.project.travelbuddy.R
import com.project.travelbuddy.adapter.TravelPostAdapter
import com.project.travelbuddy.databinding.FragmentHomeBinding
import com.project.travelbuddy.viewmodel.TravelViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TravelViewModel
    private lateinit var adapter: TravelPostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        viewModel = ViewModelProvider(this).get(TravelViewModel::class.java)

        viewModel.travelPosts.observe(viewLifecycleOwner) { posts ->
            adapter = TravelPostAdapter(posts){ postId ->
                val bundle = bundleOf("postId" to postId)
                findNavController().navigate(R.id.action_homeFragment_to_postDetailFragment, bundle)
            }
            binding.recyclerView.adapter = adapter
        }

        binding.fabAddPost.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addPostFragment)
        }
    }
}

