import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import Renderer.hero

class Enemy @JvmOverloads constructor(var x: Int = 0, var y: Int = 0, initialVelocity: Vector? = Vector()) {
    var vel // velocity
            : Vector? = null
    var acc // acceleration
            : Vector
    var maxVel = 4.0
    var maxForce = 0.5
    var mass = 1.0
    var size = 20
    var pathLength = 50
    var path = LinkedList<IntArray>()
    var color = Color.BLACK
    var defaultPredictionFactor = 5
    var target: Hero? = hero
    var comportement = Behavior.Seek
    /**
     * Constructs a new `Enemy` at the given postion and velocity
     * with the acceleration set to 0
     *
     * @param x               the x coordinates
     * @param y               the y coordinates
     * @param initialVelocity the initial velocity of the vehicle
     *
     * @see Vector
     */
    /**
     * Constructs a new `Enemy` at the given postion with the velocity and
     * acceleration set to 0
     *
     * @param x the x coordinates
     * @param y the y coordinates
     */
    /**
     * Constructs a new `Enemy` at (0, 0) with the velocity and acceleration
     * set to 0
     *
     */
    init {
        vel = initialVelocity?.let { Vector(it) } ?: Vector(0.0, 0.0)
        acc = Vector(0.0, 0.0)
        if (vel!!.getMag() > maxVel) {
            vel!!.setMag(maxVel)
        }
    }

    /***
     * Sets a random speed and position to the vehicle object
     *
     * @param v  the target object
     * @param x1 the minimum x position to the vehicle
     * @param y1 the minimum y position to the vehicle
     * @param x2 the maximum x position to the vehicle
     * @param y2 the maximum x position to the vehicle
     */
    fun Randomize(x1: Int, y1: Int, x2: Int, y2: Int) {
        RandomizePosition(x1, y1, x2, y2)
        RandomizeVelocity()
    }

    /***
     * Sets a random position to the vehicle object
     *
     * @param v  the target object
     * @param x1 the minimum x position to the vehicle
     * @param y1 the minimum y position to the vehicle
     * @param x2 the maximum x position to the vehicle
     * @param y2 the maximum x position to the vehicle
     */
    fun RandomizePosition(x1: Int, y1: Int, x2: Int, y2: Int) {
        x = ThreadLocalRandom.current().nextInt(x1, x2)
        y = ThreadLocalRandom.current().nextInt(y1, y2)
    }

    /**
     * Sets a random Velociy to the vehicle object
     */
    fun RandomizeVelocity() {
        vel!!.setMag(ThreadLocalRandom.current().nextInt((maxVel * 0.1).toInt(), maxVel.toInt() + 1).toDouble())
        vel!!.angleInDegrees = ThreadLocalRandom.current().nextInt(1, 360 + 1).toDouble()
    }

    fun ApplyForce(forceVector: Vector) {
        var forceVector = forceVector
        forceVector = Vector(forceVector).divide(mass)
        if (forceVector.getMag() > maxForce) {
            forceVector.setMag(maxForce)
        }
        acc.add(forceVector)
    }

    /***
     * Updates the position of the vehicle and limits the position to the given
     * boundary
     *
     * @param x1 the minimum x position to the vehicle
     * @param y1 the minimum y position to the vehicle
     * @param x2 the maximum x position to the vehicle
     * @param y2 the maximum x position to the vehicle
     */
    fun update() {
        if (target != null ) {
            when (comportement) {
                Behavior.Seek -> Seek(target!!.posX.toDouble(), target!!.posY.toDouble())
                Behavior.Flee -> Flee(target!!.posX.toDouble(), target!!.posY.toDouble())
                Behavior.Pursue -> Pursue(target)
                Behavior.Evade -> Evade(target)
                else -> throw IllegalArgumentException("behavior has an invalid value")
            }
        }
        vel!!.add(acc)
        if (vel!!.getMag() > maxVel) vel!!.setMag(maxVel)
        x += vel!!.getXMag().toInt()
        y -= vel!!.getYMag().toInt()
        acc.setMag(0.0)
        repulsion()




        // Add the current position to the path list
        if (path.size >= pathLength) {
            path.poll()
        }
        if (!path.isEmpty()
            && (Math.abs(x - path.last[0]) > Math.abs(vel!!.getXMag())
                    || Math.abs(y - path.last[1]) > Math.abs(vel!!.getYMag()))
        ) {
            // Add [-1, -1] to indicate the end of the previous path line and start a new
            // line
            path.poll()
            path.add(intArrayOf(-1, -1))
        }
        path.add(intArrayOf(x, y))
    }



    fun GetSteeringForce(x: Double, y: Double, flee: Boolean): Vector {
        var desiredMag = CalculateDistance(this.x.toDouble(), this.y.toDouble(), x, y)
        if (desiredMag > maxVel) {
            desiredMag = maxVel
        }
        val desiredAngle = Math.atan2(this.y - y, x - this.x)
        val desiredVel = Vector(desiredMag, desiredAngle, true)
        if (flee) {
            desiredVel.multiply(-1.0)
        }
        return Vector.Subtract(desiredVel, vel!!)
    }

    fun Seek(x: Double, y: Double) {
        ApplyForce(GetSteeringForce(x, y, false))
    }

    fun Flee(x: Double, y: Double) {
        ApplyForce(GetSteeringForce(x, y, true))
    }

    fun GetPredictedPosition(v: Hero?, predictionFactor: Int): IntArray {
        return intArrayOf(
            v!!.posX + (v.vel!!.getXMag() * predictionFactor).toInt(),
            v.posY - (v.vel!!.getYMag() * predictionFactor).toInt()
        )
    }

    @JvmOverloads
    fun Pursue(v: Hero?, predictionFactor: Int = defaultPredictionFactor) {
        val pos = GetPredictedPosition(v, predictionFactor)
        Seek(pos[0].toDouble(), pos[1].toDouble())
    }

    @JvmOverloads
    fun Evade(v: Hero?, predictionFactor: Int = defaultPredictionFactor) {
        val pos = GetPredictedPosition(v, predictionFactor)
        Flee(pos[0].toDouble(), pos[1].toDouble())
    }

    fun draw(heroX: Int, heroY: Int, g: Graphics2D) {
        g.color = color
        g.fillOval(x - heroX + WINDOW_WIDTH / 2 - size / 2, y - heroY + WINDOW_HEIGHT / 2 - size / 2, size, size)
        g.drawString(""+x+"  "+y+"" , x - heroX + WINDOW_WIDTH / 2 - size / 2 , y - heroY + WINDOW_HEIGHT / 2 - size / 2 - 4)
    }

    fun repulsion(){
        color = Color.black
        var filtre = Renderer.entities.filter{it != this}
        filtre.forEach{
      if (CheckCollision(it)){
          this.Flee((it.x).toDouble() , (it.y).toDouble())
      }
        }
    }


    fun CheckCollision(v2: Enemy): Boolean {
        return Math.pow((x - v2.x).toDouble(), 2.0) + Math.pow((y - v2.y).toDouble(), 2.0) < Math.pow((v2.size + size).toDouble(), 2.0)
    }
    companion object {
        private fun CalculateDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
            return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0))
        }


    }
}

