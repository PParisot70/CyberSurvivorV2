package Bonus

import PowerUpType
import Renderer
import sprite.Sprite
import java.awt.Image

class Powerup(val type: PowerUpType , var image : Image) {
    var level: Int = 0
        private set
    var DamageMulti: Double = 1.0
        private set
    var MultiExp:  Double = 1.0
        private set
    var HealthAdd:  Int = 0
        private set
    var Aimant: Double = 1.0
        private set
    var SpeedMulti:  Double = 1.0
        private set

    fun levelUp() {
        if (level < type.maxLevel) {
            level++
            setImage(false)
            when(type) {
                PowerUpType.SPEED ->{SpeedMulti += 0.1
                Renderer.hero.updateState()}
                PowerUpType.AIMANT ->{Aimant += 50
                    Renderer.hero.updateState()}
                PowerUpType.HEALTHMAX ->{HealthAdd += 2
                    Renderer.hero.updateState()}
                PowerUpType.MULTIEXP ->{MultiExp += 0.25
        Renderer.hero.updateState()}
                else ->DamageMulti += 0.25
            }
        }
    }

    fun setImage( forskill : Boolean){
        if (forskill){
        when(type){
        PowerUpType.SPEED -> this.image =  Sprite.getSpeedPLUSSprite(level,0)
        PowerUpType.AIMANT -> this.image  = Sprite.getAimantSprite(level,0)
        PowerUpType.HEALTHMAX -> this.image  = Sprite.getviemaxSprite(level,0)
        PowerUpType.MULTIEXP -> this.image  = Sprite.getExp_Sprite(level,0)
        PowerUpType.EXPLODEDAMAGE -> this.image  = Sprite.getExplosiveSprite(level,0)
        PowerUpType.BLASTDAMAGE  -> this.image = Sprite.getBlasterSprite(level,0)
        PowerUpType.SHARPDAMAGE -> this.image  = Sprite.getSharpnessSprite(level,0)
    }
        }else{
            when(type){
                PowerUpType.SPEED -> this.image =  Sprite.getSpeedPLUSSprite(level-1,0)
                PowerUpType.AIMANT -> this.image  = Sprite.getAimantSprite(level-1,0)
                PowerUpType.HEALTHMAX -> this.image  = Sprite.getviemaxSprite(level-1,0)
                PowerUpType.MULTIEXP -> this.image  = Sprite.getExp_Sprite(level-1,0)
                PowerUpType.EXPLODEDAMAGE -> this.image  = Sprite.getExplosiveSprite(level-1,0)
                PowerUpType.BLASTDAMAGE  -> this.image = Sprite.getBlasterSprite(level-1,0)
                PowerUpType.SHARPDAMAGE -> this.image  = Sprite.getSharpnessSprite(level-1,0)
            }
        }

}}