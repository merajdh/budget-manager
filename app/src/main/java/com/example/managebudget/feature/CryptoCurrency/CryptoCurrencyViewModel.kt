package com.example.managebudget.feature.CryptoCurrency

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managebudget.data.CryptoResponse
import com.example.managebudget.data.DollarResponse
import com.example.managebudget.model.Repository.CryptoRepository.CryptoRepository
import com.example.managebudget.model.Repository.DollarRepository.DollarRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class CryptoCurrencyViewModel(
    private val dollarRepository: DollarRepository,
    private val cryptoRepository: CryptoRepository,
    isInternetConnected: Boolean
) : ViewModel() {

    init {
        refreshCryptoData(isInternetConnected)
        refreshDollarData(isInternetConnected)
    }

    var dollarData = MutableLiveData<DollarResponse>()
    var cryptoData = MutableLiveData<CryptoResponse>()
    fun refreshDollarData(isInternetConnected: Boolean) {
        viewModelScope.launch {
            if (isInternetConnected) {
                val dollarData = async { dollarRepository.getDataDollar() }
                getDollarData(dollarData.await())
            }
        }
    }

    fun getDollarData(data: DollarResponse) {
        dollarData.value = data
    }

    fun refreshCryptoData(isInternetConnectedd: Boolean) {
        viewModelScope.launch {
            if (isInternetConnectedd) {
                val cryptoData = async { cryptoRepository.CryptoData() }
                getCryptoData(cryptoData.await())

            }
        }
    }

    fun getCryptoData(data: CryptoResponse) {

        cryptoData.value = data
    }

}