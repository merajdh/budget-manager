package com.example.managebudget.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.managebudget.data.WalletData

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(walletDao: List<WalletData>)
    @Query("SELECT * FROM wallet_table")
    suspend fun getAll(): List<WalletData>

}
