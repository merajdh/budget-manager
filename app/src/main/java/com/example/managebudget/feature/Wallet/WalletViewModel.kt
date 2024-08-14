package com.example.managebudget.feature.Wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managebudget.data.WalletData
import com.example.managebudget.db.WalletDao
import kotlinx.coroutines.launch
import java.math.BigInteger

class WalletViewModel(private val walletDao: WalletDao) : ViewModel() {


    val transactionName = MutableLiveData("")
    val transactionCount = MutableLiveData("")
    val transactionType = MutableLiveData(false)
    val transactionTime = MutableLiveData("")
    val transactionData = MutableLiveData<List<WalletData>>()
    val expensesData = MutableLiveData<BigInteger>()
    val incomesData = MutableLiveData<BigInteger>()
    val currentTotalData = MutableLiveData<BigInteger>()

    init {
        viewModelScope.launch {
            transactionData.value = walletDao.getAll()
        }
    }


    fun insertOrUpdateData(data: WalletData) {
        viewModelScope.launch {
            val walletList = arrayListOf<WalletData>()
            walletList.add(data)
            walletDao.insertOrUpdate(walletList)
        }
    }

    fun deleteItem(item: WalletData) {
        viewModelScope.launch {
            walletDao.deleteTransaction(item)
            transactionData.value = walletDao.getAll()

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
                it.count?.let { BigInteger(it) } ?: BigInteger.ZERO
            }.fold(BigInteger.ZERO) { acc, bigInt -> acc + bigInt}
        }
    }

    fun totalExpenses() {
        viewModelScope.launch {

            val walletList = walletDao.getAll()

            expensesData.value = walletList.map { it ->
                if (it.type) {
                    it.count?.let { BigInteger(it) } ?: BigInteger.ZERO
                }else{
                    BigInteger.ZERO
                }
            }.fold(BigInteger.ZERO) { acc, bigInt -> acc + bigInt}
        }
    }

    fun totalIncomes() {
        viewModelScope.launch {

            val walletList = walletDao.getAll()

            incomesData.value = walletList.map {
                if (!it.type) {
                    it.count?.toString()?.let { countStr -> BigInteger(countStr) } ?: BigInteger.ZERO
                }else{
                    BigInteger.ZERO
                }
            }.fold(BigInteger.ZERO) { acc, bigInt -> acc + bigInt }


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
            count = if (transactionType.value!!) {
                "-${transactionCount.value!!}"
            } else {
                transactionCount.value

            },
            time = transactionTime.value
        )

        insertOrUpdateData(data)
    }

}