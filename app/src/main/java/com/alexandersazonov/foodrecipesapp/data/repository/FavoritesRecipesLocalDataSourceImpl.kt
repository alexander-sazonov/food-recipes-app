package com.alexandersazonov.foodrecipesapp.data.repository

import com.alexandersazonov.foodrecipesapp.data.entities.FavoriteRecipeEntity
import com.alexandersazonov.foodrecipesapp.data.local.RecipesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRecipesLocalDataSourceImpl @Inject constructor(private val recipesDao: RecipesDao) :
    FavoritesRecipesLocalDataSource {
    override suspend fun saveToFavorites(favoriteRecipeEntity: FavoriteRecipeEntity) {
        recipesDao.insert(favoriteRecipeEntity)
    }

    override suspend fun getAllFavorites(): Flow<List<FavoriteRecipeEntity>> {
        return recipesDao.getAll()
    }

    override suspend fun deleteFromFavorites(favoriteRecipeEntity: FavoriteRecipeEntity) {
        recipesDao.delete(favoriteRecipeEntity)
    }
}