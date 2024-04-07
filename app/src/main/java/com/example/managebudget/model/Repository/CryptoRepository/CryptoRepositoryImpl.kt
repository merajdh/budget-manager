package com.example.managebudget.model.Repository.CryptoRepository

import com.example.managebudget.data.CryptoResponse
import com.example.managebudget.model.ApiServiceCrypto
import com.google.gson.JsonObject

class CryptoRepositoryImpl(private val apiServiceCrypto: ApiServiceCrypto) :CryptoRepository {
    override suspend fun CryptoData(): CryptoResponse {
        return apiServiceCrypto.getCryptoData("USD","15")
    }
}