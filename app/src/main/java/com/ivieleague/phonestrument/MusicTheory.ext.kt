package com.ivieleague.phonestrument

val Scales_list = mapOf(
        Scales.major to R.string.major,
        Scales.naturalMinor to R.string.natural_minor,
        Scales.harmonicMinor to R.string.harmonic_minor,
        Scales.melodicMinorUp to R.string.melodic_minor_up,
        Scales.melodicMinorDown to R.string.melodic_minor_down,
        Scales.dorian to R.string.dorian,
        Scales.mixolydian to R.string.mixolydian,
        Scales.blues to R.string.blues,
        Scales.chromatic to R.string.chromatic
)
val Scales.scaleToStringResource get() = Scales_list