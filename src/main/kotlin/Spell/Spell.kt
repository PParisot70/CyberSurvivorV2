package Spell

import java.awt.Image

abstract class Spell(var cooldown: Int , var level: Int , var name : String  , var image : Image) {
    var currentCooldown = 0

    fun step() {
        if (currentCooldown > 0) {
            currentCooldown--
        }
        else if(currentCooldown == 0) {
            cast()
            currentCooldown = cooldown
        }
    }


    abstract fun cast()
}