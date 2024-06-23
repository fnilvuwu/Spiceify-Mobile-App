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
import com.example.herbs.databinding.ActivityEditRecipeBinding

class EditRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRecipeBinding
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModelFactory(RecipeRepository(RecipeDatabase.getDatabase(application).recipeDao()))
    }
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditRecipeBinding.inflate(layoutInflater)
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

        // Set initial data to edit
        val recipeId = intent.getLongExtra("recipe_id", -1)
        val recipeName = intent.getStringExtra("recipe_name") ?: ""
        val recipeRegion = intent.getStringExtra("recipe_region") ?: ""
        val recipeIngredients = intent.getStringExtra("recipe_ingredients") ?: ""
        val recipeAuthor = intent.getStringExtra("recipe_author") ?: ""
        val recipeDescription = intent.getStringExtra("recipe_description") ?: ""
        val recipeSteps = intent.getStringExtra("recipe_steps") ?: ""
        selectedImageUri = Uri.parse(intent.getStringExtra("recipe_imageUri") ?: "")

        binding.etNamaResep.setText(recipeName)
        binding.etAsalDaerah.setText(recipeRegion)
        binding.etBahan.setText(recipeIngredients)
        binding.etPenulis.setText(recipeAuthor)
        binding.etDeskripsi.setText(recipeDescription)
        binding.etLangkahPembuatan.setText(recipeSteps)

        Glide.with(this)
            .load(selectedImageUri)
            .fitCenter()
            .into(binding.btnAddPhoto)

        // Back button click listener
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Save button click listener
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

            val editedRecipe = Recipe(
                id = recipeId,
                name = binding.etNamaResep.text.toString(),
                region = binding.etAsalDaerah.text.toString(),
                ingredients = binding.etBahan.text.toString(),
                author = binding.etPenulis.text.toString(),
                description = binding.etDeskripsi.text.toString(),
                steps = binding.etLangkahPembuatan.text.toString(),
                imageUri = selectedImageUri?.toString()
            )

            viewModel.update(editedRecipe)
            Toast.makeText(this, "Recipe updated!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SearchRecipeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
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
