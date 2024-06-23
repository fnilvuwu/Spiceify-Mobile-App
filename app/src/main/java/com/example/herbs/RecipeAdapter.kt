package com.example.herbs

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.herbs.databinding.ItemSearchRecipeBinding

class RecipeAdapter :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var clickListener: ClickListener? = null

    inner class RecipeViewHolder(val binding: ItemSearchRecipeBinding) :
        RecyclerView.ViewHolder(binding.root)

    // DiffUtil callback to compute differences between old and new lists
    private val diffCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id // Adjust this based on your Recipe's identifier
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem // This assumes Recipe has appropriate equals() method
        }
    }

    // AsyncListDiffer to calculate and dispatch list updates efficiently
    private val differ = AsyncListDiffer(this, diffCallback)

    var recipes: List<Recipe>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount(): Int {
        return recipes.size
    }

    // Set click listener for handling item interactions
    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    // Create ViewHolder for RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemSearchRecipeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.binding.apply {
            tvName.text = recipe.name
            // Load image if needed
            Glide.with(root.context)
                .load(Uri.parse(recipe.imageUri))
                .centerCrop()
                .into(ivIconRecipe)

            // Set click listeners
            ivEdit.setOnClickListener { clickListener?.onEditClicked(recipe) }
            ivDelete.setOnClickListener { clickListener?.onDeleteClicked(recipe) }

            // Set item click listener
            root.setOnClickListener { clickListener?.onItemClicked(recipe) }
        }
    }

    // Click listener interface for RecyclerView item interactions
    interface ClickListener {
        fun onItemClicked(recipe: Recipe)
        fun onEditClicked(recipe: Recipe)
        fun onDeleteClicked(recipe: Recipe)
    }
}
