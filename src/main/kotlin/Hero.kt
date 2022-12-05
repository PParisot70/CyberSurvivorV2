import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import Renderer.xps
import Spell.Spell
import Tile

class Hero( posX: Int, posY: Int , size :Int , initialVelocity: Vector? = Vector()) : Entity(posX, posY , size ){
    var vel // velocity
            : Vector? = null
    var acc // acceleration
            : Vector
    var maxVel = 5.0
    // Max distance per tick
    var exp = 0
    var level = 0
    var expnextLevel = 5
    var magnetsize = 200
    var spells: MutableList<Spell> = mutableListOf()
    var health = 10


    init {
        vel = initialVelocity?.let { Vector(it) } ?: Vector(0.0, 0.0)
        acc = Vector(0.0, 0.0)
        if (vel!!.getMag() > maxVel) {
            vel!!.setMag(maxVel)
        }
    }



   override fun draw(g: Graphics2D) {
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

    override fun step() {
        spells.forEach { it.step() }
        println("ici")
    }

    fun isColliding(e: Enemy): Boolean {
        // Computes the distance between the hero and the enemy
        val distance = Math.sqrt(Math.pow((posX - e.posX).toDouble(), 2.0) + Math.pow((posY - e.posY).toDouble(), 2.0))
        // If the distance is less than the sum of the radius, the hero is colliding with the enemy
        if (distance < (size / 2 + e.size / 2)){ health -= 1}
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

    fun distanceToTile(t: Tile): Boolean {
        // Computes the distance between the hero and the enemy
        val distance = Math.sqrt(Math.pow((posX - t.x).toDouble(), 2.0) + Math.pow((posY - t.y).toDouble(), 2.0))
        // If the distance is less than the sum of the radius, the hero is colliding with the enemy
        if (distance > 1000){
            return true
        }
        return false
    }



    fun getXp(xp : Experience){
        exp += xp.amount
        if (exp >= expnextLevel){
            exp -= expnextLevel
            LevelUp()
        }
        println("$exp")
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

