package com.alexandersazonov.foodrecipesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesApiState
import com.alexandersazonov.foodrecipesapp.data.repository.RecipesRepository
import com.alexandersazonov.foodrecipesapp.ui.UiLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(private val recipesRepository: RecipesRepository) :
    ViewModel() {
    private val _recipeState = MutableLiveData<RecipesApiState<Recipe>>()
    val recipeState: LiveData<RecipesApiState<Recipe>>
        get() = _recipeState
    private val _loadingState = MutableLiveData<UiLoadingState>()
    val loadingState: LiveData<UiLoadingState>
        get() = _loadingState

    fun getRecipeById(id: Int) {
        _loadingState.value = UiLoadingState.Loading
        viewModelScope.launch {
            val result = recipesRepository.getRecipeById(id)
                _loadingState.value = UiLoadingState.NotLoading
                _recipeState.value = result

        }
    }
}