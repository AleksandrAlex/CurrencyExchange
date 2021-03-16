package ru.suslovalex.exchangerate.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.viewmodel.ext.android.viewModel
import ru.suslovalex.exchangerate.R
import ru.suslovalex.exchangerate.data.DataCurrency
import ru.suslovalex.exchangerate.adapter.CurrencyAdapter
import ru.suslovalex.exchangerate.viewmodel.CurrencyListState
import ru.suslovalex.exchangerate.viewmodel.CurrencyListViewModel

class CurrencyListFragment : Fragment(R.layout.fragment_currency_list) {

    private lateinit var currencyAdapter: CurrencyAdapter

    private val currencyListViewModel: CurrencyListViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyListViewModel.currencyListState.observe(viewLifecycleOwner, Observer { state ->

            when (state){
                is CurrencyListState.Success -> {
                    hideProgressBar(view)
                    setupUI(view, state.currencyList)
                }
                is CurrencyListState.Error -> {
                    showError(state.errorMessage)

                }
                CurrencyListState.Loading ->
                    showProgressBar(view)
            }

        })
    }

    private fun showProgressBar(view: View) {
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun hideProgressBar(view: View) {
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        progressBar.visibility = View.GONE
    }

    private fun setupUI(view: View, dataCurrencies: List<DataCurrency>) {
        val currencyList: RecyclerView = view.findViewById(R.id.currency_list)
        currencyAdapter = CurrencyAdapter()
        currencyList.layoutManager = LinearLayoutManager(context)
        currencyList.adapter = currencyAdapter
        currencyAdapter.submitList(dataCurrencies)
    }
}