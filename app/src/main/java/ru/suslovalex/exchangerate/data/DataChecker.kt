package ru.suslovalex.exchangerate.data

class DataChecker {

    fun loadData(movies: List<DataCurrency>): DataResponseResult {
        return if (movies.isEmpty()){
            DataResponseResult.Error
        } else{
            DataResponseResult.Success
        }
    }
}