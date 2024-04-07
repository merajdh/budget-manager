package com.example.managebudget.model.Repository.DollarRepository

import com.example.managebudget.data.DollarResponse
import com.example.managebudget.model.ApiServiceDollar
import com.example.managebudget.model.Repository.DollarRepository.DollarRepository

class DollarRepositoryImpl(private val apiServiceDollar: ApiServiceDollar) : DollarRepository {
    override suspend fun getDataDollar(): DollarResponse {

        return apiServiceDollar.getDollarData()
    }
}