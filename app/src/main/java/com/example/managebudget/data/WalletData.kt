package com.example.managebudget.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Wallet_Table")
data class WalletData(
    @PrimaryKey
    val name: String,
    val type: Boolean,
    val count: String?,
    val time: String?,

    )