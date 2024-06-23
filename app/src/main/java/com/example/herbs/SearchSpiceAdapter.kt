package com.example.herbs

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.herbs.databinding.ItemSearchSpiceBinding

class SearchSpiceAdapter :
    RecyclerView.Adapter<SearchSpiceAdapter.SearchSpiceViewHolder>(), Filterable {

    private var clickListener: ClickListener? = null
    private var fullSpiceList: List<Spice> = listOf()

    inner class SearchSpiceViewHolder(val binding: ItemSearchSpiceBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Spice>() {
        override fun areItemsTheSame(oldItem: Spice, newItem: Spice): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Spice, newItem: Spice): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var spices: List<Spice>
        get() = differ.currentList
        set(value) {
            fullSpiceList = value
            differ.submitList(value)
        }

    override fun getItemCount() = spices.size

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSpiceViewHolder {
        val binding = ItemSearchSpiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchSpiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchSpiceViewHolder, position: Int) {
        val spice = spices[position]
        val context = holder.itemView.context

        holder.binding.apply {
            tvName.text = spice.name

            Glide.with(root.context)
                .load(spice.iconImage)
                .fitCenter()
                .into(ivIconSpice)
        }
        holder.itemView.setOnClickListener { clickListener?.onItemClicked(spice) }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val filteredList = if (query.isEmpty()) {
                    fullSpiceList
                } else {
                    fullSpiceList.filter { it.name.lowercase().contains(query) }
                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredSpices = results?.values as List<Spice>
                differ.submitList(filteredSpices)
            }
        }
    }

    interface ClickListener {
        fun onItemClicked(spice: Spice)
    }
}