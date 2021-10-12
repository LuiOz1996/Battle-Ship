import kotlin.Array as Array

class Field {

//    Массив это и есть поле(10x10) 12x12 чтобы избежать "out of range" при проверке chek_set_ship
//    описывает реальное состояние поля
//    0 пустая клетка
//    1 стоит корабль
//    -1 простреленная область поля (или область в которую стрелать бесполезно)
//    -2 простреленная часть коробля
//    visible_mat - это поле которое видно противнику, тоесть изначально пусто,
//    а потом после ходов появляются новые и новые значени

    var invisibleMap = Array(12) { Array(12) { 0 } }
    var visibleMap = Array(12) { Array(12) { 0 } }

    val ships = mutableListOf<Ship>()
    var shipCounter = 10
    var buffer: String =""

    init {
        dropField()
    }

    fun getInviibleMap(): Array<Array<Int>>{
        return this.invisibleMap
    }

    private fun dropField() {
        if (ships.isNotEmpty()) {
            ships.clear()
        }

        ships.add(Ship(1,4 , this))

        for (i in 2..3) {
            ships.add(Ship(i, 3, this))
        }

        for (i in 4..6) {
            ships.add(Ship(i,2, this))
        }

        for (i in 7..10) {
            ships.add(Ship(i,1, this))
        }
    }


//    Проверяет можно ли поставить корабль в данное место
//    На вход принимает объект "корабль"
//    Возвращает true или false

    private  fun chekSetShip(ship: Ship): Boolean {

        val x = ship.x
        val y = ship.y
        val deck = ship.deck
        val horizontal = ship.horizontal


        if (horizontal) {

            if (y < 1 || y > 10) {
                return false
            }

            if (x < 1 || x > 10 - deck) {
                return false
            }

            for (i in -1..deck) {
                for (j in -1..1) {
                    if (invisibleMap[y + j][x + i] != 0) {
                        return false
                    }
                }
            }

            for (i in x until x + deck) {
                invisibleMap[y][i] = 1
            }

            return true
        } else {

            if (x < 1 || x > 10) {
                return false
            }

            if (y < 1 || y > 10 - deck) {
                return false
            }

            for (i in -1..deck) {
                for (j in -1..1) {
                    if (invisibleMap[y + i][x + j] != 0) {
                        return false
                    }
                }
            }

            for (i in y until y + deck) {
                invisibleMap[i][x] = 1
            }

            return true
        }

    }

    fun chekSetShipForField(ship: Ship, x:Int, y:Int, horizontal: Boolean): Boolean {

        val deck = ship.deck

        if (horizontal) {

            if (y < 1 || y > 10) {
                return false
            }

            if (x < 1 || x > 10 - deck) {
                return false
            }

            for (i in -1..deck) {
                for (j in -1..1) {
                    if (invisibleMap[y + j][x + i] != 0) {
                        return false
                    }
                }
            }

            for (i in x until x + deck) {
                invisibleMap[y][i] = 1
            }

            return true
        } else {

            if (x < 1 || x > 10) {
                return false
            }

            if (y < 1 || y > 10 - deck) {
                return false
            }

            for (i in -1..deck) {
                for (j in -1..1) {
                    if (invisibleMap[y + i][x + j] != 0) {
                        return false
                    }
                }
            }

            for (i in y until y + deck) {
                invisibleMap[i][x] = 1
            }

            return true
        }

    }

//    Ставит корабль в данное место, но вначале вызывает предыдущую функцию для проверки
//    На вход принимает объект "корабль"
//    Возвращает true(если поставила) или false(если это не возможно)

    fun setShip(ship: Ship, x1: Int = 0, y1:Int = 0): Boolean {

        ship.x += x1
        ship.y += y1

        if (!chekSetShip(ship)) {
            ship.x = -1
            ship.y = -1
            return false
        }

        val x = ship.x
        val y = ship.y
        val deck = ship.deck
        val horizontal = ship.horizontal

        if (horizontal) {

            for (i in x until x + deck) {
                invisibleMap[y][i] = ship.id
            }

        } else {

            for (i in y until y + deck) {
                invisibleMap[i][x] = ship.id
            }

        }

        shipCounter++

        return true

    }
    fun setShipForField(ship: Ship, x: Int, y:Int,horizontal:Boolean) {
        val deck = ship.deck

        if (horizontal) {

            for (i in x until x + deck) {
                invisibleMap[y][i] = ship.id
            }

        } else {

            for (i in y until y + deck) {
                invisibleMap[i][x] = ship.id
            }

        }
    }


//    Функция выстрела в это поле
//    Принимате координаты выстрела и передаёт на все корабли спрашивая в них ли попали?
//    (при условии что там стоит корабль)
//    Выводит
//    2 если выстрел сделан в заведомо бесполезное место
//    0 если мимо
//    1 если ранен
//    -1 если убил
//    -2 если партия окончена

