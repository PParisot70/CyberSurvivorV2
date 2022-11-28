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
    val entities = mutableListOf<Enemy>()
    val xps = mutableListOf<Experience>()
    val hero = Hero(0, 0, 70)
    var time = Instant.now()


    init {
        preferredSize = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
        background = Color.white
    }

    fun initGame() {
        time = Instant.now()
        createEnnemies()
        createExperiences()
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

    private fun createExperiences(){
        this.xps.add(Experience(100, 100))
        this.xps.add(Experience(200, 200))
        this.xps.add(Experience(300, 300))
        this.xps.add(Experience(400, 400))
        this.xps.add(Experience(500, 500))
        this.xps.add(Experience(600, 600))
        this.xps.add(Experience(700, 700))
        this.xps.add(Experience(800, 800))
        this.xps.add(Experience(900, 900))


    }


    private fun createEnnemies() {
        for (i in 1..Level.nbenemy) {
           this.entities.add(EnemyFactory.createEnemy())
        }
    }

    private fun stepGame() {
        repaint()
        var lvl = (Duration.between(time, Instant.now()).seconds / 30).toInt()+1
        Level.levelChange(lvl)
        if (entities.size < Level.nbenemy){
            for (i in 1..(Level.nbenemy -  entities.size)) {
                this.entities.add(EnemyFactory.createEnemy())
            }
        }
    }

    override fun paint(gg: Graphics) {
        super.paintComponent(gg)
        val g = gg as Graphics2D
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        // Draw the ennemies
        entities.forEach { it.draw(hero.posX, hero.posY, g) }
        entities.forEach { it.update() }
        xps.forEach{it.draw(hero.posX, hero.posY, g)}
        xps.forEach{it.update()}
        // Draw the hero
        hero.draw(g)
        hero.magnet()
        GUI.draw(g)


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
        entities.removeAll { hero.isColliding(it) }
        xps.removeAll {hero.isCollidingxp(it)}
    }







}