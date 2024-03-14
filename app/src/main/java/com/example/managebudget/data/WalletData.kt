package com.example.managebudget.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_table")

data class WalletData(
    @PrimaryKey
    val transactionName : String ,
    val transactionMood : String ,
    val transactionCount : Int ,

)