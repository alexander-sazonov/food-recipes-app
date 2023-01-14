package com.alexandersazonov.foodrecipesapp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandersazonov.foodrecipesapp.R
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeStatus
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeWithStatus
import com.alexandersazonov.foodrecipesapp.databinding.FragmentFavoritesBinding
import com.alexandersazonov.foodrecipesapp.ui.UiLoadingState
import com.alexandersazonov.foodrecipesapp.ui.popular.PopularRecipesFragmentDirections
import com.alexandersazonov.foodrecipesapp.ui.popular.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    val viewModel: FavoritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RecipesAdapter(
            { recipe -> navigateToDetail(recipe) },
            { recipe ->
                changeFavoriteStatus(recipe)
                viewModel.getFavoriteRecipes()
            }
        )
        val recipesList = ArrayList<RecipeWithStatus>()
        binding = FragmentFavoritesBinding.bind(view)
        with(binding) {
            favoritesList.layoutManager = LinearLayoutManager(context)
            favoritesList.adapter = adapter
        }


        viewModel.favoriteRecipes.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            adapter.notifyDataSetChanged()
        }
        viewModel.uiStatus.observe(viewLifecycleOwner) {
            when (it) {
                is UiLoadingState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is UiLoadingState.NotLoading -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun navigateToDetail(recipeWithStatus: RecipeWithStatus) {
        findNavController()
            .navigate(
                FavoritesFragmentDirections
                    .actionFavoritesFragmentToRecipeDetailFragment(recipeWithStatus.recipe.recipeId)
            )
    }

    private fun changeFavoriteStatus(recipeWithStatus: RecipeWithStatus) {
        when (recipeWithStatus.status) {
            RecipeStatus.FAVORITE -> {
                viewModel.deleteFromFavorites(recipeWithStatus.recipe)
            }
            RecipeStatus.ORDINARY -> {
                viewModel.addToFavorites(recipeWithStatus.recipe)
            }
        }
    }

}