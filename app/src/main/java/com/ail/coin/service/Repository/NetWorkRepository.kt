package com.ail.coin.service.Repository

import com.ail.coin.network.Api
import com.ail.coin.network.RetrofitInstance

class NetWorkRepository {

    private val client = RetrofitInstance.getInstance().create(Api::class.java)
    suspend fun getCurrentCoinList() = client.getCurrentCoinList()
    suspend fun getInterestCoinPriceData(coin : String) = client.getRecentCoinPrice(coin)

}