package com.alexandersazonov.foodrecipesapp.data.remote

import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesService {
    @GET("recipes/random?number=10")
    suspend fun randomRecipes(): Response<RecipesResponse>

    @GET("recipes/{id}/information")
    suspend fun recipeById(@Path("id") id: Int): Response<Recipe>
}