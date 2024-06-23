package com.example.herbs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.herbs.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModelFactory(RecipeRepository(RecipeDatabase.getDatabase(application).recipeDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                onGalleryActivityResult(result)
            }

        binding.btnAddPhoto.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch(galleryIntent)
        }

        binding.btnSaveRecipe.setOnClickListener {
            val name = binding.etNamaResep.text.toString().trim()
            val region = binding.etAsalDaerah.text.toString().trim()
            val ingredients = binding.etBahan.text.toString().trim()
            val author = binding.etPenulis.text.toString().trim()
            val description = binding.etDeskripsi.text.toString().trim()
            val steps = binding.etLangkahPembuatan.text.toString().trim()

            // Check if all EditText fields are not empty and an image is selected
            if (name.isEmpty() || region.isEmpty() || ingredients.isEmpty() ||
                author.isEmpty() || description.isEmpty() || steps.isEmpty() ||
                selectedImageUri == null
            ) {
                Toast.makeText(this, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val recipe = Recipe(
                name = binding.etNamaResep.text.toString(),
                region = binding.etAsalDaerah.text.toString(),
                ingredients = binding.etBahan.text.toString(),
                author = binding.etPenulis.text.toString(),
                description = binding.etDeskripsi.text.toString(),
                steps = binding.etLangkahPembuatan.text.toString(),
                imageUri = selectedImageUri?.toString()
            )

            viewModel.insert(recipe)
            Toast.makeText(this, "Recipe saved!", Toast.LENGTH_SHORT).show()

            // Reset the EditText fields
            binding.etNamaResep.text.clear()
            binding.etAsalDaerah.text.clear()
            binding.etBahan.text.clear()
            binding.etPenulis.text.clear()
            binding.etDeskripsi.text.clear()
            binding.etLangkahPembuatan.text.clear()
            selectedImageUri = null

            val intent = Intent(this, SearchRecipeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun onGalleryActivityResult(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            if (data != null && data.data != null) {
                selectedImageUri = data.data
                Glide.with(this)
                    .load(selectedImageUri)
                    .fitCenter()
                    .into(binding.btnAddPhoto)
            }
        }
    }
}
