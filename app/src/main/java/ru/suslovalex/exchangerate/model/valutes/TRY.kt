package ru.suslovalex.exchangerate.model.valutes


import com.google.gson.annotations.SerializedName

data class TRY(
    @SerializedName("ID")
    val iD: String,
    @SerializedName("NumCode")
    val numCode: String,
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Nominal")
    val nominal: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Value")
    val value: Double,
    @SerializedName("Previous")
    val previous: Double
)