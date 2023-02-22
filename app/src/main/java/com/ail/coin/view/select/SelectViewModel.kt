package com.ail.coin.view.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ail.coin.service.Repository.DBRepository
import com.ail.coin.service.Repository.NetWorkRepository
import com.ail.coin.dataStore.MyDataStore
import com.ail.coin.view.db.entity.InterestCoinEntity
import com.ail.coin.model.CurrentPrice
import com.ail.coin.model.CurrentPriceResult
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()
    private val dbRepository = DBRepository()

    private var currentPriceResultList: ArrayList<CurrentPriceResult> = ArrayList()

    private val currentPriceResultLiveData = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>>
        get() = currentPriceResultLiveData

    private val _saved = MutableLiveData<String>()
    val save: LiveData<String>
        get() = _saved

    fun getCurrnetCoinList() = viewModelScope.launch {

        val result = netWorkRepository.getCurrentCoinList()
        currentPriceResultList = ArrayList()
        for (coin in result.data) {
            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data[coin.key])
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)
                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
                currentPriceResultList.add(currentPriceResult)
            } catch (e: java.lang.Exception) {

            }

        }
        currentPriceResultLiveData.value = currentPriceResultList
    }

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    fun saveSelectedCoinList(selectedCoinList: ArrayList<String>) =
        viewModelScope.launch(Dispatchers.IO) {

            for (coin in currentPriceResultList) {

                // 2. 내가 선택한 코인인지 아닌지 구분해서
                // 포함하면 TRUE / 포함하지 않으면 FALSE
                val selected = selectedCoinList.contains(coin.coinName)

                val interestCoinEntity = InterestCoinEntity(
                    0,
                    coin.coinName,
                    coin.coinInfo.opening_price,
                    coin.coinInfo.closing_price,
                    coin.coinInfo.min_price,
                    coin.coinInfo.max_price,
                    coin.coinInfo.units_traded,
                    coin.coinInfo.acc_trade_value,
                    coin.coinInfo.prev_closing_price,
                    coin.coinInfo.units_traded_24H,
                    coin.coinInfo.acc_trade_value_24H,
                    coin.coinInfo.fluctate_24H,
                    coin.coinInfo.fluctate_rate_24H,
                    selected
                )
                // 3. 저장
                interestCoinEntity.let {
                    Timber.d(it.toString())
                    dbRepository.insertInterestCoinData(it)
                }
            }
            withContext(Dispatchers.Main) {
                _saved.value = "done"
            }
        }
}