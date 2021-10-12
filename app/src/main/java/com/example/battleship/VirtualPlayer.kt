class VirtualPlayer(private var field: Field, private var myField: Field) {
    var shots = mutableListOf<Array<Int>>()
    private var shotsCounter = 0
    private var tryCounter = 0
    private var counter = 0
    private var heartRate = false


    private fun shoot1(): Int{
        counter ++

        var x:Int
        var y:Int
        var shot:Int

        while (true){
            if (field.shipCounter == 0){
                return -2
            }
            x = (1..10).random()
            y = (1..10).random()
            shot = field.shoot(x, y)
            if (shot != 2){
                if (shot == 1) {
                    shots.add(arrayOf(x, y))
                    shotsCounter ++
                    Thread.sleep(1_000)
                    return shoot2()
                }
                if (shot == 0){
                    Thread.sleep(1_000)
                    return shot
                }
                if (shot == -1){
                    Thread.sleep(1_000)
                    return shot
                }
                break
            }

            if (field.visibleMap[y][x-1] > -1){
                x--
                shot = field.shoot(x, y)
            } else if (field.visibleMap[y][x+1] > -1){
                x++
                shot = field.shoot(x, y)
            } else if (field.visibleMap[y-1][x-1] > -1){
                x--
                y--
                shot = field.shoot(x, y)
            } else if (field.visibleMap[y+1][x-1] > -1){
                x--
                y++
                shot = field.shoot(x, y)
            } else if (field.visibleMap[y-1][x+1] > -1){
                x++
                y--
                shot =  field.shoot(x, y)
            } else if (field.visibleMap[y+1][x] > -1){
                y++
                shot = field.shoot(x, y)
            } else if (field.visibleMap[y-1][x] > -1){
                y--
                shot =  field.shoot(x, y)
            } else if (field.visibleMap[y+1][x+1] > -1) {
                x++
                y++
                shot = field.shoot(x, y)
            }
            Thread.sleep(1_000)

            if (shot == 1) {
                shots.add(arrayOf(x, y))
                shotsCounter ++
                return shoot2()
            }

            if (shot != 2){
                return shot
            }


        }

        return shot

    }

    private fun shoot2():Int{
        counter ++
        var shot = 0
        var x = 0
        var y = 0

        when (tryCounter){
            0 -> {
                x = shots[shots.size-1][0] - 1
                y = shots[shots.size-1][1]
            }

            1 -> {
                x = shots[shots.size-1][0]
                y = shots[shots.size-1][1] - 1
            }

            2 -> {
                x = shots[shots.size-1][0] + 1
                y = shots[shots.size-1][1]
            }

            3 -> {
                x = shots[shots.size-1][0]
                y = shots[shots.size-1][1] + 1
            }
        }

        shot = field.shoot(x, y)
        Thread.sleep(1_000)



//        После каждого такого присваивания обновлять карту
//        Не накладывать никаких эффектов и анимаций при значениях 0 и 2

        when (shot){

            2 -> {
                if (heartRate){
                    shotsCounter--
                    return shoot1()
                }
                if (shots.size > 1){
                    shotsCounter++
                    return shoot3()
                }
                tryCounter++
                return shoot2()
            }

            1 -> {
                shots.add(arrayOf(x, y))
                return shoot2()
            }

            -1 -> {
                tryCounter = 0
                shotsCounter = 0
                shots.clear()
                return shoot1()
            }

            0 -> {
                if (shots.size == 1){
                    tryCounter++
                } else {
                    if (heartRate){
                        tryCounter = 0
                        shotsCounter = 0
                        return shot
                    }
                    shotsCounter++
                }
            }
        }

        return shot
    }

    private fun shoot3(): Int{
        counter ++
        var shot = 0
        var x = 0
        var y = 0

        when (tryCounter) {
            0 -> {
                x = shots[0][0] + 1
                y = shots[0][1]
                tryCounter = 2
            }

            1 -> {
                x = shots[0][0]
                y = shots[0][1] + 1
                tryCounter = 3
            }

        }

        shotsCounter--

        shot = field.shoot(x, y)
        Thread.sleep(1_000)


        if (shot == 1){
            shots.add(arrayOf(x,y))
            return shoot2()
        }

        if (shot == -1){
            shots.clear()
            tryCounter = 0
            shotsCounter = 0
        }

        return shot

    }

    fun getHeard(heart:Int):Boolean{
        if(heart > 90){
            return true
        }
        return false
    }

    fun run(heartRate:Boolean):Int{
        this.heartRate = heartRate
        var shot = 1
        while (shot != 0) {
            if (shot == -2){
                break
            }
            when (shotsCounter) {
                0 -> shot = shoot1()
                1 -> shot = shoot2()
                2 -> shot = shoot3()
            }
        }
        return shot
    }

}