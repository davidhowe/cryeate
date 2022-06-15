package com.davidhowe.cryeate.ui.main.watchlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davidhowe.cryeate.databinding.RowItemMainListBinding
import com.davidhowe.cryeate.models.db.Coin
import com.davidhowe.cryeate.ui.main.MainTabFragmentDirections
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
                    navigateToCoinDetailView(plant, it)
                }
            }
        }

        private fun navigateToCoinDetailView(
                coin: Coin,
                view: View
        ) {
            val direction =
                MainTabFragmentDirections.actionMainTabFragmentToCoinDetailFragment(
                    coin.id
                )
            view.findNavController().navigate(direction)
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