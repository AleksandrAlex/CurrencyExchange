package ru.suslovalex.exchangerate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.suslovalex.exchangerate.R
import ru.suslovalex.exchangerate.data.DataCurrency

class CurrencyAdapter(private val adapterOnClick: (DataCurrency) -> Unit): ListAdapter<DataCurrency, CurrencyViewHolder>(CurrencyDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            adapterOnClick(getItem(position))
        }
    }
}

class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.name)
    private val code: TextView = itemView.findViewById(R.id.char_code)
    private val exchangeValue: TextView = itemView.findViewById(R.id.value)

    fun bind(dataCurrency: DataCurrency){
        val codeValue = "${dataCurrency.nominal} ${dataCurrency.charCode}"
        name.text = dataCurrency.name
        code.text = codeValue
        exchangeValue.text = dataCurrency.value.toString()
    }
}

class CurrencyDiffUtil: DiffUtil.ItemCallback<DataCurrency>() {
    override fun areItemsTheSame(oldItem: DataCurrency, newItem: DataCurrency): Boolean {
        return oldItem.iD == newItem.iD
    }

    override fun areContentsTheSame(oldItem: DataCurrency, newItem: DataCurrency): Boolean {
        return oldItem == newItem
    }

}