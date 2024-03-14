package com.example.managebudget.feature.Wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managebudget.data.WalletData
import com.example.managebudget.db.WalletDao
import kotlinx.coroutines.launch

class WalletViewModel(private val walletDao : WalletDao)
    : ViewModel(){

    val transactionName  = MutableLiveData<List<WalletData>>()

    fun addTransaction(data :WalletData){
        viewModelScope.launch {
            val walletList = arrayListOf<WalletData>()
            walletList.add(data)
            walletDao.insertOrUpdate(walletList)
        }
    }
    fun getAll( ){
        viewModelScope.launch {
            transactionName.value = walletDao.getAll()
        }
    }
}