import Bonus.Powerup
import java.awt.Color
import java.awt.Graphics2D
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import Renderer.xps
import Spell.Spell
import Tile
import sprite.Animation
import sprite.Sprite
import java.awt.geom.AffineTransform
import kotlin.random.Random

class Hero( posX: Int, posY: Int , size :Int , initialVelocity: Vector? = Vector()) : Entity(posX, posY , size ){
    var vel // velocity
            : Vector? = null
    var acc // acceleration
            : Vector
    var maxVel = 5.0
    // Max distance per tick
    var exp = 0.0
    var level = 0
    var expnextLevel = 5
    var magnetsize = 200.0
    var defaultmagnetsize = 200.0
    var Multiexp = 1.0
    var spells: MutableList<Spell> = mutableListOf()
    var bonus: MutableList<Powerup> = mutableListOf()
    var health = 10
    var maxhealth = 10
    var defaulthealth = 10
    var direction = 0


    var anim = Animation(arrayOf(
        Sprite.getheroSprite(0, 0),
        Sprite.getheroSprite(1, 0),
        Sprite.getheroSprite(0, 1),
        Sprite.getheroSprite(1, 1),
        Sprite.getheroSprite(0, 2),
        Sprite.getheroSprite(1, 2),
        Sprite.getheroSprite(0, 3),
        Sprite.getheroSprite(1, 3),
    ),
        5)

    init {
        health = defaulthealth
        vel = initialVelocity?.let { Vector(it) } ?: Vector(0.0, 0.0)
        acc = Vector(0.0, 0.0)
        if (vel!!.getMag() > maxVel) {
            vel!!.setMag(maxVel)
        }
    }




   override fun draw(g: Graphics2D) {
       println(direction)
        val centerX = WINDOW_WIDTH / 2
        val centerY = WINDOW_HEIGHT / 2
       val image = anim.sprite
       val at = AffineTransform.getTranslateInstance((centerX - 56).toDouble(), (centerY - 76).toDouble())
       if (direction == 0){
           at.scale(0.4 ,0.4)
       }
       if (direction == 1){
           at.scale(-0.4 ,0.4)
           at.translate(-300.0, 0.0)
       }
       g.drawImage(image,at, null)
    }

    override fun step() {
        anim.update()
        spells.forEach { it.step() }
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
        exp += xp.amount*Multiexp
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
        GameManager.levelupsound.stop()
        GameManager.levelupsound.reset()
        GameManager.levelupsound.play()
        level ++
        expnextLevel += 2 + level * 2
        Renderer.Skillselection()
        GameManager.state = GameState.SKILL_SELECTION
    }

    fun updateState(){
        bonus.forEach() {when(it.type) {
            PowerUpType.SPEED ->{speed = (it.SpeedMulti+speed).toInt()}
            PowerUpType.AIMANT ->{magnetsize = defaultmagnetsize + it.Aimant}
            PowerUpType.HEALTHMAX ->{maxhealth = defaulthealth + it.HealthAdd}
            PowerUpType.MULTIEXP ->{ Multiexp = it.MultiExp }
            else -> "nothing"
        }
    }}


}

