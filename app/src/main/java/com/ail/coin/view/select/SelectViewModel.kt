package com.ail.coin.view.select

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ail.coin.Repository.NetWorkRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SelectViewModel: ViewModel() {

    private val netWorkRepository = NetWorkRepository()

    fun getCurrnetCoinList() = viewModelScope.launch {

        val result = netWorkRepository.getCurrentCoinList()

        Timber.d(result.toString())

    }

}