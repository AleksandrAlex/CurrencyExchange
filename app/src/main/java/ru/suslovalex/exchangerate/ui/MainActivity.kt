package ru.suslovalex.exchangerate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.suslovalex.exchangerate.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            initFragment()
        }
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.container_layout, CurrencyListFragment()).commit()
    }
}