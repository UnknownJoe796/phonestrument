package com.ivieleague.phonestrument

import com.lightningkite.kotlin.observable.property.EnablingMutableCollection
import org.billthefarmer.mididriver.MidiDriver

object Midi: MidiDriver() {
    val requires = object :EnablingMutableCollection<Any?>(){
        override fun enable() {
            println("Enabling MIDI")
            Midi.start()
        }

        override fun disable() {
            println("Disabling MIDI")
            Midi.stop()
        }

    }
}