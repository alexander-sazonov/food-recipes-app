package com.alexandersazonov.foodrecipesapp.ui.popular

import androidx.lifecycle.*
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
class PopularRecipesViewModel @Inject constructor(
    private val recipesRepository: RecipesRepository,
    private val mapper: Mapper
) : ViewModel() {
    private val remoteRecipes = ArrayList<Recipe>()
    private val _recipes = MutableLiveData<List<RecipeWithStatus>>()
    val recipes: LiveData<List<RecipeWithStatus>>
        get() = _recipes
    private val _error = MutableLiveData<Boolean>(false)
    val error: LiveData<Boolean>
        get() = _error
    private val _recipesState = MutableLiveData<RecipesApiState<List<Recipe>>>()
    val recipesState: LiveData<RecipesApiState<List<Recipe>>>
        get() {
            return _recipesState
        }

    private val _loadingState = MutableLiveData<UiLoadingState>()
    val loadingState: LiveData<UiLoadingState>
        get() {
            return _loadingState
        }

    init {
        getRandomRecipes()
    }

    fun getRandomRecipes() {
        _loadingState.value = UiLoadingState.Loading
        viewModelScope.launch {
            _loadingState.value = UiLoadingState.Loading
            val result = recipesRepository.getRandomRecipes()
            _recipesState.value = result
            when (result) {
                is RecipesApiState.Result -> {
                    _error.value = false
                    remoteRecipes.clear()
                    remoteRecipes.addAll(result.data)
                    val recipesFlow = recipesRepository.getAllFavorites()
                    recipesFlow.collect { favoriteRecipes ->
                        _recipes.value =
                            mapper.fromRecipeToRecipeWithStatus(remoteRecipes, favoriteRecipes)
                        _loadingState.value = UiLoadingState.NotLoading
                    }
                }
                is RecipesApiState.Error -> {
                    _loadingState.value = UiLoadingState.NotLoading
                    _recipes.value = emptyList()
                    _error.value = true
                }
            }

        }
    }

    fun addToFavorites(recipe: Recipe) {
        viewModelScope.launch {
            recipesRepository.saveToFavorites(FavoriteRecipeEntity(recipe.recipeId, recipe.title))
        }

    }

    fun deleteFromFavorites(recipe: Recipe) {
        viewModelScope.launch {
            recipesRepository.deleteFromFavorites(
                FavoriteRecipeEntity(
                    recipe.recipeId,
                    recipe.title
                )
            )
        }
    }


}