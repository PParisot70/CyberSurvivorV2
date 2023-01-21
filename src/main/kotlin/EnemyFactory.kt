import Renderer.hero
import java.util.*


object EnemyFactory {
    var rightorleft = 1
    var rdnsX = 0
    var topordown = 1
    var rdnsY = 0
    var x = 0
    var y = 0


    fun createEnemy(): Enemy {
        newrdn()
        while (verif()) {
            newrdn()
        }
        return Enemy(x, y ,20,  EnemyType.FAST)
    }

    fun randomX(): Int {
        return when (rightorleft) {
            1 -> return hero.posX + 540 + rdnsX
            2 -> return hero.posX - 540 - rdnsX
            3 -> return hero.posX - rdnsX
            4 -> return hero.posX + rdnsX
            else -> {
                return hero.posX
            }
        }
    }


    fun randomY(): Int {
        return when (topordown) {
            1 -> return hero.posY + 540 + rdnsY
            2 -> return hero.posY - 540 - rdnsY
            3 -> return hero.posY - rdnsY
            4 -> return hero.posY + rdnsY
            else -> {
                return hero.posX
            }
        }
    }

    fun newrdn() {
        topordown = (1..4).random()
        rdnsY = (0..150).random()
        rightorleft = (1..4).random()
        rdnsX = (0..150).random()
        x = randomX()
        y = randomY()
    }

    fun verif(): Boolean {
        if (topordown == 2 || topordown == 1 || rightorleft == 2 || rightorleft == 1) {
            return false
        }
        if (topordown == 3 && rightorleft == 4) {
            return true
        }
        if (topordown == 3 && rightorleft == 3) {
            return true
        }
        if (topordown == 4 && rightorleft == 4) {
            return true
        }
        if (topordown == 4 && rightorleft == 3) {
            return true
        }
        return true

    }


    fun getRandomEnemyType(): EnemyType {
        val rand = kotlin.random.Random.nextDouble()
        val limit = EnemyType.values().sumByDouble { it.probability }
        val prob = rand * limit
        var cumProb = 0.0
        for (type in EnemyType.values()) {
            cumProb += type.probability
            if (prob < cumProb) return type
        }
        return EnemyType.values().first()
    }

}
