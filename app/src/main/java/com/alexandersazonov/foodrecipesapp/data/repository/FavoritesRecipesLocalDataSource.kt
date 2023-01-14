package com.alexandersazonov.foodrecipesapp.data.repository

import com.alexandersazonov.foodrecipesapp.data.entities.FavoriteRecipeEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRecipesLocalDataSource {
    suspend fun saveToFavorites(favoriteRecipeEntity: FavoriteRecipeEntity)
    suspend fun getAllFavorites(): Flow<List<FavoriteRecipeEntity>>
    suspend fun deleteFromFavorites(favoriteRecipeEntity: FavoriteRecipeEntity)
}