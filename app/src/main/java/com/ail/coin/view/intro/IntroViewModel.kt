package com.ail.coin.view.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ail.coin.dataStore.MyDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroViewModel : ViewModel() {

    private val _first = MutableLiveData<Boolean>()
    val first: LiveData<Boolean>
        get() = _first
    fun checkFirstFlag() = viewModelScope.launch {

        delay(3000)

        val getData = MyDataStore().getFirstData()

        _first.value = getData
    }
}