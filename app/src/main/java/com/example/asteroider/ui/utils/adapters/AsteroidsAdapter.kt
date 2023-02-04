package com.example.asteroider.ui.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroider.R
import com.example.asteroider.databinding.ItemRvAsteroidsBinding
import com.example.asteroider.ui.screens.home.HomeFragmentDirections
import com.example.asteroider.ui.screens.neo.NeoFragmentDirections
import com.example.asteroider.ui.utils.differs.AsteroidsDiffUtilCallBack
import com.example.domain.entity.neo.Neo

class AsteroidsAdapter :
    ListAdapter<Neo, AsteroidsAdapter.AsteroidsViewHolder>(AsteroidsDiffUtilCallBack()) {
    inner class AsteroidsViewHolder(private val binding: ItemRvAsteroidsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // bind neo with received item
        fun bind(item: Neo) {
            binding.neo = item

            // set icon depends on hazardous value
            if (item.isHazardous) {
                binding.imageView.setImageResource(R.drawable.hot_asteroid)
            }
            else {
                binding.imageView.setImageResource(R.drawable.asteroid)
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidsViewHolder {
        return AsteroidsViewHolder(
            ItemRvAsteroidsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AsteroidsViewHolder, position: Int) {
        // call bind function and pass current item
        holder.bind(getItem(position))

        // on item click listener
        // check the destination the item called from
        // if its home fragment it will use home fragment directions to navigate
        // else it will use neo fragment directions
        holder.itemView.setOnClickListener {
            if (it.findNavController().currentDestination?.label == "Astroider") {
                it.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToNeoDetailsFragment(
                        getItem(position)
                    )
                )
            }
            else {
                it.findNavController().navigate(
                    NeoFragmentDirections.actionNeoFragment2ToNeoFragment(getItem(position))
                )
            }
        }
    }
}