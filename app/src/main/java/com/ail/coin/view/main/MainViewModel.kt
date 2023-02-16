package com.ail.coin.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ail.coin.Repository.DBRepository
import com.ail.coin.db.entity.InterestCoinEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val dbRepository = DBRepository()

    lateinit var selectedCoinList : LiveData<List<InterestCoinEntity>>

    fun getAllInterestCoinData() = viewModelScope.launch {
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = viewModelScope.launch(
        Dispatchers.IO) {
        interestCoinEntity.selected = !interestCoinEntity.selected
        dbRepository.updateInterestCoinData(interestCoinEntity)
    }


    // PriceChangeFragment

}