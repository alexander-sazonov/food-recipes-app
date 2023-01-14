package com.alexandersazonov.foodrecipesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alexandersazonov.foodrecipesapp.data.entities.FavoriteRecipeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipesDao {
    @Insert
    suspend fun insert(favoriteRecipeEntity: FavoriteRecipeEntity)

    @Query("SELECT * FROM favorite_recipes")
    fun getAll(): Flow<List<FavoriteRecipeEntity>>

    @Delete
    suspend fun delete(favoriteRecipeEntity: FavoriteRecipeEntity)
}