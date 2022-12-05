object LevelTimer{
    var niveau : Int = 1
    var nbenemy : Int = 10
    var pecentofbulk : Double = 0.0
    var pecentofranged : Double = 0.0
    var pecentofspeedster : Double = 1.0
    var healthmutiply : Double = 1.0
    var velmultiply: Double = 1.0


fun levelChange( lvl : Int){
    niveau = lvl
    when (niveau){
        1 -> {}
        2 -> {nbenemy = 15
            velmultiply= 1.01}
        3 -> {nbenemy = 20}
        4 -> {nbenemy =25}
        5 -> {nbenemy =30
           velmultiply= 1.1}
        6 -> {nbenemy =35}
        7 -> {nbenemy =45}
        8 -> {nbenemy =50}
        9 -> {nbenemy =60}
        10 -> {nbenemy =70
            velmultiply= 1.2}
        11 -> {nbenemy =80}
        12 -> {nbenemy =100}
        13 -> {nbenemy =120
            velmultiply= 1.3}
        14 -> {nbenemy =150}
        15 -> {nbenemy =170}
        16 -> {nbenemy =200
            velmultiply= 1.4}
        17 -> {nbenemy =300}
        18 -> {nbenemy =400
            velmultiply= 1.5}
        19 -> {nbenemy =500}
        20 -> {nbenemy = 0}


    }


}
}
