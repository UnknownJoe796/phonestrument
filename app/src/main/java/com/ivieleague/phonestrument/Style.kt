package com.ivieleague.phonestrument

import android.graphics.Color
import android.widget.TextView
import com.lightningkite.kotlin.anko.textColorResource
import org.jetbrains.anko.padding
import org.jetbrains.anko.textColor

fun TextView.styleDefault(){
    textSize = 14f
    textColor = Color.BLACK
}
fun TextView.styleBig(){
    textSize = 18f
    textColor = Color.BLACK
}
fun TextView.styleTILLabel() {
    padding = 0
    textColorResource = R.color.abc_hint_foreground_material_light
    textColor = 0xFF009688.toInt()
    textSize = 12f
}