    fun shoot(x: Int, y: Int): Int {
        if (invisibleMap[y][x] < 0) {
            return 2
        }
        if (invisibleMap[y][x] == 0){
            invisibleMap[y][x] = -1
            visibleMap[y][x] = -1
            return 0
        }


        val shot =  ships[invisibleMap[y][x]-1].shoot(x, y)
        if (shot == -1) {
            shipCounter --
            if (shipCounter == 0){
                return -2
            }
        }
        return shot
    }


//    Покрывает область в одну клетку вокруг поля
//    -1ми

    fun readyToFight() {
        for (i in 0..11) {
            visibleMap[0][i] = -1
            visibleMap[i][0] = -1
            visibleMap[visibleMap.size-1][i] = -1
            visibleMap[i][visibleMap.size-1] = -1
            invisibleMap[0][i] = -1
            invisibleMap[i][0] = -1
            invisibleMap[invisibleMap.size-1][i] = -1
            invisibleMap[i][invisibleMap.size-1] = -1
        }
    }


    fun autoFill() {
        shipCounter = 0
        for (i in 0..11) {
            for (j in 0..11) {
                invisibleMap[i][j] = 0
            }
        }

        dropField()

        var counter = 0

        for (i in ships) {
            var state = false
            while (!state) {
                i.x = (1..10).random()
                i.y = (1..10).random()
                i.horizontal = (0..1).random() != 0
                state = setShip(i)
                counter ++
                if (counter > 100){
                    break
                }
            }
            if (counter > 100){
                break
            }
        }

        if (counter > 100){
            autoFill()

        }

    }

    fun autoFillForField(x:Array<Int>,y:Array<Int>,horizontal: Array<Boolean>) {

        this.dropField()

        var j = 1

        for (i in ships) {
            i.x = x[j]
            i.y = y[j]
            i.horizontal = horizontal[j]
            j+=1
        }

    }

    fun endOfGame(): Boolean{
        if (shipCounter < 1) {
            return true
        }

        return false
    }

    fun setTextInvMap():String{
        for (i in 0..10){
            for(j in 0..10){
                if(i == 0 && j==0){ buffer += "         "}
                if(i==0 && j>0){
                    if(j==10){
                        buffer += "$j   "
                    }
                    else{
                        buffer += "$j    "
                    }
                }
                if(i> 0 && j==0){
                    if(i==10){
                        buffer += "$i   "
                    }
                    else {
                        buffer += "$i     "
                    }
                }
                if(i>0 && j>0){
                    if( invisibleMap[i][j] <0 || invisibleMap[i][j]==10){
                        this.buffer += (invisibleMap[i][j].toString() + "   ")
                    }
                    else{
                        this.buffer += (invisibleMap[i][j].toString() + "    ")
                    }
                }
            }
            this.buffer+=("\n")
        }
        return buffer
    }

    fun setTextVMap(): String{
        for (i in 0..10){
            for(j in 0..10){
                if(i == 0 && j==0){ buffer += "        "}
                if(i==0 && j>0){
                    if(j==10){
                        buffer += "$j  "
                    }
                    else{
                        buffer += "$j   "
                    }
                }
                if(i> 0 && j==0){
                    if(i==10){
                        buffer += "$i  "
                    }
                    else {
                        buffer += "$i    "
                    }
                }
                if(i>0 && j>0){
                    if( visibleMap[i][j] < 0 || visibleMap[i][j] ==10){
                        this.buffer += (visibleMap[i][j].toString() + "  ")
                    }
                    else{
                        this.buffer += (visibleMap[i][j].toString() + "   ")
                    }
                }
            }
            this.buffer+=("\n")
        }
        return buffer
    }

    fun clean():String{
        this.buffer = ""
        return buffer
    }


}