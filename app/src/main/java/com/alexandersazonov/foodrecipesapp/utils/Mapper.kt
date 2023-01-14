package com.alexandersazonov.foodrecipesapp.utils

import com.alexandersazonov.foodrecipesapp.data.entities.FavoriteRecipeEntity
import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeStatus
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeWithStatus
import javax.inject.Inject

class Mapper @Inject constructor() {
    fun fromRecipeToRecipeWithStatus(
        recipes: List<Recipe>,
        favoriteRecipes: List<FavoriteRecipeEntity>): List<RecipeWithStatus>{
        val recipesWithStatus = arrayListOf<RecipeWithStatus>()
        val commonElements = recipes.filter {
            it.recipeId in favoriteRecipes.map { favorite -> favorite.id }
        }
        for (recipe in recipes){
            if (recipe in commonElements){
                recipesWithStatus.add(RecipeWithStatus(recipe, RecipeStatus.FAVORITE))
            } else{
                recipesWithStatus.add(RecipeWithStatus(recipe, RecipeStatus.ORDINARY))
            }
        }
        return recipesWithStatus.sortedBy { it.recipe.recipeId }
    }
}