package com.example.managebudget.di

import androidx.room.Room
import com.example.managebudget.db.AppDatabase
import com.example.managebudget.feature.CryptoCurrency.CryptoCurrencyViewModel
import com.example.managebudget.feature.Wallet.DeleteItemViewModel
import com.example.managebudget.feature.Wallet.WalletDialogViewModel
import com.example.managebudget.feature.Wallet.WalletViewModel
import com.example.managebudget.model.ApiServiceCrypto
import com.example.managebudget.model.ApiServiceDollar
import com.example.managebudget.model.Repository.CryptoRepository.CryptoRepository
import com.example.managebudget.model.Repository.CryptoRepository.CryptoRepositoryImpl
import com.example.managebudget.model.Repository.DollarRepository.DollarRepository
import com.example.managebudget.model.Repository.DollarRepository.DollarRepositoryImpl
import com.example.managebudget.model.createApiServiceCrypto
import com.example.managebudget.model.createApiServiceDollar
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val Modules = module {
    single<ApiServiceDollar> { createApiServiceDollar() }
    single<ApiServiceCrypto> { createApiServiceCrypto() }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_dataBase.db").build()
    }
    single<DollarRepository> { DollarRepositoryImpl(get()) }
    single<CryptoRepository> { CryptoRepositoryImpl(get()) }

    viewModel { (isInternetConnect: Boolean) ->
        CryptoCurrencyViewModel(
            get(),
            get(),
            isInternetConnect
        )
    }
    viewModel { WalletViewModel(get<AppDatabase>().walletDao()) }
    viewModel { WalletDialogViewModel() }
    viewModel { DeleteItemViewModel() }

}