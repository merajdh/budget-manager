package com.example.managebudget.feature.Wallet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DeleteItemViewModel : ViewModel(){
    var isDialogOpen by mutableStateOf(false)

    fun onClick(){
        isDialogOpen = true
    }
    fun onDismiss(){
        isDialogOpen = false
    }
}