import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import java.awt.Color
import java.awt.Graphics2D
import java.util.Vector
import java.util.*



class Hero @JvmOverloads constructor(var posX: Int, var posY: Int, var size: Int, initialVelocity: Vector? = Vector()) {
    val speed = 10 // Max distance per tick
    var vel // velocity
            : Vector? = null
    var acc // acceleration
            : Vector
    var maxVel = 20.0
    var maxForce = 1.5

    init {
        vel = initialVelocity?.let { Vector(it) } ?: java.util.Vector(0.0, 0.0)
        acc = java.util.Vector(0.0, 0.0)
        if (vel!!.getMag() > maxVel) {
            vel!!.setMag(maxVel)
        }
    }

    fun draw(g: Graphics2D) {
        val centerX = WINDOW_WIDTH / 2
        val centerY = WINDOW_HEIGHT / 2
        // Draw the hero always in the center of the screen
        g.color = Color.blue
        g.fillOval( centerX - this.size/2, centerY - this.size/2, this.size, this.size)
        g.color = Color.white
        g.fillOval( centerX - 10, centerY - 10, 20, 20)
        g.color = Color.red
        g.fillOval( centerX - 5, centerY - 5, 10, 10)
    }

    fun isColliding(e: Enemy): Boolean {
        // Computes the distance between the hero and the enemy
        val distance = Math.sqrt(Math.pow((posX - e.x).toDouble(), 2.0) + Math.pow((posY - e.y).toDouble(), 2.0))
        // If the distance is less than the sum of the radius, the hero is colliding with the enemy
        return distance < (size / 2 + e.size / 2)
    }

    fun moveUp() {
        posY -= speed
    }
    fun moveDown() {
        posY += speed
    }
    fun moveLeft() {
        posX -= speed
    }
    fun moveRight() {
        posX += speed
    }
    fun moveUpLeft() {
        val speedX = speed * Math.cos(Math.PI / 4)
        val speedY = speed * Math.sin(Math.PI / 4)
        posX -= speedX.toInt()
        posY -= speedY.toInt()
    }
    fun moveUpRight() {
        val speedX = speed * Math.cos(Math.PI / 4)
        val speedY = speed * Math.sin(Math.PI / 4)
        posX += speedX.toInt()
        posY -= speedY.toInt()
    }
    fun moveDownLeft() {
        val speedX = speed * Math.cos(Math.PI / 4)
        val speedY = speed * Math.sin(Math.PI / 4)
        posX -= speedX.toInt()
        posY += speedY.toInt()
    }
    fun moveDownRight() {
        val speedX = speed * Math.cos(Math.PI / 4)
        val speedY = speed * Math.sin(Math.PI / 4)
        posX += speedX.toInt()
        posY += speedY.toInt()
    }
}