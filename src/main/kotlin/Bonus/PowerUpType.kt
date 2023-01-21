import sprite.Sprite
import java.awt.Image

enum class PowerUpType(val nom: String, val maxLevel: Int) {
    SHARPDAMAGE("Sharp", 5 , ),
    EXPLODEDAMAGE("Explode", 5, ),
    BLASTDAMAGE("Blast", 5,),
    HEALTHMAX("VieMax", 5, ),
    AIMANT("Aimant", 5 , ),
    SPEED("Speed", 5),
    MULTIEXP("MultiEXP", 5,)
}