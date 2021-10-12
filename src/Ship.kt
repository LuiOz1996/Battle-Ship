class Ship {

    var deck: Int // Длинна от 1 до 4
    var horizontal = true // Стоит он по горизонтали или нет
    var x:Int // x,y - координаты носа корорбля (самой верхней левой его клетки)
    var y:Int
    var decks = arrayOf(false, false, false, false) //Статус его палуб, чтоб у него было состояние "подбит"
    val field:Field // Полек к которому он принадлежит (поле игрока или поле компьютера)

    //Все корабли будут создаваться "Доком" или "Верфью" с изначальными координатами -1,-1
    // При перетаскивании на поле будут получать новые координаты носа

    constructor(deck: Int, field: Field, x:Int = -1, y:Int = -1, horizontal:Boolean = true){
        this.x = x
        this.y = y
        this.horizontal = horizontal
        this.deck = deck
        for (i in 0..deck-1) {
            this.decks[i] = true
        }
        this.field = field
    }


//    Функция выстрела
//    Принимает координаты точки выстрела и возвращает:
//    0 если не попал
//    1 если ранил
//    -1 если убил

    public fun shoot(x1:Int, y1:Int): Int {
        if (horizontal) {
            if (y != y1) {
                return 0
            }

            if (x1 < x || x1 > x + deck -1){
                return 0
            }

            decks[x1-x] = false

            for (i in decks){
                if (i){
                    field.invisible_mat[y1][x1]= -2
                    field.visible_mat[y1][x1]= -2
                    return 1
                }
            }

            for (i in -1..deck){
                for (j in -1..1) {
                    field.invisible_mat[y+j][x+i] = -1
                    field.visible_mat[y+j][x+i] = -1
                }
            }

            for (i in 0..deck-1){
                field.invisible_mat[y][x+i] = -2
                field.visible_mat[y][x+i] = -2
            }

            return -1

        } else {

            if (x != x1) {
                return 0
            }

            if (y1 < y || x1 > y + deck -1){
                return 0
            }

            decks[y1-y] = true

            for (i in decks){
                if (i){
                    field.invisible_mat[y1][x1]= -2
                    field.visible_mat[y1][x1]= -2
                    return 1
                }
            }

            for (i in -1..deck){
                for (j in -1..1) {
                    field.invisible_mat[y+i][x+j] = -1
                    field.visible_mat[y+i][x+j] = -1
                }
            }

            for (i in 0..deck-1){
                field.invisible_mat[y+i][x] = -2
                field.visible_mat[y+i][x] = -2
            }

            return -1

        }
    }



}