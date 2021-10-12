class Field {

//    Массив это и есть поле(10x10) 12x12 чтобы избежать "out of range" при проверке chek_set_ship
//    описывает реальное состояние поля
//    0 пустая клетка
//    1 стоит корабль
//    -1 простреленная область поля (или область в которую стрелать бесполезно)
//    -2 простреленная часть коробля
//    visible_mat - это поле которое видно противнику, тоесть изначально пусто,
//    а потом после ходов появляются новые и новые значени

    var invisible_mat = Array(12, {i -> Array(12, {i -> 0})})
    var visible_mat = Array(12, {i -> Array(12, {i -> 0})})
    var ships = mutableListOf<Ship>()
    var ship_counter = 0


//    Проверяет можно ли поставить корабль в данное место
//    На вход принимает объект "корабль"
//    Возвращает true или false

    public fun chek_set_ship(ship: Ship): Boolean {

        val x = ship.x
        val y = ship.y
        val deck = ship.deck
        val horizontal = ship.horizontal

        if (horizontal){

            if (y < 1 || y > 10){
                return false
            }

            if (x < 1 || x > 10-deck) {
                return false
            }

            for (i in -1..deck){
                for (j in -1..1) {
                    if (invisible_mat[y+j][x+i] == 1) {
                        return false
                    }
                }
            }

            for (i in x..x+deck-1){
                invisible_mat[y][i] = 1
            }

            return true
        } else {

            if (x < 1 || x > 10){
                return false
            }

            if (y < 1 || y > 10-deck) {
                return false
            }

            for (i in -1..deck){
                for (j in -1..1) {
                    if (invisible_mat[y+i][x+j] == 1) {
                        return false
                    }
                }
            }

            for (i in y..y+deck-1){
                invisible_mat[i][x] = 1
            }

            return true
        }

    }


//    Ставит корабль в данное место, но вначале вызывает предыдущую функцию для проверки
//    На вход принимает объект "корабль"
//    Возвращает true(если поставила) или false(если это не возможно)

    public fun set_ship(ship: Ship): Boolean{

        if (!chek_set_ship(ship)){
            ship.x = -1
            ship.y = -1
            return false
        }

        val x = ship.x
        val y = ship.y
        val deck = ship.deck
        val horizontal = ship.horizontal

        if (horizontal){

            for (i in x..x+deck-1){
                invisible_mat[y][i] = 1
            }

        } else {

            for (i in y..y+deck-1){
                invisible_mat[i][x] = 1
            }

        }

        ships.add(ship)
        ship_counter ++

        return true

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
    public fun shoot(x:Int, y:Int):Int {
        if (invisible_mat[y][x] != 1 && invisible_mat[y][x] != 0){
            return 2
        }
        if (invisible_mat[y][x] == 1){
            for (i in ships){
                var shot = i.shoot(x, y)
                if ( shot != 0){
                    if (shot == -1){
                        ship_counter --
                    }
                    return shot
                }
            }
        }

        invisible_mat[y][x] = -1
        visible_mat[y][x] = -1
        return 0
    }

    public fun ready_to_fight(){
        for (i in 0..11){
            visible_mat[0][i] = -1
            visible_mat[i][0] = -1
            invisible_mat[0][i] = -1
            invisible_mat[i][0] = -1
        }
    }


}