


enum class GameState {
    GAME, SKILL_SELECTION, GAME_OVER,PAUSE,MENU
}
object GameManager {
    var state = GameState.MENU

    var enemyCooldown = 10
    var enemySpawnCount = 1
    var drawables = listOf<Drawable>()
    var stepCount = 0
    var killCount = 0
    var secondsSurvived = 0



    fun initGame() {
        createHero()
        Renderer.initGame()
    }

    fun addDrawable(drawable: Drawable) {
        drawables += drawable
    }

    fun enemyKilled() {

    }


    private fun createHero() {
    }


    fun stepGame() {

    }

    fun endGame() {
        state = GameState.GAME_OVER
    }



}