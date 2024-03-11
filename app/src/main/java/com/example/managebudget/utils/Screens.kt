package com.example.managebudget.utils

import java.util.*

sealed class Screens(route: String){
    object MainScreen : Screens("MainScreen")
}