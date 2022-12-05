
enum class Direction {
    NORTH, SOUTH, WEST, EAST,
    NORTH_WEST, NORTH_EAST,
    SOUTH_WEST, SOUTH_EAST
}

abstract class Entity(var posX: Int, var posY: Int , var size :Int , ):Drawable {
    var speed : Int = 10
    var lastMove: Direction = Direction.SOUTH
    var lastMoveTimestamp: Long = 0

    fun distanceTo(other: Entity): Double {
        val dx = other.posX - posX
        val dy = other.posY - posY
        return Math.sqrt((dx * dx + dy * dy).toDouble())
    }

    fun isColliding(e: Entity): Boolean {
        // Computes the distance between the hero and the enemy
        val distance = Math.sqrt(Math.pow((posX - e.posX).toDouble(), 2.0) + Math.pow((posY - e.posY).toDouble(), 2.0))
        // If the distance is less than the sum of the radius, the hero is colliding with the enemy
        return distance < (size / 2 + e.size / 2)
    }


    fun isMoving(): Boolean {
        return System.currentTimeMillis() - lastMoveTimestamp < 100
    }

    fun moveUp() {
        posY -= speed
        lastMove = Direction.NORTH
        lastMoveTimestamp = System.currentTimeMillis()
    }
    fun moveDown() {
        posY += speed
        lastMove = Direction.SOUTH
        lastMoveTimestamp = System.currentTimeMillis()
    }
    fun moveLeft() {
        posX -= speed
        lastMove = Direction.WEST
        lastMoveTimestamp = System.currentTimeMillis()
    }
    fun moveRight() {
        posX += speed
        lastMove = Direction.EAST
        lastMoveTimestamp = System.currentTimeMillis()
    }
    fun moveUpLeft() {
        val speedX = speed * Math.cos(Math.PI / 4)
        val speedY = speed * Math.sin(Math.PI / 4)
        posX -= speedX.toInt()
        posY -= speedY.toInt()
        lastMove = Direction.NORTH_WEST
        lastMoveTimestamp = System.currentTimeMillis()
    }
    fun moveUpRight() {
        val speedX = speed * Math.cos(Math.PI / 4)
        val speedY = speed * Math.sin(Math.PI / 4)
        posX += speedX.toInt()
        posY -= speedY.toInt()
        lastMove = Direction.NORTH_EAST
        lastMoveTimestamp = System.currentTimeMillis()
    }
    fun moveDownLeft() {
        val speedX = speed * Math.cos(Math.PI / 4)
        val speedY = speed * Math.sin(Math.PI / 4)
        posX -= speedX.toInt()
        posY += speedY.toInt()
        lastMove = Direction.SOUTH_WEST
        lastMoveTimestamp = System.currentTimeMillis()
    }
    fun moveDownRight() {
        val speedX = speed * Math.cos(Math.PI / 4)
        val speedY = speed * Math.sin(Math.PI / 4)
        posX += speedX.toInt()
        posY += speedY.toInt()
        lastMove = Direction.SOUTH_EAST
        lastMoveTimestamp = System.currentTimeMillis()
    }
}