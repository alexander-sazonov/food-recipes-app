package com.alexandersazonov.foodrecipesapp.data.repository

import com.alexandersazonov.foodrecipesapp.data.entities.FavoriteRecipeEntity
import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesApiState
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    suspend fun getRandomRecipes(): RecipesApiState<List<Recipe>>
    suspend fun getRecipeById(id: Int): RecipesApiState<Recipe>
    suspend fun saveToFavorites(recipe: FavoriteRecipeEntity)
    suspend fun getAllFavorites(): Flow<List<FavoriteRecipeEntity>>
    suspend fun deleteFromFavorites(recipe: FavoriteRecipeEntity)
}