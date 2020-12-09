package com.beshoy.myapplication.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beshoy.myapplication.databinding.ItemStepperBinding

class StepperAdapter : ListAdapter<StepperUiModel, StepperAdapter.ViewHolder>(DiffCallback()) {

    private var _itemWidth: Int = 0
    val itemWidth: Int get() = _itemWidth

    fun setItemWidth(width: Int, changedItemsCount: Int) {
        _itemWidth = width
        notifyItemRangeChanged(0, changedItemsCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, itemWidth)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemStepperBinding, private val itemWidth: Int) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StepperUiModel) {
            binding.item = item
            binding.root.updateLayoutParams {
                width = itemWidth
            }
        }

        companion object {
            fun from(parent: ViewGroup, width: Int): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStepperBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding, width)
            }
        }

    }
}

class DiffCallback : DiffUtil.ItemCallback<StepperUiModel>() {

    override fun areItemsTheSame(
        old: StepperUiModel,
        aNew: StepperUiModel
    ): Boolean {
        return old === aNew
    }

    override fun areContentsTheSame(
        old: StepperUiModel,
        aNew: StepperUiModel
    ): Boolean {
        return old == aNew
    }

}