package ru.suslovalex.exchangerate.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.suslovalex.exchangerate.R
import ru.suslovalex.exchangerate.data.DataCurrency

private const val UNKNOWN_PROPERTY = "Unknown"
private const val UNKNOWN_DOUBLE_VALUE = 0.0
private const val UNKNOWN_INT_VALUE = 0

class CurrencyConverterFragment : Fragment(R.layout.fragment_convert_currency) {

    private lateinit var args: DataCurrency

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = getCurrency()
        initFields(view)
    }

    private fun initFields(view: View) {
        val title: TextView = view.findViewById(R.id.txt_title_converter)
        val code: TextView = view.findViewById(R.id.code_currency)
        val name = getString(R.string.converter) + "\n" + args.name
        val codeCurrency = args.charCode

        title.text = name
        code.text = codeCurrency

    }

    private fun getCurrency(): DataCurrency {
        return arguments?.getParcelable<DataCurrency>("data") ?: DataCurrency(
            UNKNOWN_PROPERTY,
            UNKNOWN_PROPERTY,
            UNKNOWN_PROPERTY,
            UNKNOWN_INT_VALUE,
            UNKNOWN_PROPERTY,
            UNKNOWN_DOUBLE_VALUE,
            UNKNOWN_DOUBLE_VALUE
        )
    }


    companion object {
        fun newInstance(dataCurrency: DataCurrency): CurrencyConverterFragment {
            val args = Bundle()
            args.putParcelable("data", dataCurrency)
            val fragment = CurrencyConverterFragment()
            fragment.arguments = args
            return fragment
        }
    }
}