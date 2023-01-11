package Spell

import Entity
import Renderer
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import sprite.Animation
import sprite.Sprite
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import kotlin.math.cos
import kotlin.math.sin


class Blaster(posX: Int, posY: Int, size: Int, var damage : Int,var range : Int, val velocityX: Int, val velocityY : Int , val level : Int , val angle : Double) : Entity(posX, posY, size) {

    var defaultdamage = damage

    var anim = Animation(arrayOf(
        Sprite.getBlastSprite(0, 0),
        Sprite.getBlastSprite(0, 1),
        Sprite.getBlastSprite(0, 2),
    ),5)



    override fun draw(g: Graphics2D) {
        g.color = Color.YELLOW
        // Draw the spike
        g.drawOval(posX - Renderer.hero.posX + WINDOW_WIDTH / 2,posY -  Renderer.hero.posY + WINDOW_HEIGHT / 2,size , size)
        g.color = Color.black
        val image = anim.sprite
        val at = AffineTransform.getTranslateInstance((posX - Renderer.hero.posX + WINDOW_WIDTH / 2 - size / 2).toDouble(), (posY -  Renderer.hero.posY + WINDOW_HEIGHT / 2 - size / 2).toDouble())
        at.rotate(-angle +1.5*Math.PI , 14.0, 6.0 )
        at.scale((size/10).toDouble() , (size/10).toDouble())
        g.drawImage(image, at , null)
    }
    fun updateState(){
        when(level){
            1-> {
            }

            2->{

                Renderer.hero.spells.filterIsInstance<BlasterSpell>().first().cooldown = 40
            }
            3->{
                size = 15
                damage = (defaultdamage*1.25).toInt()
                Renderer.hero.spells.filterIsInstance<BlasterSpell>().first().cooldown = 30
            }
            4->{

                damage = (defaultdamage*1.80).toInt()
                Renderer.hero.spells.filterIsInstance<BlasterSpell>().first().cooldown = 20

            }
            5->{ damage = (defaultdamage*3.0).toInt()
                size = 20
                Renderer.hero.spells.filterIsInstance<BlasterSpell>().first().cooldown = 10
            }
        }
    }
    override fun step() {
        updateState()
        anim.update()
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
        if(Math.sqrt((posX -  Renderer.hero.posX).toDouble() * (posX - Renderer.hero.posX).toDouble() + (posY -  Renderer.hero.posY).toDouble() * (posY -  Renderer.hero.posY).toDouble()) > range) {
            Renderer.drawablestemp += this
        }
    }
}

class BlasterSpell(level : Int ) : Spell(40, level) {

    override fun cast() {

        val targets = Renderer.entities
        // Get the closest enemy

        for (i in 0..level-1){
        var target = targets.minBy { Math.sqrt((it.posX - Renderer.hero.posX).toDouble() * (it.posX - Renderer.hero.posX).toDouble() + (it.posY - Renderer.hero.posY).toDouble() * (it.posY - Renderer.hero.posY).toDouble()) }
        var angle =  (Math.atan2((target.posX - Renderer.hero.posX).toDouble(), (target.posY - Renderer.hero.posY).toDouble()));
        castBlaster(angle , level )
    }}



    fun castBlaster(angle: Double, level: Int) {
        Renderer.addDrawable(
            Blaster(
                Renderer.hero.posX +     (-30..30).random(),
                Renderer.hero.posY +     (-30..30).random(),
                10,
                10,
                400,
                (20 * sin(angle )).toInt(),
                (20 * cos(angle )).toInt(),
                level,angle
            )
        )

    }


}