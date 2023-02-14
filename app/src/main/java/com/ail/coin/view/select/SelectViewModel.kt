package com.ail.coin.view.select

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ail.coin.Repository.NetWorkRepository
import com.ail.coin.model.CurrentPrice
import com.ail.coin.model.CurrentPriceResult
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SelectViewModel: ViewModel() {

    private val netWorkRepository = NetWorkRepository()
    private val tag = SelectViewModel::class.java.simpleName

    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    private val currentPriceResultLiveData = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult : LiveData<List<CurrentPriceResult>>
    get() = currentPriceResultLiveData

    fun getCurrnetCoinList() = viewModelScope.launch {

        val result = netWorkRepository.getCurrentCoinList()

        currentPriceResultList = ArrayList()

        for (coin in result.data ) {

            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)
                val currentPriceResult = CurrentPriceResult(gsonFromJson, coin.key)

                currentPriceResultList.add(currentPriceResult)

            }catch (e : java.lang.Exception) {

            }

        }

        currentPriceResultLiveData.value = currentPriceResultList

    }

}