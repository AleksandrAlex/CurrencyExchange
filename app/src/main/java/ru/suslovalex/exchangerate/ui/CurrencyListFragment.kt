package ru.suslovalex.exchangerate.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import org.koin.android.viewmodel.ext.android.viewModel
import ru.suslovalex.exchangerate.R
import ru.suslovalex.exchangerate.data.DataCurrency
import ru.suslovalex.exchangerate.adapter.CurrencyAdapter
import ru.suslovalex.exchangerate.isConnectedToNetwork
import ru.suslovalex.exchangerate.viewmodel.CurrencyListState
import ru.suslovalex.exchangerate.viewmodel.CurrencyListViewModel
import ru.suslovalex.exchangerate.viewmodel.NO_INTERNET_CONNECTION

class CurrencyListFragment : Fragment(R.layout.fragment_currency_list) {

    private lateinit var currencyAdapter: CurrencyAdapter
    private val currencyListViewModel: CurrencyListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backAnimationFromConverter(view)
        initRefresh(view)

        currencyListViewModel.date.observe(viewLifecycleOwner, Observer { date ->
            setupDate(view, date)
        })

        currencyListViewModel.currencyListState.observe(viewLifecycleOwner, Observer { state ->

            when (state) {
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

    private fun backAnimationFromConverter(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun initRefresh(view: View) {
        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.refresh)
        swipeRefreshLayout.setOnRefreshListener {
            if (view.context.isConnectedToNetwork()) {
                currencyListViewModel.loadDataFromInternet()
                swipeRefreshLayout.isRefreshing = false
            } else {
                showError(NO_INTERNET_CONNECTION)
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun setupDate(view: View, dateString: String?) {
        val date: TextView = view.findViewById(R.id.date)
        date.text = dateString

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
        currencyAdapter = CurrencyAdapter{dataCurrency, view -> onClickItem(dataCurrency, view) }
        currencyList.layoutManager = LinearLayoutManager(context)
        currencyList.adapter = currencyAdapter
        currencyAdapter.submitList(dataCurrencies)
    }

    private fun onClickItem(dataCurrency: DataCurrency, view: View) {
        activity?.let {
            it.supportFragmentManager.beginTransaction()
                .addSharedElement(view, getString(R.string.transition))
                .replace(R.id.container_layout, CurrencyConverterFragment.newInstance(dataCurrency))
                .addToBackStack(null)
                .commit()
        }
    }
}