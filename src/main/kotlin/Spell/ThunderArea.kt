package Spell

import Entity
import Renderer
import Renderer.FRAMES_PER_SEC
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import java.awt.Color
import java.awt.Graphics2D

class ThunderArea(posX: Int,posY: Int,size: Int=50, var  level : Int) : Entity(posX, posY , size ) {
    var damage: Int = 50
    override fun draw(g: Graphics2D) {
        // Draw the area
                g.color = Color.cyan
                g.fillOval(
                    posX - Renderer.hero.posX + WINDOW_WIDTH / 2 - size / 2,
                    posY - Renderer.hero.posY + WINDOW_HEIGHT / 2 - size / 2,
                    size,
                    size
                )
                g.color = Color.black
        }
fun updateState(){
    when(level){
        1->print("nothing speciale")
        2->{
                size = (size*1.25).toInt()
                damage = (damage*1.25).toInt()
                Renderer.hero.spells.filterIsInstance<ThunderAreaSpell>().first().cooldown = 60
        }
        3->{
            size = (size*1.50).toInt()
            damage = (damage*1.25).toInt()
            Renderer.hero.spells.filterIsInstance<ThunderAreaSpell>().first().cooldown = 50
        }
        4->{
            size = (size*2.0).toInt()
            damage = (damage*1.80).toInt()
            Renderer.hero.spells.filterIsInstance<ThunderAreaSpell>().first().cooldown = 40

        }
        5->{ this.damage *= 3
              this.size *= 3
            Renderer.hero.spells.filterIsInstance<ThunderAreaSpell>().first().cooldown = 30
       }
    }
}


    override fun step() {
        updateState()
        print("$size       $level")
        Renderer.entities.forEach {
            if (it.isColliding(this)) {
                it.health -= damage
            }
        }
        Renderer.drawablestemp += this
    }

}

class ThunderAreaSpell(level : Int ) : Spell(FRAMES_PER_SEC , level) {
    override fun cast() {
var s = ThunderArea(Renderer.hero.posX, Renderer.hero.posY,150,level)
       Renderer.addDrawable(s)
    }

}


/* LEVEL 1 Normal
LEvEL 2  Degat fois 1.5
LEVEL 3 Area x 1.5
Levle 4 1.5 fois plus rapide
 */
