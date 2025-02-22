package com.project.travelbuddy.util

import android.text.TextUtils
import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

object Constant {

    fun validateData(
        tieEmail: TextInputEditText,
        tiePassword: TextInputEditText,
        tilEmail: TextInputLayout,
        tilPassword: TextInputLayout
    ): Pair<String, String>? {
        val email = tieEmail.text.toString().trim()
        val password = tiePassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            tilEmail.error = "Please enter email"
            return null
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.error = "Invalid Email format"
            return null
        }

        if (TextUtils.isEmpty(password)) {
            tilPassword.error = "Please enter password"
            return null
        }

        if (password.length < 6) {
            tilPassword.error = "Password must be at least 6 characters long "
            return null
        }

        return Pair(email, password)

    }
}