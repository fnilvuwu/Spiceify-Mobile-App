package com.example.herbs

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.herbs.databinding.ActivityDetailRecipeBinding

class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data from the intent
        val recipeId = intent.getLongExtra("recipe_id", -1)
        val recipeName = intent.getStringExtra("recipe_name") ?: ""
        val recipeRegion = intent.getStringExtra("recipe_region") ?: ""
        val recipeIngredients = intent.getStringExtra("recipe_ingredients") ?: ""
        val recipeAuthor = intent.getStringExtra("recipe_author") ?: ""
        val recipeDescription = intent.getStringExtra("recipe_description") ?: ""
        val recipeSteps = intent.getStringExtra("recipe_steps") ?: ""
        selectedImageUri = Uri.parse(intent.getStringExtra("recipe_imageUri") ?: "")

        // Set data to views
        binding.tvNamaResepValue.text = recipeName
        binding.tvAsalDaerahValue.text = recipeRegion
        binding.tvBahanValue.text = recipeIngredients
        binding.tvPenulisValue.text = recipeAuthor
        binding.tvDeskripsiValue.text = recipeDescription
        binding.tvLangkahPembuatanValue.text = recipeSteps
        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.btnAddPhoto)

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
