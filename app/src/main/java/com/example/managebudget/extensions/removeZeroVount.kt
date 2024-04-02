package com.example.managebudget.extensions

 fun removeZeros(currencyString: String): String {
    return if (currencyString.endsWith(".00")) {
        currencyString.dropLast(3)
    } else {
        currencyString
    }
}