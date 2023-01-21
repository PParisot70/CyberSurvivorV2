import Bonus.Powerup
import Renderer.WINDOW_HEIGHT
import Renderer.WINDOW_WIDTH
import Renderer.result


import sprite.Sprite
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import javax.swing.JFrame


object  GUI {
    val font = Font("Press Start 2P", Font.PLAIN, 62)
    val font2 = Font("Press Start 2P", Font.PLAIN, 30)
    var buttonup3 = MenuButton("Button Text 3 ",WINDOW_WIDTH/2-400 , WINDOW_HEIGHT/8*6, 200, 100)
    var buttonup2 = MenuButton("Button Text 2 ", WINDOW_WIDTH/2-100, WINDOW_HEIGHT/8*6, 200, 100)
    var buttonup1 = MenuButton("Button Text 1 ", WINDOW_WIDTH/2+200, WINDOW_HEIGHT/8*6, 200, 100)
    var buttonstart = MenuButton("Jouer",WINDOW_WIDTH/2-150 , WINDOW_HEIGHT/15*5, 350, 80)
    var buttonsave = MenuButton("Sauvegarder", WINDOW_WIDTH/2-150, WINDOW_HEIGHT/15*7, 350/2, 80)
    var buttonload = MenuButton("Charger", WINDOW_WIDTH/2+25, WINDOW_HEIGHT/15*7, 350/2, 80)
    var buttonshop = MenuButton("Shop", WINDOW_WIDTH/2-150, WINDOW_HEIGHT/15*9, 350, 80)
    var buttonquit = MenuButton("Quittter", WINDOW_WIDTH/2-150, WINDOW_HEIGHT/15*11, 350, 80)

