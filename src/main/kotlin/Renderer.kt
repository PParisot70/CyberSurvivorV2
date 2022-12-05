import Spell.AreaSpell
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
    val backgroundImage = Sprite.getTileSprite(0,0)


    val temptile = mutableListOf<Tile>()
val maps = mutableListOf<Tile>()
    val entities = mutableListOf<Enemy>()
    val xps = mutableListOf<Experience>()
    val drawables = mutableListOf<Drawable>()
    val drawablestemp = mutableListOf<Drawable>()

    val hero = Hero(0, 0, 70  )
    var time = Instant.now()
    var rdn : Double = 1.0




    init {
        preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
        background = Color.white
    }

    fun initGame() {
        time = Instant.now()
        createEnnemies()
        createExperiences()
        createMap()
        hero.spells.add(AreaSpell(3))



        this.entities.addAll(entities)
        SwingUtilities.invokeLater {
            val f = JFrame()
            with (f) {
                defaultCloseOperation = JFrame.EXIT_ON_CLOSE
                title = "Survival"
                isResizable = false
                add(this@Renderer, BorderLayout.CENTER)
                pack()
                setLocationRelativeTo(null)
                isVisible = true
            }


            var stepTimer = Timer(FRAME_IN_MSEC) { e: ActionEvent? -> stepGame() }
            stepTimer.start()





            // Set up key event handler
            f.addKeyListener(object : KeyAdapter() {
                override fun keyPressed(e: KeyEvent) {
                    when (e.keyCode) {
                        KeyEvent.VK_UP -> upPressed = true
                        KeyEvent.VK_DOWN -> downPressed = true
                        KeyEvent.VK_LEFT -> leftPressed = true
                        KeyEvent.VK_RIGHT -> rightPressed = true
                    }
                }

                override fun keyReleased(e: KeyEvent) {
                    when (e.keyCode) {
                        KeyEvent.VK_UP -> upPressed = false
                        KeyEvent.VK_DOWN -> downPressed = false
                        KeyEvent.VK_LEFT -> leftPressed = false
                        KeyEvent.VK_RIGHT -> rightPressed = false
                    }
                }
            })
        }
    }

    private fun createMap(){
        for(i in -300..WINDOW_WIDTH + 300 step Sprite.TILE_SIZE) {
            for (j in -300..WINDOW_HEIGHT + 300 step Sprite.TILE_SIZE) {
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
        this.xps.add(Experience(100, 100,2))
        this.xps.add(Experience(200, 200,2))
        this.xps.add(Experience(300, 300,2))



    }


    private fun createEnnemies() {
        for (i in 1..LevelTimer.nbenemy) {
           this.entities.add(EnemyFactory.createEnemy())
        }
    }

    private fun stepGame() {
        repaint()
        var lvl = (Duration.between(time, Instant.now()).seconds / 30).toInt()+1
        LevelTimer.levelChange(lvl)
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
        entities.forEach { it.update() }
        xps.forEach{it.draw(hero.posX, hero.posY, g)}
        xps.forEach{it.update()}
        updateMap()

        println(drawables)
        // Draw the hero
        hero.draw(g)
        hero.step()
        drawables.forEach { it.step() }
        drawables.forEach { it.draw(g) }


        hero.magnet()
        GUI.draw(g)

        g.color = Color.RED


        // Move the Hero
        if(upPressed && leftPressed) {
            hero.moveUpLeft()
        }
        else if(upPressed && rightPressed) {
            hero.moveUpRight()
        }
        else if(downPressed && leftPressed) {
            hero.moveDownLeft()
        }
        else if(downPressed && rightPressed) {
            hero.moveDownRight()
        }
        else if(upPressed) {
            hero.moveUp()
        }
        else if(downPressed) {
            hero.moveDown()
        }
        else if(leftPressed) {
            hero.moveLeft()
        }
        else if(rightPressed) {
            hero.moveRight()
        }
        else {
            // Invalid moves
        }



        // Check if the hero is in collision with an enemy
        entities.removeAll {  it.dying() }
        entities.removeAll { hero.isColliding(it) }
        xps.removeAll {hero.isCollidingxp(it)}

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