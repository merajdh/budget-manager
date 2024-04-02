package com.example.managebudget.feature.Wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managebudget.data.WalletData
import com.example.managebudget.db.WalletDao
import kotlinx.coroutines.launch

class WalletViewModel(private val walletDao: WalletDao) : ViewModel() {


    val transactionName = MutableLiveData("")
    val transactionCount = MutableLiveData("")
    val transactionType = MutableLiveData(false)
    val transactionData = MutableLiveData<List<WalletData>>()
    val expensesData = MutableLiveData(0)
    val incomesData = MutableLiveData(0)
    val currentTotalData = MutableLiveData(0)


    fun insertOrUpdateData(data: WalletData) {
        viewModelScope.launch {
            val walletList = arrayListOf<WalletData>()
            walletList.add(data)
            walletDao.insertOrUpdate(walletList)
        }
    }


    fun resetTransaction() {
        transactionCount.value = ""
        transactionName.value = ""
        transactionType.value = false
    }

    fun totalCurrent() {
        viewModelScope.launch {

            val walletList = walletDao.getAll()

            currentTotalData.value = walletList.map {
                it.Count?.toInt() ?: 0
            }.sum()
        }
    }

    fun totalExpenses() {
        viewModelScope.launch {

            val walletList = walletDao.getAll()

            expensesData.value = walletList.map {

                if (it.type) {

                    it.Count.toString().replace("-", "").toInt()

                } else {
                    0
                }
            }.sum()
        }

    }

    fun totalIncomes() {
        viewModelScope.launch {

            val walletList = walletDao.getAll()

            incomesData.value = walletList.map {
                if (!it.type) {
                    it.Count?.toIntOrNull() ?: 0
                } else {
                    0
                }
            }.sum()
        }

    }

    fun getAll() {
        viewModelScope.launch {
            transactionData.value = walletDao.getAll()

        }
    }


    fun addDataWallet() {


        val data = WalletData(
            name = transactionName.value!!,
            type = transactionType.value!!,
            Count = if (transactionType.value!!) {
                "-${transactionCount.value!!}"
            } else {
                transactionCount.value+"0"

            }
        )

        insertOrUpdateData(data)
    }

}