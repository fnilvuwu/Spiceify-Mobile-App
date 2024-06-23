package com.example.herbs

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val region: String,
    val ingredients: String,
    val author: String,
    val description: String,
    val steps: String,
    val imageUri: String? = null
)
