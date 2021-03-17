package ru.suslovalex.exchangerate.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.suslovalex.exchangerate.data.entity.CurrencyEntity


@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM table_currencies")
    suspend fun getCurrencies(): List<CurrencyEntity>

    @Query("DELETE FROM table_currencies")
    suspend fun deleteTableCurrencies()
}