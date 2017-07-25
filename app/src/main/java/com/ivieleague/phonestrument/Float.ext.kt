package com.ivieleague.phonestrument

class FloatQuickTrig(val steps:Int = 360){
    val inputs = (0 .. steps).asSequence().map { it.div(steps.toFloat()).times(FloatMath.PI) }
    val sines = inputs.map { it.sin() }.toList()

    val cosineShift = FloatMath.PI / 2
    val twoPi = FloatMath.PI * 2

    private inline fun toIndex(radians: Float):Int = radians.plus(twoPi).modulus(twoPi).div(twoPi).times(steps).toInt()
    fun sin(radians:Float):Float = sines[toIndex(radians)]
    fun cos(radians:Float):Float = sines[toIndex(radians + cosineShift)]
}

val FloatTrig = FloatQuickTrig(10000)