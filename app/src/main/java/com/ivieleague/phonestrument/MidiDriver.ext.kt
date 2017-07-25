package com.ivieleague.phonestrument

import org.billthefarmer.mididriver.MidiDriver

fun MidiDriver.setInstrument(channel:Int, instrument:Int) = write(byteArrayOf((0xC0 + channel).toByte(), instrument.toByte()))
fun MidiDriver.noteOn(channel:Int = 0, note:Int, velocity:Float) = write(byteArrayOf((0x90 + channel).toByte(), note.toByte(), velocity.times(63).toByte()))
fun MidiDriver.noteOff(channel:Int = 0, note:Int, velocity:Float) = write(byteArrayOf((0x80 + channel).toByte(), note.toByte(), velocity.times(63).toByte()))
fun MidiDriver.polyphonicPressure(channel:Int = 0, note:Int, pressure:Float) = write(byteArrayOf((0xA0 + channel).toByte(), note.toByte(), pressure.times(63).toByte()))
fun MidiDriver.pressure(channel:Int = 0, controller:Int, pressure:Float) = write(byteArrayOf((0xD0 + channel).toByte(), controller.toByte(), pressure.times(63).toByte()))
fun MidiDriver.bend(channel:Int = 0, msb:Int, lsb:Float) = write(byteArrayOf((0xE0 + channel).toByte(), msb.toByte(), lsb.times(63).toByte()))

fun MidiDriver.notesOn(channel: Int, notes:Collection<Int>, velocity: Float) = notes.forEach { noteOn(channel, it, velocity) }
fun MidiDriver.notesOff(channel: Int, notes:Collection<Int>, velocity: Float) = notes.forEach { noteOn(channel, it, velocity) }