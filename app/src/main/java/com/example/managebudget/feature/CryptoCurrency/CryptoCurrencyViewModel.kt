package com.example.managebudget.feature.CryptoCurrency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managebudget.data.CryptoResponse
import com.example.managebudget.data.DollarResponse
import com.example.managebudget.model.Repository.CryptoRepository.CryptoRepository
import com.example.managebudget.model.Repository.DollarRepository.DollarRepository
import kotlinx.coroutines.launch

class CryptoCurrencyViewModel(
    private val dollarRepository: DollarRepository,
    private val cryptoRepository: CryptoRepository,

    ) : ViewModel() {

    var dollarData = MutableLiveData<DollarResponse>()
    var cryptoData = MutableLiveData<CryptoResponse>()
    fun getDollarData(){
        viewModelScope.launch {
            val data=dollarRepository.getDataDollar()
            dollarData.value =data
        }
    }

    fun getCryptoData(){
        viewModelScope.launch {
            val data = cryptoRepository.CryptoData()
            cryptoData.value = data
        }
    }
}