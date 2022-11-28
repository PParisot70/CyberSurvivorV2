import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import Renderer.xps

class Hero @JvmOverloads constructor(var posX: Int, var posY: Int, var size: Int, initialVelocity: Vector? = Vector()) {
    var vel // velocity
            : Vector? = null
    var acc // acceleration
            : Vector
    var maxVel = 20.0
    var maxForce = 1.5
    var mass = 1.0
    val speed = 10 // Max distance per tick
    var exp = 0
    var level = 0
    var expnextLevel = 5
    var magnetsize = 200


    init {
        vel = initialVelocity?.let { Vector(it) } ?: Vector(0.0, 0.0)
        acc = Vector(0.0, 0.0)
        if (vel!!.getMag() > maxVel) {
            vel!!.setMag(maxVel)
        }
    }




    fun ApplyForce(forceVector: Vector) {
        var forceVector = forceVector
        forceVector = Vector(forceVector).divide(mass)
        if (forceVector.getMag() > maxForce) {
            forceVector.setMag(maxForce)
        }
        acc.add(forceVector)
    }




    fun GetSteeringForce(x: Double, y: Double): Vector {
        var desiredMag = CalculateDistance(this.posX.toDouble(), this.posY.toDouble(), x, y)
        if (desiredMag > maxVel) {
            desiredMag = maxVel
        }
        val desiredAngle = Math.atan2(this.posY - y, x - this.posX)
        val desiredVel = Vector(desiredMag, desiredAngle, true)

        return Vector.Subtract(desiredVel, vel!!)
    }


    companion object {
        private fun CalculateDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
            return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0))
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
        g.color = Color.black
        g.drawString("$exp", 25 , 25  )
    }

    fun isColliding(e: Enemy): Boolean {
        // Computes the distance between the hero and the enemy
        val distance = Math.sqrt(Math.pow((posX - e.x).toDouble(), 2.0) + Math.pow((posY - e.y).toDouble(), 2.0))
        // If the distance is less than the sum of the radius, the hero is colliding with the enemy
        return distance < (size / 2 + e.size / 2)
    }

    fun isCollidingxp(e: Experience): Boolean {
        // Computes the distance between the hero and the enemy
        val distance = Math.sqrt(Math.pow((posX - e.x).toDouble(), 2.0) + Math.pow((posY - e.y).toDouble(), 2.0))
        // If the distance is less than the sum of the radius, the hero is colliding with the enemy
        if (distance < (size / 2 + e.size / 2)){
            getXp(e)
            return true
        }
        return false
    }

    fun moveUp() {
        ApplyForce(GetSteeringForce(10.0, 0.0))
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


    fun getXp(xp : Experience){
        exp += xp.amount
        if (exp >= expnextLevel){
            exp -= expnextLevel
            LevelUp()
        }
    }

    fun magnet(){
        xps.forEach() {
            var dis = Math.sqrt(Math.pow((posX - it.x).toDouble(), 2.0) + Math.pow((posY - it.y).toDouble(), 2.0))
            if (dis < magnetsize)
            {
                it.comportement = Behavior.Pursue
            }
            }
        }


    fun LevelUp(){

    }


}

