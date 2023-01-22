import Bonus.Powerup
import Spell.*
import sprite.Sprite
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.time.Duration
import java.time.Instant
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.Timer


object Renderer : JPanel() {
    const val FRAMES_PER_SEC = 60
    const val FRAME_IN_MSEC = 1000 / FRAMES_PER_SEC
    const val WINDOW_WIDTH = 1080
    const val WINDOW_HEIGHT = 720
    var upPressed = false
    var downPressed = false
    var leftPressed = false
    var rightPressed = false
    var escapePressed = false



    val temptile = mutableListOf<Tile>()
    val maps = mutableListOf<Tile>()
    val entities = mutableListOf<Enemy>()
    val xps = mutableListOf<Experience>()
    val drawables = mutableListOf<Drawable>()
    val drawablestemp = mutableListOf<Drawable>()
    val hero = Hero(0, 0, 70  )

    var stepTimer = Timer(FRAME_IN_MSEC) { e: ActionEvent? -> stepGame() }
    var time = Instant.now()


    private var totalPausedTime = Duration.ZERO

    private var pauseStartTime: Instant? = null

    var filteredList = hero.bonus.filter { it.level < 5 }
    var shuffledList = filteredList.shuffled()
    var result = shuffledList.take(3)

    var filteredListspell = hero.spells.filter { it.level < 5 }
    var shuffledListspell = filteredListspell.shuffled()
    var  resultspell = shuffledListspell.take(3)
    val f = JFrame()
    // Constructeur : initialise le temps de départ et le temps cumulé des pauses à 0
    init {
        preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
        background = Color.white
        totalPausedTime = Duration.ZERO
    }



    fun initGame() {
        GameManager.musique.stop()
        GameManager.musique.clip.setFramePosition(0)
        GameManager.musique.play()
        temptile.clear()
        hero.spells.clear()
        hero.bonus.clear()
        maps.clear()
        entities.clear()
        xps.clear()
        pauseStartTime = null
        drawables.clear()
        drawablestemp.clear()
        totalPausedTime = Duration.ZERO
        time = Instant.now()
        createEnnemies()
        createExperiences()
        createMap()
        hero.spells.add(ThunderAreaSpell(0))
        hero.spells.add(ThunderNucSpell(0))
        hero.spells.add(ShurikenSpell(0))
        hero.spells.add(BlasterSpell(1))


        hero.bonus.add(Powerup(PowerUpType.SHARPDAMAGE , Sprite.getSharpnessSprite(0,0), GameManager.shopSharpness))
        hero.bonus.add(Powerup(PowerUpType.EXPLODEDAMAGE , Sprite.getExplosiveSprite(0,0), GameManager.shopExplosive))
        hero.bonus.add(Powerup(PowerUpType.BLASTDAMAGE , Sprite.getBlasterSprite(0,0),GameManager.shopBlaster))
        hero.bonus.add(Powerup(PowerUpType.HEALTHMAX, Sprite.getviemaxSprite(0,0),GameManager.shopVieMax))
        hero.bonus.add(Powerup(PowerUpType.SPEED , Sprite.getSpeedPLUSSprite(0,0),GameManager.shopSpeedPlus))
        hero.bonus.add(Powerup(PowerUpType.MULTIEXP, Sprite.getExp_Sprite(0,0),GameManager.shopmultiexp))
        hero.bonus.add(Powerup(PowerUpType.AIMANT, Sprite.getAimantSprite(0,0),GameManager.shopAiment))




        this.entities.addAll(entities)
        SwingUtilities.invokeLater {

            with (f) {
                defaultCloseOperation = JFrame.EXIT_ON_CLOSE
                title = "CyberSurvivor"
                isResizable = false
                add(this@Renderer, BorderLayout.CENTER)
                pack()
                setLocationRelativeTo(null)
                isVisible = true
            }




            stepTimer.start()



            // Set up key event handler
            f.addKeyListener(object : KeyAdapter() {
                override fun keyPressed(e: KeyEvent) {
                    when (e.keyCode) {
                        KeyEvent.VK_UP -> {upPressed = true
                            }
                        KeyEvent.VK_DOWN -> downPressed = true
                        KeyEvent.VK_LEFT -> leftPressed = true
                        KeyEvent.VK_RIGHT -> rightPressed = true
                        KeyEvent.VK_ESCAPE -> escapePressed = true

                    }
                }

                override fun keyReleased(e: KeyEvent) {
                    when (e.keyCode) {
                        KeyEvent.VK_UP -> upPressed = false
                        KeyEvent.VK_DOWN -> downPressed = false
                        KeyEvent.VK_LEFT -> leftPressed = false
                        KeyEvent.VK_RIGHT -> rightPressed = false
                        KeyEvent.VK_ESCAPE -> rightPressed = false
                    }
                }
            })
        }


    }

