package ru.suslovalex.exchangerate.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.suslovalex.exchangerate.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.container_layout, MainFragment()).commit()
    }
}