package ru.suslovalex.exchangerate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.suslovalex.exchangerate.data.DataCurrency
import java.lang.Exception

class CurrencyConverterViewModel : ViewModel() {


    private val _convertCurrency = MutableLiveData<String>()
    val convertCurrency: LiveData<String>
        get() = _convertCurrency


    fun getValues(dataCurrency: DataCurrency, rubString: String) =
        viewModelScope.launch(Dispatchers.IO) {

            if (rubString.isEmpty()) {
                return@launch
            }
            try {
                val rub = rubString.toLong()
                val value = dataCurrency.value
                val nominal = dataCurrency.nominal

                val result = rub * nominal / value
                val resultString = String.format("%.3f", result)
                _convertCurrency.postValue(resultString)
            }catch (e: Exception){
                return@launch
            }


        }
}