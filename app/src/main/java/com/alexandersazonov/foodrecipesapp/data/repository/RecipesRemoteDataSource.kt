package com.alexandersazonov.foodrecipesapp.data.repository

import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesApiState
import kotlinx.coroutines.flow.Flow

interface RecipesRemoteDataSource {
    suspend fun getRandomRecipes(): RecipesApiState<List<Recipe>>
    suspend fun getRecipeById(id: Int): RecipesApiState<Recipe>
}