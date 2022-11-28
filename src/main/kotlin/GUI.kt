import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.time.Duration
import java.time.Instant
import kotlin.time.Duration.Companion.milliseconds


object  GUI{

    fun draw(g: Graphics2D){

        /*
        Affichage du timer ( milieu centre )
         */
        val s  = Duration.between(Renderer.time, Instant.now()).seconds % 60
        val m =  Duration.between(Renderer.time, Instant.now()).seconds / 60
        g.color = Color.black
        g.font = Font("ComicSans MS", Font.PLAIN, 50)
        g.drawString("$m : $s" , (WINDOW_WIDTH/2)-50 ,WINDOW_HEIGHT/10 )


        /*
Affichage Niveau Vague ( a droite )
 */
        g.drawString(" ${Level.niveau}" , (WINDOW_WIDTH)-55 ,WINDOW_HEIGHT/10)
        g.color = Color.black
        g.fillRect(WINDOW_WIDTH-45 , (WINDOW_HEIGHT/10)+5,40,310);
        g.color = Color.RED

        val t  = (Duration.between(Renderer.time, Instant.now()).seconds % 30).toInt()*10
        g.fillRect(WINDOW_WIDTH-40 , (WINDOW_HEIGHT/10)+10 ,30,t);
    }
}
