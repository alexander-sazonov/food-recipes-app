package com.alexandersazonov.foodrecipesapp.di

import com.alexandersazonov.foodrecipesapp.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsRecipesRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipesRepository

    @Binds
    @Singleton
    abstract fun bindsRecipesRemoteDataSource(recipesRemoteDataSourceImpl: RecipesRemoteDataSourceImpl): RecipesRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsFavoriteRecipesLocalDataSource(recipesLocalDataSource: FavoritesRecipesLocalDataSourceImpl): FavoritesRecipesLocalDataSource
}