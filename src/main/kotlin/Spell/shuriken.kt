package Spell

import Enemy
import Entity
import Renderer
import Renderer.FRAMES_PER_SEC
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import sprite.Sprite
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import kotlin.math.PI

class Shuriken(posX: Int, posY: Int, size: Int, var damage : Int,var range : Int ,val velocityX: Int, val velocityY : Int , val level : Int , val angle : Double) : Entity(posX, posY, size) {
    var defaultdamage = damage
    override fun draw(g: Graphics2D) {
        g.color = Color.red
        // Draw the spike
        g.drawOval(posX - Renderer.hero.posX + WINDOW_WIDTH / 2,posY -  Renderer.hero.posY + WINDOW_HEIGHT / 2,size , size)
        g.color = Color.black

        val image = Sprite.getShurikenSprite(0,0)
        val at = AffineTransform.getTranslateInstance((posX - Renderer.hero.posX + WINDOW_WIDTH / 2 - size / 2).toDouble(), (posY -  Renderer.hero.posY + WINDOW_HEIGHT / 2 - size / 2).toDouble())
        at.rotate(-angle +1.5*Math.PI , 16.0, 16.0 )
        at.scale((1+size/15).toDouble() , ((1+size/15).toDouble()))
        g.drawImage(image, at , null)
    }
    fun updateState(){
        when(level){
            1->print("nothing speciale")
            2->{
                range += 50
                Renderer.hero.spells.filterIsInstance<ShurikenSpell>().first().cooldown = 40
            }
            3->{
                size = 15
                range += 100
                damage = (defaultdamage*1.25).toInt()
                Renderer.hero.spells.filterIsInstance<ShurikenSpell>().first().cooldown = 30
            }
            4->{
                range += 150
                damage = (defaultdamage*1.80).toInt()
                Renderer.hero.spells.filterIsInstance<ShurikenSpell>().first().cooldown = 20

            }
            5->{
                size = 20
                range += 250
                damage = defaultdamage * 3
                size
                Renderer.hero.spells.filterIsInstance<ShurikenSpell>().first().cooldown = 10
            }
        }
    }
    override fun step() {
        updateState()
        posX += velocityX
        posY += velocityY

        // Check if the spike collides
        Renderer.entities.forEach {
            if(it.isColliding(this)) {
                it.health -= damage
                Renderer.drawablestemp += this
            }
        }


        // if the spike is too far away, remove it
        if(Math.sqrt((posX -  Renderer.hero.posX).toDouble() * (posX - Renderer.hero.posX).toDouble() + (posY -  Renderer.hero.posY).toDouble() * (posY -  Renderer.hero.posY).toDouble()) > 200) {
            Renderer.drawablestemp += this
        }
    }
}

class ShurikenSpell(level : Int ) : Spell(40, level, "Shuriken") {

    override fun cast() {

        for (i in 0..level-1) {

            var currentAngle =  ((0..360).random().toDouble())
            castShuriken(currentAngle , level)

        }

    }

    fun castShuriken(angle: Double, level: Int) {

        Renderer.addDrawable(
            Shuriken(
                Renderer.hero.posX,
                Renderer.hero.posY,
                10,
                10,400,
                (30 * Math.cos(angle)).toInt(),
                (30 * Math.sin(angle)).toInt(),
                level,angle
            )
        )
    }


}