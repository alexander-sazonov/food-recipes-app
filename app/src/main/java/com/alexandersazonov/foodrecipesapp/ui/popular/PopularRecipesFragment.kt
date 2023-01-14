package com.alexandersazonov.foodrecipesapp.ui.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandersazonov.foodrecipesapp.R
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeStatus
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeWithStatus
import com.alexandersazonov.foodrecipesapp.databinding.FragmentPopularRecipesBinding
import com.alexandersazonov.foodrecipesapp.ui.UiLoadingState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularRecipesFragment : Fragment(R.layout.fragment_popular_recipes) {

    private final val POPULAR_RECIPES = "Popular_Recipes"

    var adapter: RecipesAdapter? = null
    val viewModel: PopularRecipesViewModel by viewModels()
    private lateinit var binding: FragmentPopularRecipesBinding
    private var snackbar: Snackbar? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipesAdapter = RecipesAdapter (
            { recipe -> navigateToDetails(recipe) },
            {recipe -> changeFavoriteStatus(recipe) }
        )
        this.adapter = recipesAdapter
        binding = FragmentPopularRecipesBinding.bind(view)
        with(binding.popularList) {
            layoutManager = LinearLayoutManager(context)
            this.adapter = recipesAdapter
        }
        viewModel.recipes.observe(viewLifecycleOwner) { list ->
            recipesAdapter.submitList(list)
            recipesAdapter.notifyDataSetChanged()
        }
        viewModel.error.observe(viewLifecycleOwner){
            if (it){
                showError()
            }
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { uiLoadingState ->
            handleLoadingState(uiLoadingState)
        }

    }

    private fun navigateToDetails(recipe: RecipeWithStatus) {
        //Toast.makeText(context,recipe.id.toString(),Toast.LENGTH_SHORT).show()
        findNavController()
            .navigate(
                PopularRecipesFragmentDirections.actionPopularRecipesFragmentToRecipeDetailFragment(
                    recipe.recipe.recipeId
                )
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    private fun handleLoadingState(uiLoadingState: UiLoadingState) {
        when (uiLoadingState) {
            is UiLoadingState.Loading -> {
                binding.progressBar.isVisible = true
            }
            is UiLoadingState.NotLoading -> {
                binding.progressBar.isVisible = false
            }
        }
    }

    private fun showError() {
        snackbar = Snackbar.make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                viewModel.getRandomRecipes()
            }.apply { show() }

    }

    private fun changeFavoriteStatus(recipeWithStatus: RecipeWithStatus){
        when(recipeWithStatus.status){
            RecipeStatus.FAVORITE -> {viewModel.deleteFromFavorites(recipeWithStatus.recipe)}
            RecipeStatus.ORDINARY -> {viewModel.addToFavorites(recipeWithStatus.recipe)}
        }
    }

}