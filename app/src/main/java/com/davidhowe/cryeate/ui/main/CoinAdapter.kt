package com.davidhowe.cryeate.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davidhowe.cryeate.databinding.RowItemMainListBinding
import com.davidhowe.cryeate.models.db.Coin
import timber.log.Timber

class CoinAdapter : ListAdapter<Coin, RecyclerView.ViewHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Timber.d("onCreateViewHolder")
        return CoinViewHolder(
                RowItemMainListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Timber.d("onBindViewHolder")
        val coin = getItem(position)
        (holder as CoinViewHolder).bind(coin)
    }

    class CoinViewHolder(
            private val binding: RowItemMainListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.coin?.let { plant ->
                    navigateToCoin(plant, it)
                }
            }
        }

        private fun navigateToCoin(
                coin: Coin,
                view: View
        ) {
            /*val direction =
                    HomeViewPagerFragmentDirections.actionViewPagerFragmentToPlantDetailFragment(
                            plant.plantId
                    )
            view.findNavController().navigate(direction)*/
            //todo navigate to coin view
        }

        fun bind(item: Coin) {
            Timber.d("bind coin")
            binding.apply {
                coin = item
                executePendingBindings()
            }
        }
    }
}

private class CoinDiffCallback : DiffUtil.ItemCallback<Coin>() {

    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.price == newItem.price
    }
}