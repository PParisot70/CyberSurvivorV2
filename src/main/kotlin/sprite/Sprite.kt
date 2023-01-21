package sprite

import java.awt.AlphaComposite
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.IOException
import javax.imageio.ImageIO


object Sprite {
    private var heroSpriteSheet: BufferedImage? = null
    private var spiderSpriteSheet: BufferedImage? = null
    private var tilesetSpriteSheet: BufferedImage? = null
    private var     expsheet: BufferedImage? = null
private var blastsheet :BufferedImage? = null
    private var         ennemiesheet: BufferedImage? = null
    private var     GUIlevelsheet: BufferedImage? = null
    private var    GUIvaguesheet: BufferedImage? = null
    private var    herosheet: BufferedImage? = null
    private var       shurikensheet: BufferedImage? = null
    private var       strongsheet: BufferedImage? = null
    private var       fastsheet: BufferedImage? = null
    private var       aimantsheet: BufferedImage? = null
    private var       blastersheet: BufferedImage? = null
    private var       Exp_sheet: BufferedImage? = null
    private var       explosivesheet: BufferedImage? = null
    private var       Sharpnesssheet: BufferedImage? = null
    private var       Speedplussheet: BufferedImage? = null
    private var       viemaxsheet: BufferedImage? = null

    const val TILE_SIZE = 128
    fun loadSprite(file: String): BufferedImage? {
        var sprite: BufferedImage? = null
        try {
            sprite = ImageIO.read(javaClass.getResource("/images/$file.png"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sprite
    }





    fun dye(image: BufferedImage, color: Color): BufferedImage {
        val w = image.width
        val h = image.height
        val dyed = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
        val g = dyed.createGraphics()
        g.drawImage(image, 0, 0, null)
        g.composite = AlphaComposite.SrcAtop
        g.color = color
        g.fillRect(0, 0, w, h)
        g.dispose()
        return dyed
    }

    fun getTileSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (tilesetSpriteSheet == null) {
            tilesetSpriteSheet = loadSprite("tileset")
        }
        return tilesetSpriteSheet!!.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE)
    }
    fun getExpSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (expsheet == null) {
            expsheet = loadSprite("exp")
        }
        return expsheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }

    fun getEnnemieSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (ennemiesheet == null) {
            ennemiesheet = loadSprite("Ennemie")
        }
        return ennemiesheet!!.getSubimage(xGrid * 64, yGrid *64, 64, 64)
    }

    fun getGUILevelSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (GUIlevelsheet == null) {
            GUIlevelsheet = loadSprite("Level")
        }
        return GUIlevelsheet!!.getSubimage(xGrid * 125, yGrid *60, 125, 60)
    }

    fun getGUIVagueSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (GUIvaguesheet == null) {
            GUIvaguesheet = loadSprite("Vague")
        }
        return GUIvaguesheet!!.getSubimage(xGrid * 124, yGrid *60, 124, 60)
    }


    fun getBlastSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (blastsheet == null) {
            blastsheet = loadSprite("Blast")
        }
        return blastsheet!!.getSubimage(xGrid * 28, yGrid *12, 28, 12)
    }

    fun getShurikenSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (shurikensheet == null) {
            shurikensheet = loadSprite("Shuriken")
        }
        return shurikensheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }

    fun getheroSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (herosheet == null) {
            herosheet = loadSprite("Hero")
        }
        return herosheet!!.getSubimage(xGrid * 315, yGrid *340, 315, 340)
    }

    fun getSTRONGSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (strongsheet == null) {
            strongsheet = loadSprite("EnnemieStrong")
        }
        return strongsheet!!.getSubimage(xGrid * 128, yGrid *128, 128, 128)
    }

    fun getAimantSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (aimantsheet == null) {
           aimantsheet = loadSprite("Aimant")
        }
        return aimantsheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }
    fun getBlasterSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (blastersheet == null) {
            blastersheet = loadSprite("blaster")
        }
        return blastersheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }
    fun getExp_Sprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (Exp_sheet == null) {
            Exp_sheet = loadSprite("MultiExp")
        }
        return Exp_sheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }
    fun getExplosiveSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (explosivesheet == null) {
            explosivesheet = loadSprite("Explosive")
        }
        return explosivesheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }
    fun getSharpnessSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (Sharpnesssheet == null) {
            Sharpnesssheet = loadSprite("Sharpness")
        }
        return Sharpnesssheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }
    fun getSpeedPLUSSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (Speedplussheet == null) {
            Speedplussheet = loadSprite("SpeedPLus")
        }
        return Speedplussheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }
    fun getviemaxSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (viemaxsheet == null) {
            viemaxsheet = loadSprite("Viemaxx_plus_")
        }
        return viemaxsheet!!.getSubimage(xGrid * 32, yGrid *32, 32, 32)
    }

    fun getFastsprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (fastsheet == null) {
            fastsheet = loadSprite("FAST")
        }
        return fastsheet!!.getSubimage(xGrid * 50, yGrid *50, 50, 50)
    }
}