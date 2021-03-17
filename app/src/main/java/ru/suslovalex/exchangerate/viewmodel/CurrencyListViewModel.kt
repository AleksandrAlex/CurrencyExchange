package ru.suslovalex.exchangerate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.suslovalex.exchangerate.repository.CurrencyRepository
import ru.suslovalex.exchangerate.data.DataChecker
import ru.suslovalex.exchangerate.data.DataCurrency
import ru.suslovalex.exchangerate.data.DataResponseResult
import ru.suslovalex.exchangerate.data.entity.CurrencyEntity

const val NO_INTERNET_CONNECTION = "No internet connection!"

class CurrencyListViewModel(
    private val currencyRepository: CurrencyRepository,
    private val checker: DataChecker
) : ViewModel() {

    private val _currencyListState = MutableLiveData<CurrencyListState>()
    val currencyListState: LiveData<CurrencyListState>
        get() = _currencyListState

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    private val _internetConnection = MutableLiveData<Boolean>()
    val internetConnection: LiveData<Boolean>
    get() = _internetConnection

    init {
        readDataFromDatabase()
//        loadDataFromInternet()
    }

    fun hasInternetConnection() {
        _internetConnection.postValue(true)
    }

    fun noInternetConnection() {
        _internetConnection.postValue(false)
    }

    fun loadDataFromInternet() = viewModelScope.launch {
        _currencyListState.postValue(CurrencyListState.Loading)
        val listDataCurrency = currencyRepository.getData()
        val newState = when (checker.loadData(listDataCurrency)) {
            DataResponseResult.Error -> CurrencyListState.Error("Error!")
            DataResponseResult.Success -> {
                saveDatabase(listDataCurrency)
                _date.postValue(currencyRepository.date)
                CurrencyListState.Success(listDataCurrency)
            }
        }
        _currencyListState.postValue(newState)
    }

    private fun saveDatabase(listDataCurrency: List<DataCurrency>) = viewModelScope.launch {
        currencyRepository.saveDataCurrencyToDatabase(listDataCurrency)
    }

    private fun readDataFromDatabase() = viewModelScope.launch {
        _currencyListState.postValue(CurrencyListState.Loading)
        val listCurrencyEntity = currencyRepository.readDataFromDatabase()
        if (listCurrencyEntity.isEmpty()) {
            loadDataFromInternet()
        } else {
            val listDataCurrency = convertToDataCurrency(listCurrencyEntity)
            _currencyListState.postValue(CurrencyListState.Success(listDataCurrency))
            _date.postValue(listCurrencyEntity[0].date)
        }


    }

    private fun convertToDataCurrency(listCurrencyEntity: List<CurrencyEntity>): List<DataCurrency> {

        return listCurrencyEntity.map { entity ->
            DataCurrency(
                iD = entity.id,
                numCode = entity.numCode,
                charCode = entity.charCode,
                nominal = entity.nominal,
                name = entity.name,
                value = entity.value,
                previous = entity.previous
            )
        }
    }
}

sealed class CurrencyListState {
    data class Success(val currencyList: List<DataCurrency>) : CurrencyListState()
    data class Error(val errorMessage: String) : CurrencyListState()
    object Loading : CurrencyListState()
}