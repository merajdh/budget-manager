package com.example.managebudget.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.managebudget.data.IncomeData

//@Database(entities = [IncomeData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun incomeDao(): IncomeDao

}