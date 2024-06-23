package com.example.herbs


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.herbs.databinding.ItemSpiceBinding

class SpiceAdapter :
    RecyclerView.Adapter<SpiceAdapter.SpiceViewHolder>() {

    private var clickListener: ClickListener? = null

    inner class SpiceViewHolder(val binding: ItemSpiceBinding) :
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
            differ.submitList(value)
        }

    override fun getItemCount() = spices.size

    fun setClickListener(clickListener: ClickListener?) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpiceViewHolder {
        val binding = ItemSpiceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SpiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpiceViewHolder, position: Int) {
        val spice = spices[position]
        val context = holder.itemView.context

        holder.binding.apply {
            tvName.text = spice.name
            tvType.text = spice.type

            Glide.with(root.context)
                .load(spice.iconImage)
                .fitCenter()
                .into(ivIconSpice)
        }
        holder.itemView.setOnClickListener { clickListener?.onItemClicked(spice) }
    }

    interface ClickListener {
        fun onItemClicked(spice: Spice)
    }
}