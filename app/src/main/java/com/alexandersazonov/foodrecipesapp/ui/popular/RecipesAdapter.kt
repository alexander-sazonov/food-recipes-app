package com.alexandersazonov.foodrecipesapp.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexandersazonov.foodrecipesapp.R
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeStatus
import com.alexandersazonov.foodrecipesapp.data.entities.RecipeWithStatus
import com.alexandersazonov.foodrecipesapp.databinding.RecipeItemBinding
import com.squareup.picasso.Picasso

class RecipesAdapter(
    private val onRecipeClick: (RecipeWithStatus) -> Unit,
    private val onFavoriteImageClick: (RecipeWithStatus) -> Unit
) : ListAdapter<RecipeWithStatus, RecipesAdapter.RecipesViewHolder>(RecipesDiffCallback) {

    inner class RecipesViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onRecipeClick(getItem(adapterPosition))
            }
            binding.favoriteImage.setOnClickListener {
                onFavoriteImageClick(getItem(adapterPosition))
            }
        }

        fun bind(recipeWithStatus: RecipeWithStatus) {
            Picasso.get().load(recipeWithStatus.recipe.image).into(binding.recipeImage)
            binding.recipeTitle.text = recipeWithStatus.recipe.title
            when (recipeWithStatus.status) {
                RecipeStatus.FAVORITE -> {
                    binding.favoriteImage.setImageDrawable(
                        ResourcesCompat
                            .getDrawable(
                                binding.root.resources, R.drawable.ic_baseline_favorite_24, null
                            )
                    )
                }
                RecipeStatus.ORDINARY -> {
                    binding.favoriteImage.setImageDrawable(
                        ResourcesCompat
                            .getDrawable(
                                binding.root.resources, R.drawable.ic_baseline_favorite_border_24, null
                            )
                    )
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            RecipeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object RecipesDiffCallback : DiffUtil.ItemCallback<RecipeWithStatus>() {
    override fun areItemsTheSame(oldItem: RecipeWithStatus, newItem: RecipeWithStatus): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RecipeWithStatus, newItem: RecipeWithStatus): Boolean {
        return oldItem.recipe.recipeId == newItem.recipe.recipeId
    }

}