package ru.suslovalex.exchangerate.data

sealed class DataResponseResult {
    object Success : DataResponseResult()
    object Error : DataResponseResult()

}
