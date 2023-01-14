package com.alexandersazonov.foodrecipesapp.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexandersazonov.foodrecipesapp.data.entities.Ingredient
import com.alexandersazonov.foodrecipesapp.databinding.IngredientItemBinding
import com.squareup.picasso.Picasso

class IngredientsAdapter :
    ListAdapter<Ingredient, IngredientsAdapter.IngredientsViewHolder>(IngredientsDiffCallback) {
    inner class IngredientsViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            Picasso.get().load(ingredient.imageUrl).into(binding.ingredientImage)
            binding.ingredientName.text = ingredient.name
            binding.ingredientAmount.text = "${ingredient.amount} ${ingredient.unit}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            IngredientItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object IngredientsDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.ingredientId == newItem.ingredientId
    }


}
