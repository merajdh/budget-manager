package com.example.managebudget.feature.Wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managebudget.data.WalletData
import com.example.managebudget.db.WalletDao
import kotlinx.coroutines.launch

class WalletViewModel(private val walletDao : WalletDao)
    : ViewModel(){

    val transactionName  = MutableLiveData("")
    val transactionCount  = MutableLiveData("")
    val transactionType  = MutableLiveData("")
    val transactionData = MutableLiveData<List<WalletData>>()

    fun insertOrUpdateData(data :WalletData){
        viewModelScope.launch {
            val walletList = arrayListOf<WalletData>()
            walletList.add(data)
            walletDao.insertOrUpdate(walletList)
        }
    }
    fun getAll( ){
        viewModelScope.launch {
            transactionData.value = walletDao.getAll()
        }
    }

    fun addDataWallet(){
        val data = WalletData(
            name = transactionName.value!!,
            type = transactionType.value!!,
            Count = transactionCount.value!!
        )
        insertOrUpdateData(data)
    }
}