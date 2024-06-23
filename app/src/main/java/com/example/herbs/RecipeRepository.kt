package com.example.herbs

import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val recipeDao: RecipeDao) {

    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }

    fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }

    suspend fun delete(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    suspend fun update(recipe: Recipe) {
        recipeDao.update(recipe)
    }

    fun searchRecipes(query: String): Flow<List<Recipe>> {
        return recipeDao.searchRecipes("%$query%")
    }
}
