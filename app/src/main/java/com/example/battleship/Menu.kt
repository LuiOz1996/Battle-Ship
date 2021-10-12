package com.example.battleship

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_menu.*


class Menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        go_buttom.setOnClickListener{
            val Intent_to_set_ship = Intent(this,SettingShip::class.java)
            startActivity(Intent_to_set_ship)
        }
    }
}