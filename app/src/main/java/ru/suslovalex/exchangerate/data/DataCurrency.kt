package ru.suslovalex.exchangerate.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataCurrency(
        val iD: String,
        val numCode: String,
        val charCode: String,
        val nominal: Int,
        val name: String,
        val value: Double,
        val previous: Double
): Parcelable