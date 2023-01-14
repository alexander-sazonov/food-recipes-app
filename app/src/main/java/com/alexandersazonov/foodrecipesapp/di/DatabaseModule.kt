package com.alexandersazonov.foodrecipesapp.di

import android.content.Context
import androidx.room.Room
import com.alexandersazonov.foodrecipesapp.data.local.AppDatabase
import com.alexandersazonov.foodrecipesapp.data.local.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideRecipesDao(appDatabase: AppDatabase): RecipesDao {
        return appDatabase.recipesDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "favorite_recipes"
        ).build()
    }
}