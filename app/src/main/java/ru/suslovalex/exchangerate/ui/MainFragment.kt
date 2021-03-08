package ru.suslovalex.exchangerate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.suslovalex.exchangerate.R
import ru.suslovalex.exchangerate.adapter.Currency
import ru.suslovalex.exchangerate.adapter.CurrencyAdapter

class MainFragment : Fragment() {

    private lateinit var currencyAdapter: CurrencyAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupUI(view, getCurrencyList())

    }

    private fun getCurrencyList(): List<Currency> {
        val list = mutableListOf<Currency>()
        list.add( Currency("1", "Pound", "3224FD", 100))
        list.add( Currency("2", "Rub", "rer", 200))
        list.add( Currency("3", "Yo!", "KUH653", 300))
        list.add( Currency("4", "Griv", "LJY78", 400))
        return list
    }

    private fun setupUI(view: View, currencies: List<Currency>) {
        val currencyList: RecyclerView = view.findViewById(R.id.currency_list)
        currencyAdapter = CurrencyAdapter()
        currencyList.layoutManager = LinearLayoutManager(context)
        currencyList.adapter = currencyAdapter
        currencyAdapter.submitList(currencies)
    }
}