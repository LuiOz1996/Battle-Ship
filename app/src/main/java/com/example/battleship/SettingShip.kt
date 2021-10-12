package com.example.battleship

import Ship
import Field
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_setting_ship.*

class SettingShip : AppCompatActivity() {

    var count = 10

    val field_for_user = Field()

    val ship_user_1 = Ship(1, 4, field_for_user)
    val ship_user_2 = Ship(2, 3, field_for_user)
    val ship_user_3 = Ship(3, 3, field_for_user)
    val ship_user_4 = Ship(4, 2, field_for_user)
    val ship_user_5 = Ship(5, 2, field_for_user)
    val ship_user_6 = Ship(6, 2, field_for_user)
    val ship_user_7 = Ship(7, 1, field_for_user)
    val ship_user_8 = Ship(8, 1, field_for_user)
    val ship_user_9 = Ship(9, 1, field_for_user)
    val ship_user_10 = Ship(10, 1, field_for_user)

    var array_x:Array<Int> = Array (11){0}
    var array_y:Array<Int> = Array(11){0}
    var array_horizontal:Array<Boolean> = Array(11){false}


    private fun setting_ship(v:View){

        val x = editText_coordinates_x.text.toString().toInt()
        val y = editText_coordinates_y.text.toString().toInt()
        val horizontal = radio_horizontal.isChecked

        number_decks.text = "4"

        if (count == 10 && field_for_user.chekSetShipForField(ship_user_1,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_1,x,y,horizontal)
            count -= 1
            number_decks.text = "3"
            array_x[1] = x
            array_y[1] = y
            array_horizontal[1] = horizontal
        }
        if (count == 9 && field_for_user.chekSetShipForField(ship_user_2,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_2,x,y,horizontal)
            count -= 1
            number_decks.text = "3"
            array_x[2] = x
            array_y[2] = y
            array_horizontal[2] = horizontal
        }
        if (count == 8 && field_for_user.chekSetShipForField(ship_user_3,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_3,x,y,horizontal)
            count -= 1
            number_decks.text = "2"
            array_x[3] = x
            array_y[3] = y
            array_horizontal[3] = horizontal
        }
        if (count == 7 && field_for_user.chekSetShipForField(ship_user_4,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_4,x,y,horizontal)
            count -= 1
            number_decks.text = "2"
            array_x[4] = x
            array_y[4] = y
            array_horizontal[4] = horizontal
        }
        if (count == 6 && field_for_user.chekSetShipForField(ship_user_5,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_5,x,y,horizontal)
            count -= 1
            number_decks.text = "2"
            array_x[5] = x
            array_y[5] = y
            array_horizontal[5] = horizontal
        }
        if (count == 5 && field_for_user.chekSetShipForField(ship_user_6,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_6,x,y,horizontal)
            count -= 1
            number_decks.text = "1"
            array_x[6] = x
            array_y[6] = y
            array_horizontal[6] = horizontal
        }
        if (count == 4 && field_for_user.chekSetShipForField(ship_user_7,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_7,x,y,horizontal)
            count -= 1
            number_decks.text = "1"
            array_x[7] = x
            array_y[7] = y
            array_horizontal[7] = horizontal
        }
        if (count == 3 && field_for_user.chekSetShipForField(ship_user_8,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_8,x,y,horizontal)
            count -= 1
            number_decks.text = "1"
            array_x[8] = x
            array_y[8] = y
            array_horizontal[8] = horizontal
        }
        if (count == 2 && field_for_user.chekSetShipForField(ship_user_9,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_9,x,y,horizontal)
            count -= 1
            number_decks.text = "1"
            array_x[9] = x
            array_y[9] = y
            array_horizontal[9] = horizontal
        }
        if (count == 1 && field_for_user.chekSetShipForField(ship_user_10,x,y,horizontal)){
            field_for_user.setShipForField(ship_user_10,x,y,horizontal)
            count -= 1
            number_decks.text = "Stop"
            array_x[10] = x
            array_y[10] = y
            array_horizontal[10] = horizontal
        }

        field_user_player.text = field_for_user.clean()
        field_user_player.text = field_for_user.setTextInvMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_ship)

        set_ship.setOnClickListener(this::setting_ship)
        start.setOnClickListener {
            val Intent_to_plat = Intent(this, MainActivity::class.java)
            Intent_to_plat.putExtra("field_info",field_user_player.text.toString())
            Intent_to_plat.putExtra("info_field_user",field_for_user.getInviibleMap())
            Intent_to_plat.putExtra("array_x",array_x)
            Intent_to_plat.putExtra("array_y",array_y)
            Intent_to_plat.putExtra("array_horizontal",array_horizontal)
            startActivity(Intent_to_plat)
        }

    }
    
}