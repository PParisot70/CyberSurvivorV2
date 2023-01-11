class Time {
    var updateSinceStart: Int = 0

    fun getUpdateFromSeconds(seconds: Double): Double
    {
        return seconds * Renderer .FRAMES_PER_SEC
    }

    fun update(){
        updateSinceStart++
    }

    fun getFormattedTime(): String{
        val stringBuilder: StringBuilder = StringBuilder()
        val minutes: Int = updateSinceStart / Renderer.FRAMES_PER_SEC.toInt() / 60
        val seconds: Int = updateSinceStart / Renderer.FRAMES_PER_SEC.toInt() % 60

        if(minutes < 10){
            stringBuilder.append(0)
        }
        stringBuilder.append(minutes)
        stringBuilder.append(":")

        if(seconds < 10){
            stringBuilder.append(0)
        }
        stringBuilder.append(seconds)
        return stringBuilder.toString()
    }

    fun secondsDividableBy(seconds: Double): Boolean {
        return updateSinceStart % getUpdateFromSeconds(seconds) == 0.0
    }
}