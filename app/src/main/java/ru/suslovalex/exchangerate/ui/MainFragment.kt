package ru.suslovalex.exchangerate.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.suslovalex.exchangerate.R
import ru.suslovalex.exchangerate.data.DataCurrency
import ru.suslovalex.exchangerate.adapter.CurrencyAdapter
import ru.suslovalex.exchangerate.retrofit.RemoteDataStore

class MainFragment : Fragment() {

    private lateinit var currencyAdapter: CurrencyAdapter

    private val api = RemoteDataStore()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        setupUI(view, getCurrencyList())

        lifecycleScope.launch {
            val data = api.getData()

            val valute = data.valute
            val valuteStrings = valute.toString()
                .drop(11)
                .dropLast(2)
                .split("\\),\\s\\w{3}=".toRegex())
                .map { it.drop(4) }

            for (string in valuteStrings) {
                Log.d("DATA", string)
            }

            val valuteMap = valuteStrings.map {
               it.split(", ").associate {
                   val (left, right) = it.split("=")
                   left to right
               }
            }

            val dataCurrencyList = valuteMap.map {
                DataCurrency(
                    iD = it["iD"].toString(),
                    numCode = it["numCode"].toString(),
                    charCode = it["charCode"].toString(),
                    nominal = it["nominal"]?.toInt() ?: 0,
                    name = it["name"].toString(),
                    value = it["value"]?.toDouble() ?: 0.0,
                    previous = it["previous"]?.toDouble() ?: 0.0
                )
            }

            for (string in dataCurrencyList) {
                Log.d("DATA", "${string.iD} || ${string.name} || ${string.charCode}")
            }

            setupUI(view, dataCurrencyList)
        }

    }

    private fun setupUI(view: View, dataCurrencies: List<DataCurrency>) {
        val currencyList: RecyclerView = view.findViewById(R.id.currency_list)
        currencyAdapter = CurrencyAdapter()
        currencyList.layoutManager = LinearLayoutManager(context)
        currencyList.adapter = currencyAdapter
        currencyAdapter.submitList(dataCurrencies)
    }
}