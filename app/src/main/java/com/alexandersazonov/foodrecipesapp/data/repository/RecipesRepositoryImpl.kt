package com.alexandersazonov.foodrecipesapp.data.repository

import com.alexandersazonov.foodrecipesapp.data.entities.FavoriteRecipeEntity
import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesApiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val recipesRemoteDataSource: RecipesRemoteDataSource,
    private val favoritesRecipesLocalDataSource: FavoritesRecipesLocalDataSource
) : RecipesRepository {
    override suspend fun getRandomRecipes(): RecipesApiState<List<Recipe>> {
        return recipesRemoteDataSource.getRandomRecipes()
    }

    override suspend fun getRecipeById(id: Int): RecipesApiState<Recipe> {
        return recipesRemoteDataSource.getRecipeById(id)
    }

    override suspend fun saveToFavorites(recipe: FavoriteRecipeEntity) {
        favoritesRecipesLocalDataSource.saveToFavorites(recipe)
    }

    override suspend fun getAllFavorites(): Flow<List<FavoriteRecipeEntity>> {
        return favoritesRecipesLocalDataSource.getAllFavorites()
    }

    override suspend fun deleteFromFavorites(recipe: FavoriteRecipeEntity) {
        favoritesRecipesLocalDataSource.deleteFromFavorites(recipe)
    }

}