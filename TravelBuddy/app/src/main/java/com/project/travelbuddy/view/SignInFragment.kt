package com.project.travelbuddy.view

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.project.travelbuddy.R
import com.project.travelbuddy.databinding.FragmentSignInBinding

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var email: String
    private lateinit var password: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSignInBinding.bind(view)

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.loginBtn.setOnClickListener {
            binding.tilEmail.error = null
            binding.tilPassword.error = null
            validateData()
        }
    }

    private fun validateData() {
        email = binding.tieEmail.text.toString().trim()
        password = binding.tiePassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            binding.tilEmail.error = "Please enter email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Invalid Email format"
            return
        }

        if (TextUtils.isEmpty(password)) {
            binding.tilPassword.error = "Please enter password"
            return
        }

        if (password.length < 6) {
            binding.tilPassword.error = "Password must be at least 6 characters long "
            return
        } else {

        }
    }

}