    fun Skillselection(){
if (hero.level%5 == 0){
    filteredListspell = hero.spells.filter { it.level < 5 }
    shuffledListspell = filteredListspell.shuffled()
    resultspell = shuffledListspell.take(3)
}else{
        filteredList = hero.bonus.filter { it.level < 5 }
        shuffledList = filteredList.shuffled()
        result = shuffledList.take(3)}

    }
    fun ChooseSkill(int : Int){
        result[int].levelUp()
    }

    fun ChooseSpell(int:Int){
        if (resultspell[int].level <5){ resultspell[int].level+=1}
    }
    fun pauseGame() {
        // Mettre en pause le jeu


        if(  GameManager.state == GameState.GAME){
            pauseStartTime = Instant.now()
            GameManager.state = GameState.PAUSE

        }
        else if(  GameManager.state == GameState.PAUSE){
            println("${GameManager.state} 3 ")
            GameManager.state = GameState.GAME
            resume()

        }


       if(  GameManager.state == GameState.SKILL_SELECTION){
            println("${GameManager.state} 3 ")
            GameManager.state = GameState.GAME
            resume()
        }
    }


    // Renvoie le temps écoulé depuis le début (en tenant compte des pauses)
    val elapsedTime: Duration
        get() =// Si l'application est en pause, le temps écoulé est le temps cumulé des pauses
            if (pauseStartTime != null) {
                totalPausedTime.plus(Duration.between(pauseStartTime, Instant.now()))
            } else {
                Duration.between(time, Instant.now()).minus(totalPausedTime)
            }

    fun resume() {
        // Vérifie qu'il y a une pause en cours
        if (pauseStartTime != null) {
            // Calcul le temps écoulé pendant la pause et l'ajoute au temps cumulé des pauses
            val pauseDuration = Duration.between(pauseStartTime, Instant.now())
            totalPausedTime = totalPausedTime.plus(pauseDuration)

            // Réinitialise le temps de début de la pause
            pauseStartTime = null
        }
    }


    private fun createMap(){
        for(i in -600..WINDOW_WIDTH + 300 step Sprite.TILE_SIZE) {
            for (j in -600..WINDOW_HEIGHT + 300 step Sprite.TILE_SIZE) {
                this.maps.add(TileFactory.createTile(i, j))
            }
        }
    }
    fun updateMap(){
        maps.removeAll {hero.distanceToTile(it)}
        temptile.clear()
        maps.forEach{ it ->
            var newtilex = it.x + Sprite.TILE_SIZE
            var newtiley = it.y
            var fulltile  = maps.filter { it2 -> it2.x == newtilex && it2.y ==newtiley}
            var temptiletemp = temptile.filter{ it2 -> it2.x == newtilex && it2.y ==newtiley}
      if (!(fulltile.isNotEmpty()) && !(temptiletemp.isNotEmpty())){
          temptile.add(TileFactory.createTile(newtilex, newtiley))
      }

            newtilex = it.x
            newtiley = it.y + Sprite.TILE_SIZE
            fulltile  = maps.filter { it2 -> it2.x == newtilex && it2.y ==newtiley}
            temptiletemp = temptile.filter{ it2 -> it2.x == newtilex && it2.y ==newtiley}
            if (!(fulltile.isNotEmpty()) && !(temptiletemp.isNotEmpty())){
                 temptile.add(TileFactory.createTile(newtilex, newtiley))
      }

            newtilex = it.x
            newtiley = it.y - Sprite.TILE_SIZE
            fulltile  = maps.filter { it2 -> it2.x == newtilex && it2.y ==newtiley}
            temptiletemp = temptile.filter{ it2 -> it2.x == newtilex && it2.y ==newtiley}
            if (!(fulltile.isNotEmpty()) && !(temptiletemp.isNotEmpty())){
                 temptile.add(TileFactory.createTile(newtilex, newtiley))
      }


            newtilex = it.x - Sprite.TILE_SIZE
            newtiley = it.y
            fulltile  = maps.filter { it2 -> it2.x == newtilex && it2.y ==newtiley}
            temptiletemp = temptile.filter{ it2 -> it2.x == newtilex && it2.y ==newtiley}
            if (!(fulltile.isNotEmpty()) && !(temptiletemp.isNotEmpty())){
                 temptile.add(TileFactory.createTile(newtilex, newtiley))
      }


        }
        temptile.forEach { maps.add(it) }}

