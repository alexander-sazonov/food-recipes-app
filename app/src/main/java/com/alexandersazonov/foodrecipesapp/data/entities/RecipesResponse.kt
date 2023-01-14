package com.alexandersazonov.foodrecipesapp.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class RecipesResponse(val recipes: List<Recipe>)