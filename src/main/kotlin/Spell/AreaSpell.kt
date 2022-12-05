package Spell

import Enemy
import Entity
import Renderer
import Renderer.FRAMES_PER_SEC
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import Renderer.drawables
import Renderer.drawablestemp
import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.PI

class Area(posX: Int,posY: Int,size: Int, val damage: Int, var  level : Int) : Entity(posX, posY , size ){
    override fun draw(g: Graphics2D) {
        // Draw the area
        when(level){
            1 ,2 ->{  g.color = Color.cyan
                        g.fillOval(posX - Renderer.hero.posX + WINDOW_WIDTH / 2 - size / 2, posY - Renderer.hero.posY + WINDOW_HEIGHT / 2 - size / 2, size, size)
                        g.color = Color.black}
            3 -> {  g.color = Color.BLUE
                g.fillOval(posX - Renderer.hero.posX + WINDOW_WIDTH / 2 - (size*1.5).toInt() / 2, posY - Renderer.hero.posY + WINDOW_HEIGHT / 2 - (size*1.5).toInt() / 2, (size*1.5).toInt(), (size*1.5).toInt())
                g.color = Color.black}
        }

    }

   override fun step() {
when(level){
    1->{Renderer.entities.forEach {
            if(it.isColliding(this)) {
                it.health -= damage } }
        Renderer.drawablestemp += this}
    2 ,3->{        Renderer.entities.forEach {
        if(it.isColliding(this)) {
            it.health -= (damage*1.5).toInt()
        }
    }
        Renderer.drawablestemp += this}
    4->{}
    5->{}
    else ->{}
}
        Renderer.entities.forEach {
            if(it.isColliding(this)) {
                it.health -= damage
            }
        }
        Renderer.drawablestemp += this
    }
}

class AreaSpell(var level : Int ) : Spell(FRAMES_PER_SEC) {
    override fun cast() {
var t = Area(Renderer.hero.posX, Renderer.hero.posY, 150, 50, level)
       Renderer.addDrawable(t)
        println("La ")
    }

}


/* LEVEL 1 Normal
LEvEL 2  Degat fois 1.5
LEVEL 3 Area x 1.5
Levle 4 1.5 fois plus rapide
 */
