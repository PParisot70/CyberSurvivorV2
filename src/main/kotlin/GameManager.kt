import java.io.File


enum class GameState {
    GAME, SKILL_SELECTION, GAME_OVER,PAUSE,MENU,SHOP
}
object GameManager {
    var state = GameState.MENU

    var enemyCooldown = 10
    var enemySpawnCount = 1
    var drawables = listOf<Drawable>()
    var stepCount = 0
    var killCount = 7000
    var secondsSurvived = 0

     var shopmultiexp = 0
     var shopExplosive= 0
   var  shopSpeedPlus= 0
   var  shopVieMax= 0
   var shopAiment= 0
    var shopBlaster= 0
    var shopSharpness = 0

    val musique = SoundPlayer("src/main/kotlin/Sound/TURBO TAWA.wav")
    val buttonhover = SoundPlayer("src/main/kotlin/Sound/buttonhover.wav")
    val blastersound = SoundPlayer("src/main/kotlin/Sound/Blaster.wav")
    val shurikensound = SoundPlayer("src/main/kotlin/Sound/Shuriken.wav")
    val levelupsound = SoundPlayer("src/main/kotlin/Sound/levelup.wav")
    val gameover = SoundPlayer("src/main/kotlin/Sound/Gameover.wav")
val hitmarker= SoundPlayer("src/main/kotlin/Sound/hitmarker.wav")


    fun initGame() {

        createHero()
        Renderer.initGame()
    }



    private fun createHero() {
    }

    val FOLDER_PATH = "c:/temp"
    fun saveVariables() {
        val folder = File(FOLDER_PATH)
        val file = File(folder, "saved_variables.txt")
        file.writeText("$shopmultiexp,$shopExplosive,$shopSpeedPlus,$shopVieMax,$shopAiment,$shopBlaster,$shopSharpness,$killCount")
    }
    fun loadVariables() {

        val folder = File(FOLDER_PATH)
        val file = File(folder, "saved_variables.txt")
        val variables = file.readText().split(",")
        this.shopmultiexp = variables[0].toInt()
        this.shopExplosive = variables[1].toInt()
        this.shopSpeedPlus = variables[2].toInt()
        this.shopVieMax = variables[3].toInt()
        this.shopAiment = variables[4].toInt()
        this.shopBlaster = variables[5].toInt()
        this.shopSharpness = variables[6].toInt()
        this.killCount = variables[7].toInt()
    }

    fun changeProbabilities(basicProbability: Double, strongProbability: Double, fastProbability: Double) {
        EnemyType.BASIC.probability = basicProbability
        EnemyType.STRONG.probability = strongProbability
        EnemyType.FAST.probability = fastProbability
    }


}