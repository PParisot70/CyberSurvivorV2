package Bonus

import PowerUpType
import Renderer

class Powerup(val type: PowerUpType) {
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
            when(type) {
                PowerUpType.SPEED ->{SpeedMulti += 0.1
                Renderer.hero.updateState()}
                PowerUpType.AIMANT ->{Aimant += 0.25
                    Renderer.hero.updateState()}
                PowerUpType.HEALTHMAX ->{HealthAdd += 2
                    Renderer.hero.updateState()}
                PowerUpType.MULTIEXP ->{MultiExp += 0.25
        Renderer.hero.updateState()}
                else ->DamageMulti += 0.25
            }
        }
    }
}