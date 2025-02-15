package com.project.travelbuddy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.project.travelbuddy.R
import com.project.travelbuddy.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
        }
    }
}
