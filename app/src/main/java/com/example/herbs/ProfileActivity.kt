package com.example.herbs

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.herbs.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var username = "firlan"
    private var email = "firlansyah54321@gmail.com"
    private var phoneNumber = "+62 123-4567-8910"
    private var updatedUsername: String? = null
    private var updatedEmail: String? = null
    private var updatedPhoneNumber: String? = null

    private val editProfileLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.let {
                    updatedUsername = it.getStringExtra("updatedUsername")
                    updatedEmail = it.getStringExtra("updatedEmail")
                    updatedPhoneNumber = it.getStringExtra("updatedPhoneNumber")

                    // Update UI with updated values
                    binding.tvUsernameValue.text = updatedUsername ?: username
                    binding.tvEmailValue.text = updatedEmail ?: email
                    binding.tvPhoneNumberValue.text = updatedPhoneNumber ?: phoneNumber
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Update UI with default or updated values
        binding.tvUsernameValue.text = updatedUsername ?: username
        binding.tvEmailValue.text = updatedEmail ?: email
        binding.tvPhoneNumberValue.text = updatedPhoneNumber ?: phoneNumber

        binding.btnEdit.setOnClickListener {
            val editIntent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("username", updatedUsername ?: username)
                putExtra("email", updatedEmail ?: email)
                putExtra("phoneNumber", updatedPhoneNumber ?: phoneNumber)
            }
            editProfileLauncher.launch(editIntent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
