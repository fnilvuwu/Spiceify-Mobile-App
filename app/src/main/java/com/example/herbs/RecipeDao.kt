package com.example.herbs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes WHERE name LIKE :query OR region LIKE :query")
    fun searchRecipes(query: String): Flow<List<Recipe>>

    @Insert
    suspend fun insert(recipe: Recipe)

    @Query("SELECT * FROM recipes ORDER BY name ASC")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Delete
    suspend fun delete(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)
}
