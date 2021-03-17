package ru.suslovalex.exchangerate.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.suslovalex.exchangerate.data.entity.CurrencyEntity


@Database(entities = [CurrencyEntity::class], version = 1, exportSchema = false)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract val currencyDao: CurrencyDao

    companion object {
        @Volatile
        private var instance: CurrencyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }

        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                CurrencyDatabase::class.java,
                "CurrencyDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}