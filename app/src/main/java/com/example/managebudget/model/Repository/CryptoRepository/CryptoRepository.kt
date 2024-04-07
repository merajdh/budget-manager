package com.example.managebudget.model.Repository.CryptoRepository

import com.example.managebudget.data.CryptoResponse

interface CryptoRepository {
    suspend fun CryptoData():CryptoResponse

}