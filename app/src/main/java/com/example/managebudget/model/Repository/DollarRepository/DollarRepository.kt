package com.example.managebudget.model.Repository.DollarRepository

import com.example.managebudget.data.DollarResponse

interface DollarRepository {

    suspend fun getDataDollar():DollarResponse
}
