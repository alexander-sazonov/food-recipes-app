package com.alexandersazonov.foodrecipesapp.ui

sealed class UiLoadingState{
    object Loading: UiLoadingState()
    object NotLoading: UiLoadingState()
}