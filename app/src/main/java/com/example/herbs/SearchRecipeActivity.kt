package com.example.herbs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.herbs.databinding.ActivitySearchRecipeBinding

class SearchRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchRecipeBinding
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView adapter
        adapter = RecipeAdapter()

        // Setup RecyclerView
        binding.rvSearchRecipe.layoutManager = LinearLayoutManager(this)
        binding.rvSearchRecipe.adapter = adapter

        // Initialize ViewModel using ViewModelProvider with ViewModelFactory
        val recipeDao = RecipeDatabase.getDatabase(application).recipeDao() // Replace with your actual Room database instance
        val repository = RecipeRepository(recipeDao)
        val viewModelFactory = RecipeViewModelFactory(repository)
        recipeViewModel = ViewModelProvider(this, viewModelFactory)[RecipeViewModel::class.java]

        // Observe the recipes LiveData from ViewModel
        recipeViewModel.recipes.observe(this) { recipes ->
            recipes?.let {
                adapter.recipes = it // Update RecyclerView adapter with new list of recipes
            }
        }

        // Set up RecyclerView item click listener
        adapter.setClickListener(object : RecipeAdapter.ClickListener {
            override fun onItemClicked(recipe: Recipe) {
                val intent = Intent(this@SearchRecipeActivity, DetailRecipeActivity::class.java).apply {
                    putExtra("recipe_id", recipe.id)
                    putExtra("recipe_name", recipe.name)
                    putExtra("recipe_region", recipe.region)
                    putExtra("recipe_ingredients", recipe.ingredients)
                    putExtra("recipe_author", recipe.author)
                    putExtra("recipe_description", recipe.description)
                    putExtra("recipe_steps", recipe.steps)
                    putExtra("recipe_imageUri", recipe.imageUri)
                    // Add more extras as needed
                }
                startActivity(intent)
            }

            override fun onEditClicked(recipe: Recipe) {
                binding.searchView.setQuery("", false)
                // Launch EditRecipeActivity with recipe details
                val intent = Intent(this@SearchRecipeActivity, EditRecipeActivity::class.java).apply {
                    putExtra("recipe_id", recipe.id)
                    putExtra("recipe_name", recipe.name)
                    putExtra("recipe_region", recipe.region)
                    putExtra("recipe_ingredients", recipe.ingredients)
                    putExtra("recipe_author", recipe.author)
                    putExtra("recipe_description", recipe.description)
                    putExtra("recipe_steps", recipe.steps)
                    putExtra("recipe_imageUri", recipe.imageUri)
                    // Add more extras as needed
                }
                startActivity(intent)
            }

            override fun onDeleteClicked(recipe: Recipe) {
                binding.searchView.setQuery("", false)
                // Handle delete action
                recipeViewModel.delete(recipe)
            }
        })

        binding.fabAddRecipe.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchRecipes(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchRecipes(it)
                }
                return true
            }
        })
    }

    private fun searchRecipes(query: String) {
        recipeViewModel.searchRecipes(query).observe(this) { recipes ->
            recipes?.let {
                adapter.recipes = it // Update RecyclerView adapter with filtered list of recipes
            }
        }
    }
}
