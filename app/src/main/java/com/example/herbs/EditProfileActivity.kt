package com.example.herbs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.herbs.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        val email = intent.getStringExtra("email")
        val phoneNumber = intent.getStringExtra("phoneNumber")

        binding.etUsername.hint = "Username"
        binding.etEmail.hint = "Email"
        binding.etPhoneNumber.hint = "Phone Number"

        username?.let { binding.etUsername.setText(it) }
        email?.let { binding.etEmail.setText(it) }
        phoneNumber?.let { binding.etPhoneNumber.setText(it) }

        binding.btnSave.setOnClickListener {
            if (validatePasswords()) {
                val updatedUsername = binding.etUsername.text.toString().trim()
                val updatedEmail = binding.etEmail.text.toString().trim()
                val updatedPhoneNumber = binding.etPhoneNumber.text.toString().trim()

                val resultIntent = Intent()
                resultIntent.putExtra("updatedUsername", updatedUsername)
                resultIntent.putExtra("updatedEmail", updatedEmail)
                resultIntent.putExtra("updatedPhoneNumber", updatedPhoneNumber)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun validatePasswords(): Boolean {
        val oldPassword = binding.etOldPassword.text.toString().trim()
        val newPassword = binding.etNewPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        if (newPassword != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords do not match"
            return false
        }

        return true
    }
}