    fun addDrawable(drawable: Drawable) {
        drawables += drawable
    }

    fun updateDrawable(){
        drawables -= drawablestemp
        drawablestemp.removeAll{true}
    }
    private fun createExperiences(){

    }


    private fun createEnnemies() {
        for (i in 1..LevelTimer.nbenemy) {
           this.entities.add(EnemyFactory.createEnemy())
        }
    }

    private fun stepGame() {
        repaint()
        var lvl =elapsedTime.toMinutes()*2 +  elapsedTime.seconds/30  +1
        LevelTimer.levelChange((lvl).toInt())
        if (entities.size < LevelTimer.nbenemy){
            for (i in 1..(LevelTimer.nbenemy -  entities.size)) {
                this.entities.add(EnemyFactory.createEnemy())
            }
        }


    }


    override fun paint(gg: Graphics) {
        super.paintComponent(gg)
        val g = gg as Graphics2D
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Draw the grid lines
        val gridLines = mutableListOf<GridLine>()



        for (i in -100000..100000 step 128) {
            gridLines.add(GridLine(i, -10000, i, 10000))
        }
        for (i in -100000..100000 step 128) {
            gridLines.add(GridLine(-10000, i, 10000, i))
        }
        g.color = Color.white
        gridLines.forEach { it.draw(g) }
        maps.forEach{it.draw(g , hero.posX, hero.posY)}
        updateDrawable()

        // Draw the ennemies
        entities.forEach { it.draw(g) }
        xps.forEach{it.draw(hero.posX, hero.posY, g)}
        drawables.forEach { it.draw(g) }
        g.color = Color.RED
        hero.draw(g)
        GUI.draw(g)

        if (GameManager.state == GameState.GAME) {
            entities.forEach { it.update() }
            xps.forEach { it.update() }
            updateMap()
            hero.step()
            drawables.forEach { it.step() }
            hero.magnet()



            // Draw the hero
            // Move the Hero
            if(upPressed && leftPressed) {
                hero.moveUpLeft()
                hero.direction  = 1
            }
            else if(upPressed && rightPressed) {
                hero.moveUpRight()
                hero.direction  = 0
            }
            else if(downPressed && leftPressed) {
                hero.moveDownLeft()
                hero.direction  = 1
            }
            else if(downPressed && rightPressed) {
                hero.moveDownRight()
                hero.direction  = 0
            }
            else if(upPressed) {
                hero.moveUp()
            }
            else if(downPressed) {
                hero.moveDown()
            }
            else if(leftPressed) {
                hero.moveLeft()
                hero.direction  = 1
            }
            else if(rightPressed) {
                hero.moveRight()
                hero.direction  = 0
            }

            else {
                // Invalid moves
            }
        }

        if(escapePressed){
            pauseGame()
            escapePressed = false
        }


        // Check if the hero is in collision with an enemy
        entities.removeAll {  it.dying() }
        entities.removeAll { hero.isColliding(it) }
        xps.removeAll {hero.isCollidingxp(it)}
        if (hero.health <= 0) {
            GameManager.gameover.stop()
            GameManager.gameover.clip.setFramePosition(0)
            GameManager.gameover.play()
            GameManager.state = GameState.GAME_OVER
            hero.health ++
        }
    }



    class GridLine(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        fun draw(g: Graphics2D) {
            g.color = Color.lightGray
            g.drawLine(
                x1 - hero.posX + WINDOW_WIDTH / 2,
                y1 - hero.posY + WINDOW_HEIGHT / 2,
                x2 - hero.posX + WINDOW_WIDTH / 2,
                y2 - hero.posY + WINDOW_HEIGHT / 2)
            g.color = Color.black
        }
    }






}