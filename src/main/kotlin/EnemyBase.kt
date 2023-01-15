import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import Renderer.hero
import sprite.Animation
import sprite.Sprite
import java.awt.geom.AffineTransform


class Enemy  (posX: Int = 0, posY: Int = 0, size : Int = 20 , type : EnemyType ): Entity(posX, posY, size ) {
    var vel // velocity
            : Vector? = null
    var acc // acceleration
            : Vector
    var maxVel = 4.0 * LevelTimer.velmultiply
    var maxForce = 0.5
    var mass = 1.0
    var pathLength = 50
    var path = LinkedList<IntArray>()
    var color = Color.RED
    var defaultPredictionFactor = 5
    var target: Hero? = hero
    var comportement = Behavior.Seek
    var health = 0.0
    var initialVelocity: Vector? = Vector()
var realsize = size
    var anim = Animation(arrayOf(
    Sprite.getEnnemieSprite(0, 0),
        Sprite.getEnnemieSprite(1, 0),
        Sprite.getEnnemieSprite(0, 1),
        Sprite.getEnnemieSprite(1, 1),
        Sprite.getEnnemieSprite(0, 2),
        Sprite.getEnnemieSprite(1, 2),
    ),
        5)


    init {
        vel = initialVelocity?.let { Vector(it) } ?: Vector(0.0, 0.0)
        acc = Vector(0.0, 0.0)
        if (vel!!.getMag() > maxVel) {
            vel!!.setMag(maxVel)
        }

        when(type) {
            EnemyType.BASIC ->{health = 10 * LevelTimer.healthmutiply}
            EnemyType.STRONG -> {
                realsize = 80
                maxVel *= 0.6
                health = 10000 * LevelTimer.healthmutiply
                anim = Animation(arrayOf(
                    Sprite.getSTRONGSprite(0, 0),
                    Sprite.getSTRONGSprite(1, 0),
                    Sprite.getSTRONGSprite(0, 1),
                    Sprite.getSTRONGSprite(1, 1),
                ),4
                )
            }
            EnemyType.FAST -> {}
            EnemyType.BLASTER -> {}
            EnemyType.BOSS ->  {}

        }


        synchronized(LevelTimer.sync) {
            health = 10 * LevelTimer.healthmutiply
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
        anim.update()
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
        this.posX += vel!!.getXMag().toInt()
        posY -= vel!!.getYMag().toInt()
        acc.setMag(0.0)
        repulsion()




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



    fun GetSteeringForce(x: Double, y: Double, flee: Boolean): Vector {
        var desiredMag = CalculateDistance(this.posX.toDouble(), this.posY.toDouble(), x, y)
        if (desiredMag > maxVel) {
            desiredMag = maxVel
        }
        val desiredAngle = Math.atan2(this.posY - y, x - this.posX)
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


    fun Pursue(v: Hero?, predictionFactor: Int = defaultPredictionFactor) {
        val pos = GetPredictedPosition(v, predictionFactor)
        Seek(pos[0].toDouble(), pos[1].toDouble())
    }

    fun Evade(v: Hero?, predictionFactor: Int = defaultPredictionFactor) {
        val pos = GetPredictedPosition(v, predictionFactor)
        Flee(pos[0].toDouble(), pos[1].toDouble())
    }


    fun repulsion(){
        var filtre = Renderer.entities.filter{it != this}
        filtre.forEach{
      if (CheckCollision(it)){
          this.Flee((it.posX).toDouble() , (it.posY).toDouble())
      }
        }
    }


    fun CheckCollision(v2: Enemy): Boolean {
        return Math.pow((posX - v2.posX).toDouble(), 2.0) + Math.pow((posY - v2.posY).toDouble(), 2.0) < Math.pow((v2.realsize + realsize).toDouble(), 2.0)
    }
    companion object {
        private fun CalculateDistance(x1: Double, y1: Double, x2: Double, y2: Double): Double {
            return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0))
        }


    }

    override fun draw(g: Graphics2D) {
        g.color = color
        val image = anim.sprite
        val at = AffineTransform.getTranslateInstance((posX - hero.posX + WINDOW_WIDTH / 2 - (realsize+10)).toDouble() , (posY - hero.posY + WINDOW_HEIGHT / 2- (realsize+10)).toDouble())
        if (hero.posX -50 > posX){
            at.scale(-1.0 ,1.0)
            at.translate(-64.0, 0.0)
        }
        if (hero.posX +50 < posX){
            at.scale(1.0 ,1.0)
        }
        g.fillOval(posX - Renderer.hero.posX + WINDOW_WIDTH / 2 - (realsize+10) / 2, posY - Renderer.hero.posY + WINDOW_HEIGHT / 2 - (realsize+10) / 2,(realsize+10), (realsize+10))
        g.drawImage(image,at, null)

    }

    fun dying():Boolean{
        if (health <= 0){
            Renderer.xps.add(Experience(this.posX, this.posY,2))
                    return true
        }else{
            return false}

    }

    override fun step() {
        TODO("Not yet implemented")
    }
}

