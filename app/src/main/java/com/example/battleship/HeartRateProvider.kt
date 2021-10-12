package com.example.battleship

import android.icu.lang.UScript
import android.view.SurfaceView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.heartrateometer.HeartRateOmeter

class HeartRateProvider : IHeartRateProvider {
    var bpmCounter = 70
    constructor(surfaceView: SurfaceView)
    {
        HeartRateOmeter()
            .bpmUpdates(surfaceView)
            .subscribe(
                { bpm: HeartRateOmeter.Bpm -> bpmCounter = bpm?.value })
    }
    override fun getCurrentBpm(): Int {
        return bpmCounter
    }

}