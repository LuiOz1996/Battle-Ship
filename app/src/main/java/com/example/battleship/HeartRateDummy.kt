package com.example.battleship

class HeartRateDummy : IHeartRateProvider {
    var defaultBpm = 80
    override fun getCurrentBpm(): Int {
        return defaultBpm++;
    }
}