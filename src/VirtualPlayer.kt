class VirtualPlayer {
    var shots = mutableListOf<Array<Int>>()
    var shots_counter = 1
    var field:Field

    constructor(field: Field){
        this.field = field
    }

    private fun shoot1(): Int{

        var x:Int
        var y:Int
        var shot:Int

        while (true){
            x = (0..10).random()
            y = (0..10).random()
            shot = field.shoot(x, y)
            if (shot != 2){
                break
            }
        }

        when (shot){
            1 -> shots_counter ++
            -1 -> shots_counter = 0
        }

        return shot

    }

//    private fun shoot2():Int{
//        return shot
//    }



}