package com.example.managebudget.utils

import java.util.*

sealed class Screens(val route: String){
    object WalletScreen : Screens( "کیف پول")
    object SettingScreen : Screens("تنظیمات")
    object CryptoCurrencyScreen : Screens("ارز دیجیتال")

}