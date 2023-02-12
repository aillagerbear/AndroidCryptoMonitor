package com.ail.coin.network

import com.ail.coin.model.CurrentPriceTest
import retrofit2.http.GET

interface Api {

    @GET("public/ticker/ALL_KRW")
    suspend fun getCurrentCoinList() :CurrentPriceTest

}