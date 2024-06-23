package com.example.herbs

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    val recipes: LiveData<List<Recipe>> = repository.getAllRecipes()
        .catch { _ ->
            // Handle exceptions if any
            // For example, log the error or show a toast
        }
        .asLiveData(viewModelScope.coroutineContext)

    fun insert(recipe: Recipe) {
        viewModelScope.launch {
            repository.insert(recipe)
        }
    }

    fun delete(recipe: Recipe) {
        viewModelScope.launch {
            repository.delete(recipe)
        }
    }

    fun update(recipe: Recipe) {
        viewModelScope.launch {
            repository.update(recipe)
        }
    }

    fun searchRecipes(query: String): LiveData<List<Recipe>> {
        return repository.searchRecipes(query).asLiveData()
    }
}

class RecipeViewModelFactory(private val repository: RecipeRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
