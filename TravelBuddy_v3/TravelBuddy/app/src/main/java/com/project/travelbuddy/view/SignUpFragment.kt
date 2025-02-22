package com.project.travelbuddy.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.project.travelbuddy.R
import com.project.travelbuddy.databinding.FragmentSignUpBinding
import com.project.travelbuddy.util.Constant.validateData

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignUpBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        binding.registerBtn.setOnClickListener {
            binding.tilEmail.error = null
            binding.tilPassword.error = null
            val validatedData = validateData(
                binding.tieEmail,
                binding.tiePassword,
                binding.tilEmail,
                binding.tilPassword
            )
            if (validatedData != null) {
                val (email, password) = validatedData
                signUpUser(email, password)
            }
        }

    }

    private fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                } else {
                    Toast.makeText(requireContext(), "Registration failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}