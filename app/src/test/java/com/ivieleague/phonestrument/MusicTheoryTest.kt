package com.ivieleague.phonestrument

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).

 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class MusicTheoryTest {
    @Test
    @Throws(Exception::class)
    fun test() {
        val baseKey = Scale(Scales.major)
        println(Chords.chords(baseKey).joinToString("\n") { (it.name to it.notes.map { Notes.name(it) }).toString() })
    }

    @Test
    fun aroundTest() {
        val baseKey = Scale(Scales.major)
        val triad = Chords.triad(baseKey)
        val changedTriad = triad.withinOctaveRelative(17)
        println(triad.notes.map { Notes.name(it) })
        println(changedTriad.notes.map { Notes.name(it) })
    }
}