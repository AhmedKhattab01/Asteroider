package com.example.asteroider.ui.utils.differs

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.entity.neo.Neo

class AsteroidsDiffUtilCallBack : DiffUtil.ItemCallback<Neo>() {
    override fun areItemsTheSame(oldItem: Neo, newItem: Neo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Neo, newItem: Neo): Boolean {
        return oldItem == newItem
    }
}