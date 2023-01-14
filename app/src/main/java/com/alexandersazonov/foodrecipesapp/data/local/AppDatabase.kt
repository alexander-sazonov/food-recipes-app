package com.alexandersazonov.foodrecipesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexandersazonov.foodrecipesapp.data.entities.FavoriteRecipeEntity

@Database(entities = [FavoriteRecipeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}