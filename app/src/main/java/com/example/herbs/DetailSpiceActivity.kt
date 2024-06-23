package com.example.herbs

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.herbs.databinding.ActivityDetailSpiceBinding


class DetailSpiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailSpiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSpiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val name: String? = extras?.getString("name")
        // Find the spice by name
        val spice = DataSource.spices.find { it.name == name }
        if (spice != null) {
            binding.tvNameDetail.text = spice.name
            binding.tvTypeDetail.text = spice.type
            binding.tvDescDetail.text = spice.desc
            binding.ivImageDetail.setImageResource(spice.detailImage) // Set image resource
        } else {
            Toast.makeText(this, "Spice not found", Toast.LENGTH_SHORT).show()
        }


        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()

        binding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}