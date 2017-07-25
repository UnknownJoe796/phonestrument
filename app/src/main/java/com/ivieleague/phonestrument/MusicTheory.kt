package com.ivieleague.phonestrument

object Notes {
    val c0 = 0
    val cs0 = 1
    val d0 = 2
    val ds0 = 3
    val e0 = 4
    val f0 = 5
    val fs0 = 6
    val g0 = 7
    val gs0 = 8
    val a0 = 9
    val as0 = 10
    val b0 = 11
    val c1 = 12
    val cs1 = 13
    val d1 = 14
    val ds1 = 15
    val e1 = 16
    val f1 = 17
    val fs1 = 18
    val g1 = 19
    val gs1 = 20
    val a1 = 21
    val as1 = 22
    val b1 = 23
    val c2 = 24
    val cs2 = 25
    val d2 = 26
    val ds2 = 27
    val e2 = 28
    val f2 = 29
    val fs2 = 30
    val g2 = 31
    val gs2 = 32
    val a2 = 33
    val as2 = 34
    val b2 = 35
    val c3 = 36
    val cs3 = 37
    val d3 = 38
    val ds3 = 39
    val e3 = 40
    val f3 = 41
    val fs3 = 42
    val g3 = 43
    val gs3 = 44
    val a3 = 45
    val as3 = 46
    val b3 = 47
    val c4 = 48
    val cs4 = 49
    val d4 = 50
    val ds4 = 51
    val e4 = 52
    val f4 = 53
    val fs4 = 54
    val g4 = 55
    val gs4 = 56
    val a4 = 57
    val as4 = 58
    val b4 = 59
    val c5 = 60
    val cs5 = 61
    val d5 = 62
    val ds5 = 63
    val e5 = 64
    val f5 = 65
    val fs5 = 66
    val g5 = 67
    val gs5 = 68
    val a5 = 69
    val as5 = 70
    val b5 = 71
    val c6 = 72
    val cs6 = 73
    val d6 = 74
    val ds6 = 75
    val e6 = 76
    val f6 = 77
    val fs6 = 78
    val g6 = 79
    val gs6 = 80
    val a6 = 81
    val as6 = 82
    val b6 = 83
    val c7 = 84
    val cs7 = 85
    val d7 = 86
    val ds7 = 87
    val e7 = 88
    val f7 = 89
    val fs7 = 90
    val g7 = 91
    val gs7 = 92
    val a7 = 93
    val as7 = 94
    val b7 = 95
    val c8 = 96
    val cs8 = 97
    val d8 = 98
    val ds8 = 99
    val e8 = 100
    val f8 = 101
    val fs8 = 102
    val g8 = 103
    val gs8 = 104
    val a8 = 105
    val as8 = 106
    val b8 = 107
    val c9 = 108
    val cs9 = 109
    val d9 = 110
    val ds9 = 111
    val e9 = 112
    val f9 = 113
    val fs9 = 114
    val g9 = 115
    val gs9 = 116
    val a9 = 117
    val as9 = 118
    val b9 = 119
    val c10 = 120
    val cs10 = 121
    val d10 = 122
    val ds10 = 123
    val e10 = 124
    val f10 = 125
    val fs10 = 126
    val g10 = 127

    fun letter(id: Int) = when (id modulus 12) {
        0 -> "C"
        1 -> "C#"
        2 -> "D"
        3 -> "Eb"
        4 -> "E"
        5 -> "F"
        6 -> "F#"
        7 -> "G"
        8 -> "Ab"
        9 -> "A"
        10 -> "A#"
        11 -> "B"
        else -> throw UnknownError()
    }

    fun name(id: Int) = when (id modulus 12) {
        0 -> "C"
        1 -> "C#"
        2 -> "D"
        3 -> "Eb"
        4 -> "E"
        5 -> "F"
        6 -> "F#"
        7 -> "G"
        8 -> "Ab"
        9 -> "A"
        10 -> "A#"
        11 -> "B"
        else -> throw UnknownError()
    } + (id / 12).toString()
}

object Scales {
    val chromatic = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    val major = listOf(0, 2, 4, 5, 7, 9, 11)
    val naturalMinor = listOf(0, 2, 3, 5, 7, 8, 10)
    val harmonicMinor = listOf(0, 2, 3, 5, 7, 8, 11)
    val melodicMinorUp = listOf(0, 2, 3, 5, 7, 9, 11)
    val melodicMinorDown = listOf(0, 2, 4, 5, 7, 9, 10)
    val dorian = listOf(0, 2, 3, 5, 7, 9, 10)
    val mixolydian = listOf(0, 2, 4, 5, 7, 9, 10)
    val blues = listOf(0, 3, 5, 6, 7, 10)

    val scales = listOf(
            chromatic,
            major,
            naturalMinor,
            harmonicMinor,
            melodicMinorUp,
            melodicMinorDown,
            dorian,
            mixolydian,
            blues
    )

}

