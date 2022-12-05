import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.time.Duration
import java.time.Instant


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

        val decalageint = 50
        /*
        Affichage Niveau Vague ( a droite )
        */
        g.color = Color.black
        g.drawString(" ${LevelTimer.niveau}" , (WINDOW_WIDTH)-50 ,WINDOW_HEIGHT/10)
        g.fillRect(WINDOW_WIDTH-45 , (WINDOW_HEIGHT/10)+5,30,310);
        g.color = Color.RED

        val t  = (Duration.between(Renderer.time, Instant.now()).seconds % 30).toInt()*10
        g.fillRect(WINDOW_WIDTH-40 , (WINDOW_HEIGHT/10)+10 ,20,t);

        /*
        Affichage Niveau XP ( a milieu )
        */
        g.color = Color.black
        g.drawString(" ${Renderer.hero.exp}" , (WINDOW_WIDTH)-50-decalageint ,WINDOW_HEIGHT/10)
        g.fillRect(WINDOW_WIDTH-45-decalageint , (WINDOW_HEIGHT/10)+5,30,310);
        g.color = Color.YELLOW
        g.fillRect(WINDOW_WIDTH-40-decalageint , (WINDOW_HEIGHT/10)+10 ,20,300*Renderer.hero.exp/Renderer.hero.expnextLevel)

/*
Affichage de la vie
 */
        g.color = Color.green
        for (i in 1..Renderer.hero.health) {
            g.fillOval( WINDOW_WIDTH/2-(22*Renderer.hero.health) + i*40, (WINDOW_HEIGHT/10)*9, 20, 20)
        }





    }
}
