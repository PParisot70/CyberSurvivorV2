object LevelTimer{
    var sync = Object()
    var niveau : Int = 1
    var nbenemy : Int = 10
    var pecentofbulk : Double = 0.0
    var pecentofranged : Double = 0.0
    var pecentofspeedster : Double = 1.0
    var healthmutiply : Double = 1.0
    var velmultiply: Double = 1.0


fun levelChange( lvl : Int){
    niveau = lvl
    synchronized(sync) {
        when (niveau) {
            1 -> {}
            2 -> {
                nbenemy = 15
                healthmutiply = 1.5
            }

            3 -> {
                nbenemy = 20
                healthmutiply = 2.0
            }

            4 -> {
                nbenemy = 25
                healthmutiply = 2.5
            }

            5 -> {
                nbenemy = 30

                healthmutiply = 3.0
            }

            6 -> {
                nbenemy = 35
                healthmutiply = 3.5
            }

            7 -> {
                nbenemy = 45
                velmultiply = 1.5
                healthmutiply = 4.0
            }

            8 -> {
                nbenemy = 50
                healthmutiply = 4.5
            }

            9 -> {
                nbenemy = 60
                healthmutiply = 5.0
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
                healthmutiply = 8.0
            }

            13 -> {
                nbenemy = 120
                velmultiply = 3.0
                healthmutiply = 9.0
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
            }

            18 -> {
                nbenemy = 400
                healthmutiply = 17.0
                velmultiply = 3.5
            }

            19 -> {
                nbenemy = 500
                healthmutiply = 20.0
            }

            20 -> {
                nbenemy = 0
                healthmutiply = 25.0
            }


        }
    }

}
}
