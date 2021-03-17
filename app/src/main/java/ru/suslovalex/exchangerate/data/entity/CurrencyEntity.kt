package ru.suslovalex.exchangerate.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_currencies")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val numCode: String,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Double,
    val previous: Double,
    val date: String
)