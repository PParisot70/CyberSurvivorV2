import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import sprite.Sprite
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.io.File


object  GUI {
    val font = Font("Press Start 2P", Font.PLAIN, 62)
    val font2 = Font("Press Start 2P", Font.PLAIN, 30)

    fun draw(g: Graphics2D) {

if (GameManager.state == GameState.GAME) {
    g.font = font
    /*
    Affichage du timer ( milieu centre )
     */
    Renderer.elapsedTime
    val formattedElapsedTime =
        String.format("%02d:%02d", Renderer.elapsedTime.toMinutes(), Renderer.elapsedTime.seconds)
    g.color = Color.WHITE
    g.drawString(formattedElapsedTime, (WINDOW_WIDTH / 2) - 75, WINDOW_HEIGHT / 10)

    /*
      Affichage Niveau Vague ( a droite )
      */
    g.color = Color.red
    g.drawString(" ${LevelTimer.niveau}", (WINDOW_WIDTH) - 100, 107)
    g.color = Color.black
    g.fillRect(WINDOW_WIDTH - 105, (WINDOW_HEIGHT / 10) + 50, 30, 310);
    g.color = Color.RED

    val t = (Renderer.elapsedTime.seconds % 30).toInt() * 10
    g.fillRect(WINDOW_WIDTH - 100, (WINDOW_HEIGHT / 10) + 55, 20, t);
    g.drawImage(Sprite.getGUIVagueSprite(0, 0), (WINDOW_WIDTH) - 131, 17, null)
    /*
    Affichage Niveau XP ( a gauche )
    */
    g.color = Color.YELLOW
    g.drawString(" ${Renderer.hero.level}", 45, 107)
    g.color = Color.BLACK
    g.fillRect(0, (WINDOW_HEIGHT / 700) * 5, 1080, 8);
    g.color = Color.YELLOW
    g.fillRect(0, (WINDOW_HEIGHT / 700) * 6, 1080 * (Renderer.hero.exp).toInt() / (Renderer.hero.expnextLevel).toInt(), 6)
    g.drawImage(Sprite.getGUILevelSprite(0, 0), 7, 17, null)

    /*
Affichage de la vie
*/
    g.color = Color.green
    for (i in 1..Renderer.hero.health) {
        g.fillOval(WINDOW_WIDTH / 2 - (22 * Renderer.hero.health) + i * 40, (WINDOW_HEIGHT / 10) * 9, 20, 20)
    }


    /*
   Vue des sort obtenue
     */
    g.color = Color.YELLOW
    g.font = Font("ComicSans MS", Font.PLAIN, 20)

    var i = 0
    Renderer.hero.spells.forEach {
        g.drawString(
            " ${it.level}",
            25 - (22 * Renderer.hero.spells.size) + i * 40,
            (WINDOW_HEIGHT / 20) * 18
        )
        i++
    }
}


if (GameManager.state == GameState.PAUSE) {
    var alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
    g.setComposite(alpha)
    g.color = Color.BLACK
    g.fillRect(0, 0,WINDOW_WIDTH , WINDOW_HEIGHT)
    alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
    g.setComposite(alpha)
            g.font = font

    g.color = Color.WHITE
    g.drawString(" -|PAUSE|-", (WINDOW_WIDTH/2)-150, 107 +WINDOW_HEIGHT/5)

    /*
      Affichage Niveau Vague ( a droite )
      */
            g.color = Color.red
            g.drawString(" ${LevelTimer.niveau}", (WINDOW_WIDTH/2)+5, 107 +WINDOW_HEIGHT/5*2)
            g.drawImage(Sprite.getGUIVagueSprite(0, 0), (WINDOW_WIDTH/2)+5, 17 +  WINDOW_HEIGHT/5*2, null)
            /*
            Affichage Niveau XP ( a gauche )
            */
            g.color = Color.YELLOW
            g.drawString(" ${Renderer.hero.level}", 45 + WINDOW_WIDTH/2 -130  , 107  + WINDOW_HEIGHT/5*2)
            g.drawImage(Sprite.getGUILevelSprite(0, 0), 7 + WINDOW_WIDTH/2 -130 , 17 + WINDOW_HEIGHT/5*2, null)

            /*
        Affichage de la vie
        */
            g.color = Color.green
            for (i in 1..Renderer.hero.health) {
                g.fillOval(WINDOW_WIDTH / 2 - (22 * Renderer.hero.health) + i * 40, (WINDOW_HEIGHT / 10) * 9, 20, 20)
            }


            /*
           Vue des sort obtenue
             */
            g.color = Color.YELLOW
            g.font = Font("ComicSans MS", Font.PLAIN, 20)
        }


if (GameManager.state == GameState.SKILL_SELECTION) {
            var alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
            g.setComposite(alpha)
            g.color = Color.BLACK
            g.fillRect(0, 0,WINDOW_WIDTH , WINDOW_HEIGHT)
            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
            g.setComposite(alpha)
            g.font = font

            g.color = Color.WHITE
            g.drawString(" -|LEVEL UP|-", (WINDOW_WIDTH/2)-150, 20+WINDOW_HEIGHT/5)

    g.font = font2
            g.drawString("${Renderer.result[0].type}",(WINDOW_WIDTH/2) , WINDOW_HEIGHT/2 )
    g.drawString("${Renderer.result[1].type}",(WINDOW_WIDTH/3)-50 , WINDOW_HEIGHT/2 )
    g.drawString("${Renderer.result[2].type}",(WINDOW_WIDTH/3)*2+50 , WINDOW_HEIGHT/2 )
        }
    }

    }
