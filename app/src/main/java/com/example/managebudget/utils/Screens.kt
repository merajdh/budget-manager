package com.example.managebudget.utils

import java.util.*

sealed class Screens(val route: String){
    object WalletScreen : Screens( "wallet")
    object SettingScreen : Screens("setting")

}