fun main() {

    var field = Field()
    var ship1 = Ship(4, field,1, 1)
    var ship2 = Ship(3, field,3, 3, false)
    var ship3 = Ship(2, field,1, 6)

    print(field.set_ship(ship1))
    print(" ")
    print(field.set_ship(ship2))
    print(" ")
    println(field.set_ship(ship3))

    var shot = 0

    while (shot != -2){

        val shoot_line = readLine()

        if (shoot_line != null){

            val shoot_coordinates = shoot_line.split(' ')

            if (shoot_coordinates.size == 2) {
                val x = shoot_coordinates[0].toInt()
                val y = shoot_coordinates[1].toInt()

                shot = field.shoot(x, y)

                for (i in 1..10) {
                    for (j in 1..10){
                        print(String.format("%2d ", field.visible_mat[i][j]))
                    }
                    println()
                }
            }
        }
    }


}