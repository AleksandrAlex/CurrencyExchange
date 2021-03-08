package ru.suslovalex.exchangerate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.suslovalex.exchangerate.R

class CurrencyAdapter: ListAdapter<Currency, CurrencyViewHolder>(CurrencyDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.name)
    private val code: TextView = itemView.findViewById(R.id.char_code)
    private val exchangeValue: TextView = itemView.findViewById(R.id.value)

    fun bind(currency: Currency){
        name.text = currency.name
        code.text = currency.code
        exchangeValue.text = currency.value.toString()
    }
}

class CurrencyDiffUtil: DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
        return oldItem == newItem
    }

}