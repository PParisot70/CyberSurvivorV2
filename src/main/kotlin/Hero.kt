import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH

class Hero @JvmOverloads constructor(var posX: Int, var posY: Int, var size: Int, initialVelocity: Vector? = Vector()) {
    var vel // velocity
            : Vector? = null
    var acc // acceleration
            : Vector
    var maxVel = 20.0
    var maxForce = 1.5
    var mass = 1.0
    var pathLength = 50
    var path = LinkedList<IntArray>()
    val speed = 10 // Max distance per tick





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

    fun Update(x1: Int, x2: Int, y1: Int, y2: Int) {
        vel!!.add(acc)
        if (vel!!.getMag() > maxVel) vel!!.setMag(maxVel)
        posX += vel!!.getXMag().toInt()
        posY -= vel!!.getYMag().toInt()
        acc.setMag(0.0)



        // Add the current position to the path list
        if (path.size >= pathLength) {
            path.poll()
        }
        if (!path.isEmpty()
            && (Math.abs(posX - path.last[0]) > Math.abs(vel!!.getXMag())
                    || Math.abs(posY - path.last[1]) > Math.abs(vel!!.getYMag()))
        ) {
            // Add [-1, -1] to indicate the end of the previous path line and start a new
            // line
            path.poll()
            path.add(intArrayOf(-1, -1))
        }
        path.add(intArrayOf(posX, posY))
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

        fun CheckCollision(v1: Enemy, v2: Enemy): Boolean {
            return Math.pow((v1.x - v2.x).toDouble(), 2.0) + Math.pow(
                (v1.y - v2.y).toDouble(),
                2.0
            ) < Math.pow((v2.size + v1.size).toDouble(), 2.0)
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
}

