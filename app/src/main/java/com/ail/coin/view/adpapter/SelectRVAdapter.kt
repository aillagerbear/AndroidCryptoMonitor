package com.ail.coin.view.adpapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ail.coin.R
import com.ail.coin.databinding.IntroCoinItemBinding
import com.ail.coin.model.CurrentPriceResult

class SelectRVAdapter(
    private val context: Context,
    private var coinPriceList: List<CurrentPriceResult>
) : RecyclerView.Adapter<SelectRVAdapter.ViewHolder>() {

    val selectedCoinList = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            IntroCoinItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coinPriceList[position], selectedCoinList)
    }

    override fun getItemCount(): Int {
        return coinPriceList.size
    }

    fun updateData(newData: List<CurrentPriceResult>) {
        val oldSize = coinPriceList.size
        coinPriceList = newData
        val newSize = coinPriceList.size
        val diffSize = newSize - oldSize
        if (diffSize > 0) {
            // New items have been added, notify the adapter
            notifyItemRangeInserted(oldSize, diffSize)
        } else if (diffSize < 0) {
            // Some items have been removed, notify the adapter
            notifyItemRangeRemoved(newSize, -diffSize)
        } else {
            // The size of the list hasn't changed, notify the adapter of item changes
            notifyItemRangeChanged(0, newSize)
        }
    }


    inner class ViewHolder(private val binding: IntroCoinItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coinPriceResult: CurrentPriceResult, selectedCoinList: MutableList<String>) {
            binding.coinName.text = coinPriceResult.coinName
            val fluctuatingCoinValue = coinPriceResult.coinInfo.fluctate_24H
            val coinPriceText = binding.coinPriceUpDown
            val likeImage = binding.likeImage

            if (fluctuatingCoinValue.contains("-")) {
                coinPriceText.text = "하락입니다"
                coinPriceText.setTextColor(Color.parseColor("red"))
            } else {
                coinPriceText.text = "상승입니다"
                coinPriceText.setTextColor(Color.parseColor("green"))
            }

            if (selectedCoinList.contains(coinPriceResult.coinName)) {
                likeImage.setImageResource(R.drawable.like_red)
            } else {
                likeImage.setImageResource(R.drawable.like_grey)
            }

            likeImage.setOnClickListener {
                val selected = selectedCoinList.contains(coinPriceResult.coinName)
                if (selected) {
                    selectedCoinList.remove(coinPriceResult.coinName)
                } else {
                    selectedCoinList.add(coinPriceResult.coinName)
                }
                likeImage.setImageResource(if (selected) R.drawable.like_grey else R.drawable.like_red)
            }

        }
    }
}
