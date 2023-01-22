object LevelTimer{
    var sync = Object()
    var niveau : Int = 1
    var nbenemy : Int = 10
    var healthmutiply : Double = 1.0
    var velmultiply: Double = 1.0


fun levelChange( lvl : Int){
    niveau = lvl
    synchronized(sync) {
        when (niveau) {
            1 -> {GameManager.changeProbabilities(1.0,0.0,0.0)}
            2 -> {
                nbenemy = 50
                healthmutiply = 1.5
            }

            3 -> {
                nbenemy = 20
                healthmutiply = 2.0
                GameManager.changeProbabilities(0.9,0.0,0.1)
            }

            4 -> {
                nbenemy = 25
                healthmutiply = 2.5
            }

            5 -> {
                nbenemy = 30

                healthmutiply = 3.0
                GameManager.changeProbabilities(0.8,0.1,0.1)
            }

            6 -> {
                nbenemy = 35
                healthmutiply = 3.5
            }

            7 -> {
                nbenemy = 45
                velmultiply = 1.5
                healthmutiply = 4.0
                GameManager.changeProbabilities(0.7,0.1,0.2)
            }

            8 -> {
                nbenemy = 50
                healthmutiply = 4.5
            }

            9 -> {
                nbenemy = 60
                healthmutiply = 5.0
                GameManager.changeProbabilities(0.6,0.2,0.2)
            }

            10 -> {
                nbenemy = 70
                healthmutiply = 6.0
            }

            11 -> {
                nbenemy = 80
                healthmutiply = 7.0
            }

            12 -> {
                nbenemy = 100
                healthmutiply = 0.5
                GameManager.changeProbabilities(0.0,0.0,1.0)
            }

            13 -> {
                nbenemy = 120
                velmultiply = 3.0
                healthmutiply = 9.0
                GameManager.changeProbabilities(0.6,0.2,0.2)
            }

            14 -> {
                nbenemy = 150
                healthmutiply = 10.0
            }

            15 -> {
                nbenemy = 170
                healthmutiply = 12.0
            }

            16 -> {
                nbenemy = 200
                velmultiply = 1.4
                healthmutiply = 14.0
            }

            17 -> {
                nbenemy = 300
                healthmutiply = 15.0
                GameManager.changeProbabilities(0.4,0.3,0.3)

            }

            18 -> {
                nbenemy = 400
                healthmutiply = 17.0
                velmultiply = 3.5
                GameManager.changeProbabilities(0.2,0.4,0.4)
            }

            19 -> {
                nbenemy = 500
                healthmutiply = 20.0
                GameManager.changeProbabilities(0.0,0.5,0.5)
            }

            20 -> {
                nbenemy = 0
                healthmutiply = 25.0
            }


        }
    }

}
}
