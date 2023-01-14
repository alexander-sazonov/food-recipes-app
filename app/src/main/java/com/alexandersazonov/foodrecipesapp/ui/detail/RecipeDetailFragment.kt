package com.alexandersazonov.foodrecipesapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexandersazonov.foodrecipesapp.R
import com.alexandersazonov.foodrecipesapp.data.entities.Recipe
import com.alexandersazonov.foodrecipesapp.data.entities.RecipesApiState
import com.alexandersazonov.foodrecipesapp.databinding.FragmentRecipeDetailBinding
import com.alexandersazonov.foodrecipesapp.ui.UiLoadingState
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates


@AndroidEntryPoint
class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {
    private var snackbar: Snackbar? = null
    private lateinit var binding: FragmentRecipeDetailBinding
    val viewModel: RecipeDetailViewModel by viewModels()
    val args: RecipeDetailFragmentArgs by navArgs()
    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeId = args.recipeId
        binding = FragmentRecipeDetailBinding.bind(view)
        ingredientsAdapter = IngredientsAdapter()
        binding.ingredientsList.layoutManager = LinearLayoutManager(context)
        binding.ingredientsList.adapter = ingredientsAdapter
        viewModel.recipeState.observe(viewLifecycleOwner) {
            when (it) {
                RecipesApiState.Error -> {
                    showError()
                }
                is RecipesApiState.Result -> {
                    val recipe: Recipe = it.data
                    bindViews(recipe)
                }
            }
        }
        viewModel.loadingState.observe(viewLifecycleOwner) { uiLoadingState ->
            handleLoadingState(uiLoadingState)
        }
        viewModel.getRecipeById(recipeId)
    }

    private fun handleLoadingState(uiLoadingState: UiLoadingState) {
        when (uiLoadingState) {
            is UiLoadingState.Loading -> {
                binding.progressBarrDetail.isVisible = true
            }
            is UiLoadingState.NotLoading -> {
                binding.progressBarrDetail.isVisible = false
            }
        }
    }

    private fun showError() {
        snackbar = Snackbar.make(binding.root, "Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                viewModel.getRecipeById(args.recipeId)
            }.apply { show() }
    }

    private fun bindViews(recipe: Recipe){
        with(binding){
            recipeTitle.text = recipe.title
            Picasso.get().load(recipe.image).into(recipeDetailImage)
            ingredientsAdapter.submitList(recipe.ingredients)
            ingredientsAdapter.notifyItemRangeChanged(0, recipe.ingredients.size - 1)
            binding.recipeInstructions.text =HtmlCompat.fromHtml(recipe.instructions, 0)

        }

    }
}