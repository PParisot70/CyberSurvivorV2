package Spell

import Entity
import Renderer
import Renderer.FRAMES_PER_SEC
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import java.awt.Color
import java.awt.Graphics2D

class ThunderNuc(posX: Int,posY: Int,size: Int=50, var  level : Int) : Entity(posX, posY , size ) {
    var damage: Int = 200
    override fun draw(g: Graphics2D) {
        // Draw the area
        g.color = Color.YELLOW
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
                damage = (damage*1.25).toInt()
                Renderer.hero.spells.filterIsInstance<ThunderAreaSpell>().first().cooldown = 480
            }
            3->{
                size = (size*1.50).toInt()
                damage = (damage*1.25).toInt()
                Renderer.hero.spells.filterIsInstance<ThunderAreaSpell>().first().cooldown = 420
            }
            4->{
                size = (size*1.50).toInt()
                damage = (damage*1.80).toInt()
                Renderer.hero.spells.filterIsInstance<ThunderAreaSpell>().first().cooldown = 390

            }
            5->{ this.damage *= 3
                this.size *= 2
                Renderer.hero.spells.filterIsInstance<ThunderAreaSpell>().first().cooldown = 350
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

class ThunderNucSpell(level : Int ) : Spell(500 , level , "ThunderNuc") {
    override fun cast() {
        var t = ThunderArea(Renderer.hero.posX, Renderer.hero.posY,1000,level)
        Renderer.addDrawable(t)
    }

}

