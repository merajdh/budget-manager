package com.example.managebudget.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.managebudget.data.IncomeData
import com.example.managebudget.data.WalletData

@Database(entities = [WalletData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun incomeDao(): WalletDao

}