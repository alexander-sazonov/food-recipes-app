package com.alexandersazonov.foodrecipesapp.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandersazonov.foodrecipesapp.data.entities.FavoriteRecipeEntity
import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeWithStatus
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesApiState
import com.alexandersazonov.foodrecipesapp.data.repository.RecipesRepository
import com.alexandersazonov.foodrecipesapp.ui.UiLoadingState
import com.alexandersazonov.foodrecipesapp.utils.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    private val mapper: Mapper
) : ViewModel() {


    private val _favoriteRecipes = MutableLiveData<List<RecipeWithStatus>>();
    val favoriteRecipes: LiveData<List<RecipeWithStatus>>
        get() = _favoriteRecipes
    private val _uiState = MutableLiveData<UiLoadingState>()
    val uiStatus: LiveData<UiLoadingState>
        get() = _uiState
    private val remoteRecipes = ArrayList<Recipe>()

    init {
        getFavoriteRecipes()
    }

    fun getFavoriteRecipes() {

        viewModelScope.launch {
            _uiState.value = UiLoadingState.Loading
            recipesRepository.getAllFavorites().collect { list ->
                remoteRecipes.clear()
                for (elem in list){
                    val result = recipesRepository.getRecipeById(elem.id)
                    if (result is RecipesApiState.Result){
                        remoteRecipes.add(result.data)
                    }
                }
                _favoriteRecipes.value = mapper.fromRecipeToRecipeWithStatus(remoteRecipes, list)
                _uiState.value = UiLoadingState.NotLoading

            }
        }
    }

    fun addToFavorites(recipe: Recipe) {
        viewModelScope.launch {
            with(recipe) {
                recipesRepository.saveToFavorites(FavoriteRecipeEntity(recipeId, title))
            }

        }
    }

    fun deleteFromFavorites(recipe: Recipe) {
        viewModelScope.launch {
            with(recipe) {
                recipesRepository.deleteFromFavorites(FavoriteRecipeEntity(recipeId, title))
            }

        }
    }
}