package com.example.herbs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.herbs.databinding.FragmentAddRecipeBinding

class AddRecipeFragment : Fragment() {

    private var _binding: FragmentAddRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModelFactory(RecipeRepository(RecipeDatabase.getDatabase(requireContext()).recipeDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                Toast.makeText(context, "Please fill in all fields and select an image", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(context, recipe.toString(), Toast.LENGTH_SHORT).show()

            // Reset the EditText fields
            binding.etNamaResep.text.clear()
            binding.etAsalDaerah.text.clear()
            binding.etBahan.text.clear()
            binding.etPenulis.text.clear()
            binding.etDeskripsi.text.clear()
            binding.etLangkahPembuatan.text.clear()
            selectedImageUri = null

            val intent = Intent(activity, SearchRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onGalleryActivityResult(result: androidx.activity.result.ActivityResult) {
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
