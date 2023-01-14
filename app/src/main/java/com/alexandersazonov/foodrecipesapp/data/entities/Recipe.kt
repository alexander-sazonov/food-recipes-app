package com.alexandersazonov.foodrecipesapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("id")
    val recipeId: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val image: String,
    val summary: String,
    val cuisines: List<String>,
    val instructions: String,
    @SerialName("extendedIngredients")
    val ingredients: List<Ingredient>
)