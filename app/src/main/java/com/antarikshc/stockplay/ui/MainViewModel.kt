package com.antarikshc.stockplay.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antarikshc.stockplay.data.Repository
import com.antarikshc.stockplay.models.IncPrices
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _stocks = MutableLiveData<List<IncPrices>>()
    val stocks = _stocks

    init {
        repository.getStock()
            .onEach { _stocks.postValue(it) }
            .launchIn(viewModelScope)
    }

}
