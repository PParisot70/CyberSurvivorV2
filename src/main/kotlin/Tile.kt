import Renderer.hero
import java.awt.Graphics2D
import sprite.Sprite
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH

class Tile(val x: Int, val y: Int, val gridx: Int , val gridy :Int){


    fun draw(g: Graphics2D ,heroX: Int, heroY: Int) {
        g.drawImage(Sprite.getTileSprite(gridx, gridy), x - heroX + WINDOW_WIDTH / 2 , y - heroY + WINDOW_HEIGHT / 2 , null)
    }

}
        object TileFactory{
            fun createTile(x: Int , y : Int): Tile {
                return Tile(x, y , (0..2).random(), (0..2).random())

            }

        }