data class Scale(val scale: List<Int>, val base: Int = Notes.c4) {
    fun next() = copy(base = base.plus(7).modulus(12).plus(Notes.c3))
    fun previous() = copy(base = base.plus(5).modulus(12).plus(Notes.c3))
    fun swapInPlaceMinorMajor() = if (scale == Scales.major) copy(Scales.naturalMinor) else copy(Scales.major)
    fun swapMinorMajor() = when(scale){
        Scales.major -> Scale(Scales.naturalMinor, base - 3)
        Scales.naturalMinor, Scales.harmonicMinor, Scales.melodicMinorDown, Scales.melodicMinorUp -> Scale(Scales.major, base + 3)
        else -> this
    }
}

object Chords {

    val major = listOf(0, 4, 7)
    val minor = listOf(0, 3, 7)
    val diminished = listOf(0, 3, 6)
    val augmented = listOf(0, 4, 8)

    fun chords(scale: Scale): List<Chord> = when(scale.scale){
        Scales.major -> listOf(
                triad(scale).copy(name = "I"),
                triad(scale.previous()).withinOctaveAbsolute(scale.base - 2).copy(name = "IV"),
                triad(scale.next()).withinOctaveAbsolute(scale.base - 2).copy(name = "V"),
                triad(scale.next()).add7().sub3().withinOctaveAbsolute(scale.base - 2).copy(name = "V7"),
                triad(scale.swapMinorMajor()).withinOctaveAbsolute(scale.base - 2).copy(name = "vi"),
                triad(scale.swapMinorMajor().next()).withinOctaveAbsolute(scale.base - 2).copy(name = "iii"),
                triad(scale.swapMinorMajor().previous()).withinOctaveAbsolute(scale.base - 2).copy(name = "ii")
        )
        Scales.naturalMinor, Scales.harmonicMinor, Scales.melodicMinorDown, Scales.melodicMinorUp -> listOf(
                triad(scale).copy(name = "i"),
                triad(scale.previous()).withinOctaveAbsolute(scale.base - 2).copy(name = "iv"),
                triad(scale.next()).withinOctaveAbsolute(scale.base - 2).copy(name = "v"),
                triad(scale.next()).add7().sub3().withinOctaveAbsolute(scale.base - 2).copy(name = "v7"),
                triad(scale.swapMinorMajor()).withinOctaveAbsolute(scale.base - 2).copy(name = "VI"),
                triad(scale.swapMinorMajor().next()).withinOctaveAbsolute(scale.base - 2).copy(name = "III"),
                triad(scale.swapMinorMajor().previous()).withinOctaveAbsolute(scale.base - 2).copy(name = "II")
        )
        else -> listOf(triad(scale))
    }

    fun triad(scale: Scale): Chord = Chord(scale, listOf(scale.scale[0], scale.scale[2], scale.scale[4]), if(scale.scale == Scales.major) "I" else "i")
}

data class Chord(val scale: Scale, val offsets: List<Int>, val name:String? = null) {
    val notes get() = offsets.map { it + scale.base }

    fun lower(amount: Int): Chord {
        val newOffsets = offsets.toMutableList()
        for (i in 0..amount - 1) {
            newOffsets[notes.size - 1 - (i % notes.size)] -= 12
        }
        return Chord(scale, newOffsets, name)
    }

    fun raise(amount: Int): Chord {
        val newOffsets = offsets.toMutableList()
        for (i in 0..amount - 1) {
            newOffsets[i % notes.size] += 12
        }
        return Chord(scale, newOffsets, name)
    }

    fun withinOctaveRelative(minRelativeNote: Int): Chord = Chord(scale, offsets.map {
        it.minus(minRelativeNote).modulus(12).plus(minRelativeNote)
    }, name)

    fun withinOctaveAbsolute(minNote: Int): Chord = withinOctaveRelative(minNote - scale.base)

    operator fun plus(offset: Int) = Chord(scale, offsets + offset)
    operator fun minus(offset: Int) = Chord(scale, offsets - offset)

    fun minorize(): Chord {
        val newKey = scale.copy(scale = Scales.naturalMinor)
        return Chord(newKey, offsets = offsets.map {
            val index = scale.scale.indexOf(it modulus 12)
            if (index == -1) return@map it
            else newKey.scale[index]
        })
    }

    fun majorize(): Chord {
        val newKey = scale.copy(scale = Scales.major)
        return Chord(newKey, offsets = offsets.map {
            val index = scale.scale.indexOf(it modulus 12)
            if (index == -1) return@map it
            else newKey.scale[index]
        })
    }

    fun add7(): Chord = if (scale.scale.size > 6) Chord(scale, offsets + (scale.scale[6] - 1), name?.plus("7")) else this
    fun sub3(): Chord = Chord(scale, offsets - scale.scale[2], name)
    fun sus4(): Chord = Chord(scale, offsets - scale.scale[2] + scale.scale[3], name?.plus("sus4"))
}