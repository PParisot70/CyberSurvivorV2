import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import Renderer.hero

class Experience @JvmOverloads constructor(var x: Int = 0, var y: Int = 0, initialVelocity: Vector? = Vector()) {
    var vel // velocity
            : Vector? = null
    var acc // acceleration
            : Vector
    var maxVel = 20.0
    var maxForce = 0.5
    var mass = 1.0
    var size = 10
    var defaultPredictionFactor = 5
    var target: Hero? = hero
    var comportement = Behavior.Wait
    var amount : Int = 1

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


    fun update() {
        if (target != null ) {
            when (comportement) {
                Behavior.Pursue -> Pursue(target)
                Behavior.Wait -> Wait()
                else -> throw IllegalArgumentException("behavior has an invalid value")
            }
        }
        vel!!.add(acc)
        if (vel!!.getMag() > maxVel) vel!!.setMag(maxVel)
        x += vel!!.getXMag().toInt()
        y -= vel!!.getYMag().toInt()
        acc.setMag(0.0)

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


    fun GetPredictedPosition(v: Hero?, predictionFactor: Int): IntArray {
        return intArrayOf(
            v!!.posX + (v.vel!!.getXMag() * predictionFactor).toInt(),
            v.posY - (v.vel!!.getYMag() * predictionFactor).toInt()
        )
    }
    fun Seek(x: Double, y: Double) {
        ApplyForce(GetSteeringForce(x, y, false))
    }

    @JvmOverloads
    fun Pursue(v: Hero?, predictionFactor: Int = defaultPredictionFactor) {
        val pos = GetPredictedPosition(v, predictionFactor)
        Seek(pos[0].toDouble(), pos[1].toDouble())
    }

    fun Wait(){
    }


    fun draw(heroX: Int, heroY: Int, g: Graphics2D) {
        g.color = Color.YELLOW
        g.fillOval(x - heroX + WINDOW_WIDTH / 2 - (size+10) / 2, y - heroY + WINDOW_HEIGHT / 2 - (size) / 2,(size), (size))
        g.color = Color.BLACK
        g.drawString("$comportement ", x - heroX + WINDOW_WIDTH / 2 - size / 2 , y - heroY + WINDOW_HEIGHT / 2 - size / 2 - 4)
    }


    companion object {
        private fun CalculateDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
            return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0))
        }


    }
}

