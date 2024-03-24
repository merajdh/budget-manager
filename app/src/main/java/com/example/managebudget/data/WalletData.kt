package com.example.managebudget.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Wallet_Table")
data class WalletData(
    @PrimaryKey
    val type : String,
    val name : String?,
    val Count : String? ,

)