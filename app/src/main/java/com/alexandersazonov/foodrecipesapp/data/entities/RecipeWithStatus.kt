package com.alexandersazonov.foodrecipesapp.data.entities

import kotlinx.serialization.SerialName

data class RecipeWithStatus(
    val recipe: Recipe,
    val status: RecipeStatus
)