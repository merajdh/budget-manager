package com.example.managebudget.extensions
fun changeCharAtIndex(str: String, index: Int, newChar: Char): String {
    if (index < 0 || index >= str.length) {
        return "Index out of range"
    }

    val stringBuilder = StringBuilder(str)
    stringBuilder.setCharAt(index, newChar)
    return stringBuilder.toString()
}