    init {

        Renderer.add(buttonup1)
        buttonup1.setButtonAction {
            disableskillbutton()
            if(Renderer.hero.level%5 == 0){Renderer.ChooseSpell(0)}
            else{Renderer.ChooseSkill(0)}
            Renderer.pauseGame()

        }
        Renderer.add(buttonup2)
        buttonup2.setButtonAction {
           disableskillbutton()
            if(Renderer.hero.level%5 == 0){Renderer.ChooseSpell(1)}
            else{Renderer.ChooseSkill(1)}
            Renderer.pauseGame()

        }
       Renderer.add(buttonup3)
        buttonup3.setButtonAction {
           disableskillbutton()
            if(Renderer.hero.level%5 == 0){Renderer.ChooseSpell(2)}
            else{Renderer.ChooseSkill(2)}
            Renderer.pauseGame()
        }


        Renderer.add(buttonstart)
        buttonstart.setButtonAction {
            disablemenubutton()
            GameManager.state=GameState.GAME
            Renderer.initGame()
        }
        Renderer.add(buttonsave)
        Renderer.add(buttonload)
        Renderer.add(buttonshop)
        Renderer.add(buttonquit)
        buttonquit.setButtonAction{
            Renderer.f.defaultCloseOperation
        }
        disablemenubutton()
    }
    fun draw(g: Graphics2D) {

        if (GameManager.state == GameState.GAME) {
            g.font = font
            disablemenubutton()
            disableskillbutton()
            /*
    Affichage du timer ( milieu centre )
     */
            Renderer.elapsedTime
            val formattedElapsedTime =
                String.format("%02d:%02d", Renderer.elapsedTime.toMinutes(), Renderer.elapsedTime.seconds%60)
            g.color = Color.WHITE
            g.drawString(formattedElapsedTime, (WINDOW_WIDTH / 2) - 75, WINDOW_HEIGHT / 10)
println(GameManager.state)
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
            g.fillRect(
                0,
                (WINDOW_HEIGHT / 700) * 6,
                1080 * (Renderer.hero.exp).toInt() / (Renderer.hero.expnextLevel).toInt(),
                6
            )
            g.drawImage(Sprite.getGUILevelSprite(0, 0), 7, 17, null)

            /*
Affichage de la vie
*/
            g.color = Color.green
            for (i in 1..Renderer.hero.health) {
                g.fillOval(WINDOW_WIDTH / 2 - (22 * Renderer.hero.health) + i * 40, (WINDOW_HEIGHT / 10) * 9, 20, 20)
            }

g.drawOval((WINDOW_WIDTH / 2 -(Renderer.hero.magnetsize)).toInt(), (WINDOW_HEIGHT / 2 -(Renderer.hero.magnetsize)).toInt(),(Renderer.hero.magnetsize*2).toInt(),(Renderer.hero.magnetsize*2).toInt())
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

            var j = 0
            var filteredList = Renderer.hero.bonus.filter { it.level > 0 }
            filteredList.forEach {
                g.drawImage(it.image,
                    WINDOW_WIDTH/25,
                    ((WINDOW_HEIGHT / 2) -32 +(j-1)*64)-128,
                    64,
                    64,
                    null
                )

                j++
            }



        }


        if (GameManager.state == GameState.PAUSE) {
            var alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
            g.setComposite(alpha)
            g.color = Color.BLACK
            g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)
            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
            g.setComposite(alpha)
            g.font = font

            g.color = Color.WHITE
            g.drawString(" -|PAUSE|-", (WINDOW_WIDTH / 2) - 150, 107 + WINDOW_HEIGHT / 5)


        /*
      Affichage Niveau Vague ( a droite )
      */
        g.color = Color.red
        g.drawString(" ${LevelTimer.niveau}", (WINDOW_WIDTH / 2) + 5, 107 + WINDOW_HEIGHT / 5 * 2)
        g.drawImage(Sprite.getGUIVagueSprite(0, 0), (WINDOW_WIDTH / 2) + 5, 17 + WINDOW_HEIGHT / 5 * 2, null)
        /*
            Affichage Niveau XP ( a gauche )
            */
        g.color = Color.YELLOW
        g.drawString(" ${Renderer.hero.level}", 45 + WINDOW_WIDTH / 2 - 130, 107 + WINDOW_HEIGHT / 5 * 2)
        g.drawImage(Sprite.getGUILevelSprite(0, 0), 7 + WINDOW_WIDTH / 2 - 130, 17 + WINDOW_HEIGHT / 5 * 2, null)

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
            g.drawString(" -|LEVEL UP|-", (WINDOW_WIDTH/2)-175, 20+WINDOW_HEIGHT/5)

    g.font = font2
    if(Renderer.hero.level%5 == 0){
        buttonup1.setText("${Renderer.resultspell[0].name}")
        buttonup2.setText("${Renderer.resultspell[1].name}")
        buttonup3.setText("${Renderer.resultspell[2].name}")}
    else{
        buttonup1.setText("${Renderer.result[0].type}")
        buttonup2.setText("${Renderer.result[1].type}")
        buttonup3.setText("${Renderer.result[2].type}")}


    enableskillbutton()
    result[0].setImage(true)
    result[1].setImage(true)
    result[2].setImage(true)
    g.drawImage(Renderer.result[0].image ,750 , 250, 192, 192,null)
    g.drawImage(Renderer.result[1].image ,450 , 250, 192,192, null)
    g.drawImage(Renderer.result[2].image ,150 , 250,192,192, null)

}
        if (GameManager.state == GameState.MENU) {
            var alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
            g.setComposite(alpha)
            g.color = Color.BLACK
            g.fillRect(0, 0,WINDOW_WIDTH , WINDOW_HEIGHT)
            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
            g.setComposite(alpha)
            g.font = font
            g.color = Color.WHITE
            g.drawString(" CyberSurvivor", (WINDOW_WIDTH/2)-175, 20+WINDOW_HEIGHT/5)
            g.font = font2
            enablemenubutton()
            disableskillbutton()
        }
    }
    fun disablemenubutton(){
                buttonstart.isVisible = false
                buttonsave.isVisible = false
                buttonload.isVisible = false
                buttonshop.isVisible = false
                buttonquit.isVisible = false
        Renderer.f.requestFocus()
    }

    fun enablemenubutton(){
        buttonstart.isVisible = true
        buttonsave.isVisible = true
        buttonload.isVisible = true
        buttonshop.isVisible = true
        buttonquit.isVisible = true
    }

    fun disableskillbutton(){
        buttonup1.isVisible = false
        buttonup2.isVisible = false
        buttonup3.isVisible = false
        Renderer.f.requestFocus()
    }

    fun enableskillbutton(){
        buttonup1.isVisible = true
        buttonup2.isVisible = true
        buttonup3.isVisible = true
    }


    }
