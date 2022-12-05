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

    fun getHeroSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (heroSpriteSheet == null) {
            heroSpriteSheet = loadSprite("human_spritesheet")
        }
        return heroSpriteSheet!!.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE)
    }

    fun getSpiderSprite(xGrid: Int, yGrid: Int): BufferedImage {
        if (spiderSpriteSheet == null) {
            spiderSpriteSheet = loadSprite("slime_spritesheet")
        }
        return spiderSpriteSheet!!.getSubimage(xGrid * TILE_SIZE, yGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE)
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
}