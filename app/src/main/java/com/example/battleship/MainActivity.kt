package com.example.battleship

import Field
import Ship
import VirtualPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
//import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.heartrateometer.HeartRateOmeter
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    val REQ_CODE = 1

    var field_for_virtual = Field()
    var field_for_user = Field()
    val virtual_player = VirtualPlayer(field_for_user,field_for_virtual)
    private lateinit var heartRateProvider: IHeartRateProvider

    var shot = 1

    @Suppress("UNCHECKED_CAST")
    private fun play(v: View) {
        val passedData = intent.extras
        val Map: Array<Array<Int>> = passedData!!.get("info_field_user") as Array<Array<Int>>
        val info = intent.extras
        val array_x:Array<Int> = info!!.get("array_x") as Array<Int>
        val infoinfo = intent.extras
        val array_y:Array<Int> = infoinfo!!.get("array_y") as Array<Int>
        val infoinfoinfo = intent.extras
        val array_horizontal:Array<Boolean> = infoinfoinfo!!.get("array_horizontal") as Array<Boolean>

        heartRateProvider = HeartRateProvider(preview)

        // Расстоновка кароблей пользователя
        for (i in 0..11){
            for(j in 0..11){
                field_for_user.invisibleMap[i][j] = Map[i][j]
            }
        }

        // Расстоновка кароблей
        field_for_virtual.autoFill()
        field_for_user.autoFillForField(array_x,array_y,array_horizontal)
        // Подготовка к бою
        field_for_user.readyToFight()
        field_for_virtual.readyToFight()

        field_user.text = field_for_user.setTextInvMap()
        field_virtual_player.text = field_for_virtual.setTextVMap()
    }

    @Suppress("UNCHECKED_CAST")
    private fun shoot_for_ship(v:View){
        val x = x.text.toString().toInt()
        val y = y.text.toString().toInt()

        val heart:Int = heartRateProvider.getCurrentBpm()


        field_for_virtual.clean()
        field_for_user.clean()

        if(!field_for_user.endOfGame() && !field_for_virtual.endOfGame()) {

            shot = field_for_virtual.shoot(x, y)
            field_virtual_player.text = (field_for_virtual.setTextVMap())
            if(shot == 1) {
                field_user.text = (field_for_user.setTextInvMap())
            }
            else{
                virtual_player.run(virtual_player.getHeard(heart))
                field_user.text = (field_for_user.setTextInvMap())
            }
        }
        if(field_for_user.endOfGame()){
            field_for_virtual.clean()
            field_virtual_player.text = "You Lose"
        }
        if(field_for_virtual.endOfGame()){
            field_for_virtual.clean()
            field_virtual_player.text = "You win"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Play.setOnClickListener(this::play)
        Shoot.setOnClickListener(this::shoot_for_ship)

        val status = ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),REQ_CODE)
    }
}

