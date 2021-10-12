class Ship(var id: Int, var deck: Int, private val field: Field) {
    var x: Int = -1
    var y: Int = -1
    var horizontal: Boolean = true
    private var decks = arrayOf(false, false, false, false) //Статус его палуб, чтоб у него было состояние "подбит"

    //Все корабли будут создаваться "Доком" или "Верфью" с изначальными координатами -1,-1
    // При перетаскивании на поле будут получать новые координаты носа

    init {
        for (i in 0 until deck) {
            this.decks[i] = true
        }
    }


//    Функция выстрела
//    Принимает координаты точки выстрела и возвращает:
//    0 если не попал
//    1 если ранил
//    -1 если убил

    fun shoot(x1: Int, y1: Int): Int {
        if (horizontal) {

            decks[x1 - x] = false

            for (i in decks) {
                if (i) {
                    field.invisibleMap[y1][x1] = -2
                    field.visibleMap[y1][x1] = -2
                    return 1
                }
            }

            for (i in -1..deck) {
                for (j in -1..1) {
                    field.invisibleMap[y + j][x + i] = -1
                    field.visibleMap[y + j][x + i] = -1
                }
            }

            for (i in 0 until deck) {
                field.invisibleMap[y][x + i] = -2
                field.visibleMap[y][x + i] = -2
            }

            return -1

        } else {

            decks[y1 - y] = false

            for (i in decks) {
                if (i) {
                    field.invisibleMap[y1][x1] = -2
                    field.visibleMap[y1][x1] = -2
                    return 1
                }
            }

            for (i in -1..deck) {
                for (j in -1..1) {
                    field.invisibleMap[y + i][x + j] = -1
                    field.visibleMap[y + i][x + j] = -1
                }
            }

            for (i in 0 until deck) {
                field.invisibleMap[y + i][x] = -2
                field.visibleMap[y + i][x] = -2
            }

            return -1

        }
    }
}
