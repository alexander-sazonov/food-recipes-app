package com.alexandersazonov.foodrecipesapp.data.entities

sealed class RecipesApiState<out T> {
    object Error: RecipesApiState<Nothing>()
    data class Result<out T>(val data: T): RecipesApiState<T>()
}