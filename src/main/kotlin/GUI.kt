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

    var buttonretour = MenuButton("Retour",WINDOW_WIDTH/2-100 , WINDOW_HEIGHT/15*1, 200, 80)
    var buttonmultiexp = MenuButton("Multi EXP", WINDOW_WIDTH/2-250, WINDOW_HEIGHT/15*4, 200, 80)
    var buttonExplosive = MenuButton("Explosive", WINDOW_WIDTH/2+50, WINDOW_HEIGHT/15*4, 200, 80)
    var buttonSpeedPlus = MenuButton("Speed", WINDOW_WIDTH/2-250, WINDOW_HEIGHT/15*7, 200, 80)
    var buttonVieMax = MenuButton("VieMax", WINDOW_WIDTH/2+50, WINDOW_HEIGHT/15*7, 200, 80)
    var buttonAiment = MenuButton("Aiment", WINDOW_WIDTH/2-250, WINDOW_HEIGHT/15*10, 200, 80)
    var buttonBlaster = MenuButton("Blaster", WINDOW_WIDTH/2+50, WINDOW_HEIGHT/15*10, 200, 80)
var buttonSharpness = MenuButton("Sharpness", WINDOW_WIDTH/2-100, WINDOW_HEIGHT/15*12, 200, 80)


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



        //BUTTON MENU !!!!!!!!!!!!
        Renderer.add(buttonstart)
        buttonstart.setButtonAction {
            disablemenubutton()
            GameManager.state=GameState.GAME
            Renderer.initGame()
        }
        Renderer.add(buttonsave)
        buttonsave.setButtonAction{GameManager.saveVariables()}
        Renderer.add(buttonload)
        buttonload.setButtonAction{GameManager.loadVariables()}
        Renderer.add(buttonshop)
        buttonshop.setButtonAction {
            disablemenubutton()
            updateshoplabel()
            GameManager.state=GameState.SHOP

        }
        Renderer.add(buttonquit)
        buttonquit.setButtonAction{
            Renderer.f.defaultCloseOperation
        }



        //button SHOP
        Renderer.add(buttonretour)
        buttonretour.setButtonAction{
            disableshopbutton()
            GameManager.state=GameState.MENU
        }
        Renderer.add(buttonmultiexp)
        buttonmultiexp.setButtonAction{ shop(GameManager.shopmultiexp)
            GameManager.shopmultiexp += 1
        }
        Renderer.add(buttonExplosive)
        buttonExplosive.setButtonAction{ shop(GameManager.shopExplosive)
            GameManager.shopExplosive+= 1
        }
        Renderer.add(buttonSpeedPlus)
        buttonSpeedPlus.setButtonAction {
            shop(GameManager.shopSpeedPlus)
            GameManager.shopSpeedPlus += 1
        }
        Renderer.add(buttonVieMax)
        buttonVieMax.setButtonAction{shop(GameManager.shopVieMax)
            GameManager.shopVieMax+=1}
        Renderer.add(buttonAiment)
        buttonAiment.setButtonAction{shop(GameManager.shopAiment)
            GameManager.shopAiment+=1}
        Renderer.add(buttonBlaster)
        buttonBlaster.setButtonAction { shop(GameManager.shopBlaster)
            GameManager.shopBlaster+=1}
        Renderer.add(buttonSharpness)
        buttonSharpness.setButtonAction { shop(GameManager.shopSharpness)
            GameManager.shopSharpness+=1}


        disablemenubutton()
        updateshoplabel()
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
                g.drawImage(it.image,
                    25 - (22 * Renderer.hero.spells.size) + i * 40, WINDOW_HEIGHT/15*14 , 50,50,
                    null
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
    if(Renderer.hero.level%5 == 0) {
        g.drawImage(Renderer.resultspell[0].image, 750, 250, 192, 192, null)
        g.drawImage(Renderer.resultspell[1].image, 450, 250, 192, 192, null)
        g.drawImage(Renderer.resultspell[2].image, 150, 250, 192, 192, null)

    }else{
        g.drawImage(Renderer.result[0].image, 750, 250, 192, 192, null)
        g.drawImage(Renderer.result[1].image, 450, 250, 192, 192, null)
        g.drawImage(Renderer.result[2].image, 150, 250, 192, 192, null)
    }
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
            disableshopbutton()
        }

        if (GameManager.state == GameState.SHOP) {
            var alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
            g.setComposite(alpha)
            g.color = Color.BLACK
            g.fillRect(0, 0,WINDOW_WIDTH , WINDOW_HEIGHT)
            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
            g.setComposite(alpha)
            g.font = font
            g.color = Color.WHITE
            g.drawString("SHOP", 100, WINDOW_HEIGHT/5)
            g.drawString("KillCount :  ${GameManager.killCount}", WINDOW_WIDTH/2+100, WINDOW_HEIGHT/5)
            g.font = font2
            disablemenubutton()
            disableskillbutton()
            enableshopbutton()
        }

        if(GameManager.state == GameState.GAME_OVER){
            var alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)
            g.setComposite(alpha)
            g.color = Color.BLACK
            g.fillRect(0, 0,WINDOW_WIDTH , WINDOW_HEIGHT)
            alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
            g.setComposite(alpha)
            g.font = font
            g.color = Color.RED
            g.drawString("GAME OVER", WINDOW_WIDTH/2-100, WINDOW_HEIGHT/2)
buttonretour.isVisible = true
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

    fun enableshopbutton(){
       buttonretour.isVisible = true
        buttonmultiexp.isVisible = true
        buttonExplosive.isVisible = true
        buttonSpeedPlus.isVisible = true
       buttonVieMax.isVisible = true
        buttonAiment.isVisible = true
        buttonBlaster.isVisible = true
        buttonSharpness.isVisible = true
    }
    fun disableshopbutton(){
        buttonretour.isVisible = false
        buttonmultiexp.isVisible = false
        buttonExplosive.isVisible = false
        buttonSpeedPlus.isVisible = false
        buttonVieMax.isVisible = false
        buttonAiment.isVisible = false
        buttonBlaster.isVisible = false
        buttonSharpness.isVisible = false
        Renderer.f.requestFocus()
    }

    fun shop(shoplevel : Int){

            if (shoplevel < 5 || GameManager.killCount >= (500* shoplevel)) {
                GameManager.killCount -= 500 * shoplevel
                updateshoplabel()
            }
        updateshoplabel()
    }
    fun updateshoplabel(){

        buttonmultiexp.setText ("Multi EXP  ${(GameManager.shopmultiexp+1)*500}")
        buttonExplosive.setText ("Explosive  ${(GameManager.shopExplosive+1)*500}")
        buttonSpeedPlus.setText ("Speed  ${(GameManager.shopSpeedPlus+1)*500}")
        buttonVieMax.setText ("VieMax  ${(GameManager.shopVieMax+1)*500}")
        buttonAiment.setText ("Aiment  ${(GameManager.shopAiment+1)*500}")
        buttonBlaster.setText ("Blaster  ${(GameManager.shopBlaster+1)*500}")
buttonSharpness.setText("Sharpness  ${(GameManager.shopSharpness+1)*500}")

    }}
