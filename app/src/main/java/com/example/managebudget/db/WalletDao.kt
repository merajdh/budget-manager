package com.example.managebudget.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.managebudget.data.WalletData

@Dao
interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(walletDao: List<WalletData>)
    @Query("SELECT * FROM Wallet_Table")
    suspend fun getAll(): List<WalletData>

}
