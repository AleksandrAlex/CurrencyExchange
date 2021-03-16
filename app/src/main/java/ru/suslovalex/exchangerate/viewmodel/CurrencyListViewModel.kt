package ru.suslovalex.exchangerate.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.suslovalex.exchangerate.repository.CurrencyRepository
import ru.suslovalex.exchangerate.data.DataChecker
import ru.suslovalex.exchangerate.data.DataCurrency
import ru.suslovalex.exchangerate.data.DataResponseResult

class CurrencyListViewModel(private val currencyRepository: CurrencyRepository, private val checker: DataChecker) : ViewModel() {

    private val _currencyListState = MutableLiveData<CurrencyListState>()
    val currencyListState: LiveData<CurrencyListState>
        get() = _currencyListState

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        Log.d("MyViewModel", "init viewmodel")

        _currencyListState.postValue(CurrencyListState.Loading)
        val listDataCurrency = currencyRepository.getData()
        val newState = when (checker.loadData(listDataCurrency)){
            DataResponseResult.Error -> CurrencyListState.Error("Error!")
            DataResponseResult.Success -> CurrencyListState.Success(listDataCurrency)
        }
        _currencyListState.postValue(newState)
    }
}

sealed class CurrencyListState {

    data class Success(val currencyList: List<DataCurrency>) : CurrencyListState()
    data class Error(val errorMessage: String) : CurrencyListState()
    object Loading : CurrencyListState()


}