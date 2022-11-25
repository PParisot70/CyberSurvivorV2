class Vector {
    private var mag = 0.0
    private var xMag = 0.0
    private var yMag = 0.0
    private var angle // The angle of the vector in radians (relative to x+ axis)
            = 0.0

    @JvmOverloads
    constructor(mag: Double = 0.0, angle: Double = 0.0, isRadians: Boolean = false) {
        if (mag == 0.0) {
            yMag = 0.0
            xMag = yMag
            this.angle = xMag
            this.mag = this.angle
            return
        }
        if (isRadians) {
            angleInRadians = angle
        } else {
            angleInDegrees = angle
        }
        setMag(mag)
    }


    constructor(v: Vector) {
        mag = v.mag
        xMag = v.xMag
        yMag = v.yMag
        angle = v.angle
    }


    fun setMag(newMag: Double) {
        mag = newMag
        if (mag < 0) {
            angleInRadians = angleInRadians + Math.PI
            mag *= -1.0
        }
        val arr = CalculateXYMag(mag, angle)
        xMag = arr[0]
        yMag = arr[1]
        if (newMag == 0.0) angle = 0.0
    }

    fun setXMag(newXMag: Double) {
        xMag = newXMag
        angle = CalculateAgnle(xMag, yMag)
        setMag(CalculateMag(xMag, yMag))
    }

    fun setYMag(newYMag: Double) {
        yMag = newYMag
        angle = CalculateAgnle(xMag, yMag)
        setMag(CalculateMag(xMag, yMag))
    }

    // #region setters
    // #endregion
    // #region getters
    var angleInDegrees: Double
        get() = Math.toDegrees(angle)
        set(angle) {
            angleInRadians = Math.toRadians(angle)
        }

    var angleInRadians: Double
        get() = angle
        set(angle) {
            this.angle = angle
            val arr = CalculateXYMag(mag, angle)
            xMag = arr[0]
            yMag = arr[1]
        }

    fun getMag(): Double {
        return mag
    }

    fun getXMag(): Double {
        return xMag
    }

    fun getYMag(): Double {
        return yMag
    }
    // #endregion

    fun multiply(scalar: Double): Vector {
        setMag(mag * scalar)
        return this
    }


    fun divide(divisor: Double): Vector {
        setMag(mag / divisor)
        return this
    }


    fun add(v: Vector): Vector {
        xMag += v.xMag
        yMag += v.yMag
        mag = CalculateMag(xMag, yMag)
        angle = CalculateAgnle(xMag, yMag)
        return this
    }


    fun subtract(v: Vector): Vector {
        add(Vector(v).multiply(-1.0))
        return this
    }

    companion object {
        private fun CalculateAgnle(xMag: Double, yMag: Double): Double {
            var newAngle: Double
            if (xMag == 0.0) {
                newAngle = if (yMag > 0) {
                    Math.PI / 2
                } else {
                    Math.PI * (3 / 2)
                }
                return newAngle
            }
            newAngle = Math.atan(Math.abs(yMag / xMag))
            if (xMag < 0) {
                if (yMag < 0) {
                    newAngle += Math.PI
                } else {
                    newAngle = Math.PI - newAngle
                }
            } else if (yMag < 0) {
                newAngle = 2 * Math.PI - newAngle
            }
            return newAngle
        }

        private fun CalculateMag(xMag: Double, yMag: Double): Double {
            return Math.sqrt(Math.pow(xMag, 2.0) + Math.pow(yMag, 2.0))
        }

        private fun CalculateXYMag(mag: Double, angle: Double): DoubleArray {
            return doubleArrayOf(mag * Math.cos(angle), mag * Math.sin(angle))
        }


        fun Add(v1: Vector, v2: Vector): Vector {
            return Vector(v1).add(v2)
        }


        fun Subtract(v1: Vector, v2: Vector): Vector {
            return Vector(v1).subtract(v2)
        }

        fun DotProduct(v1: Vector, v2: Vector): Double {
            return Math.abs(v1.mag * v2.mag) * Math.cos(Math.abs(v1.angle - v2.angle))
        }


        fun Multiply(v: Vector, scalar: Double): Vector {
            return Vector(v).multiply(scalar)
        }


        fun Divide(v: Vector, divisor: Double): Vector {
            return Vector(v).divide(divisor)
        }

        fun CreateVectorFromXY(x: Double, y: Double): Vector {
            val v = Vector()
            v.angleInRadians = CalculateAgnle(x, y)
            v.setMag(CalculateMag(x, y))
            return v
        }
    }
}