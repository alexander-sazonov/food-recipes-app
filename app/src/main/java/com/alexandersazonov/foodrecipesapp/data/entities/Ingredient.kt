package com.alexandersazonov.foodrecipesapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("id")
    val ingredientId: Int,
    val image: String,
    val name: String,
    val amount: Double,
    val unit: String
) {
    val imageUrl: String
        get() = "https://spoonacular.com/cdn/ingredients_100x100/$image"
}