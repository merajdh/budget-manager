package com.example.managebudget.di

import androidx.room.Room
import com.example.managebudget.db.AppDatabase
import com.example.managebudget.feature.Wallet.WalletDialogViewModel
import com.example.managebudget.feature.Wallet.WalletViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Modules   = module {
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_dataBase.db").build() }
    viewModel{WalletViewModel( get<AppDatabase>().walletDao())}
    viewModel{WalletDialogViewModel()}

}