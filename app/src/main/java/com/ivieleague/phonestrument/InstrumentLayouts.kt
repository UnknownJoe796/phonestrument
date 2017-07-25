package com.ivieleague.phonestrument

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewManager
import com.lightningkite.kotlin.anko.alpha
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.selectableItemBackgroundBorderlessResource
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.viewControllerDialog
import com.lightningkite.kotlin.lifecycle.listen
import com.lightningkite.kotlin.observable.list.ObservableList
import com.lightningkite.kotlin.observable.list.ObservableListWrapper
import com.lightningkite.kotlin.observable.property.MutableObservableProperty
import com.lightningkite.kotlin.observable.property.ObservableProperty
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import org.jetbrains.anko.*


fun ViewGroup.layoutBass(
        channel: Int = 0,
        scale: ObservableProperty<Scale>,
        chord: MutableObservableProperty<Chord?>,
        chordLoadout: ObservableList<Chord?> = ObservableListWrapper(Chords.chords(scale.value).map<Chord, Chord?> { it }.toMutableList())
) = verticalLayout {

    lifecycle.listen(Midi.requires, this)

    val width = 2
    val height = 4

    for (i in chordLoadout.lastIndex..width * height - 1) {
        chordLoadout.add(null)
    }

    val editMode = StandardObservableProperty(false)

    repeat(height) { y ->
        linearLayout {
            repeat(width) { x ->
                val itemObs = chordLoadout.indexObservable(y * width + x)
                textView {
                    styleBig()
                    padding = dip(8)
                    gravity = Gravity.CENTER
                    lifecycle.bind(itemObs) {
                        text = it?.name ?: "None"
                    }
                    backgroundResource = selectableItemBackgroundBorderlessResource
                    setOnTouchListener { v, event ->
                        if (!editMode.value) {
                            val item = itemObs.value
                            if (item != null) {
                                when (event.actionMasked) {
                                    MotionEvent.ACTION_DOWN -> {
                                        chord.value = item
                                        Midi.notesOn(channel, item.notes.map { it - 12 }, 1f)
                                    }
                                    MotionEvent.ACTION_UP -> {
                                        if (chord.value == item) chord.value = null
                                        Midi.notesOff(channel, item.notes.map { it - 12 }, 0f)
                                    }
                                }
                            }
                            true
                        } else false
                    }
                    setOnClickListener {
                        if (editMode.value) {
                            context.viewControllerDialog(VCStack().apply {
                                reset(SelectChordVC(scale.value, itemObs.value ?: Chords.triad(scale.value), {
                                    itemObs.value = it
                                    pop()
                                }))
                            }, layoutParamModifier = { this.width = matchParent })
                        }
                    }
                }.lparams(0, dip(100), 1f)
            }
        }.lparams(matchParent, wrapContent)
    }
    imageButton {
        backgroundResource = selectableItemBackgroundBorderlessResource
        lifecycle.bind(editMode) {
            imageResource = if (it) R.drawable.ic_cancel else R.drawable.ic_edit
            setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)
        }
        setOnClickListener {
            editMode.value = !editMode.value
        }
    }.lparams(matchParent, wrapContent)
}

fun ViewGroup.layoutMelody(
        channel: Int = 0,
        scale: ObservableProperty<Scale>,
        chord: ObservableProperty<Chord?>
) = frameLayout {

    val offset = StandardObservableProperty(0)

    verticalLayout {

        gravity = Gravity.CENTER

        layoutOctave(channel, scale, chord, offset, 1).lparams(matchParent, 0, 1f)
        layoutOctave(channel, scale, chord, offset, 0).lparams(matchParent, 0, 1f)
        layoutOctave(channel, scale, chord, offset, -1).lparams(matchParent, 0, 1f)
    }.lparams(matchParent, matchParent)

    textView {
        styleBig()
        gravity = Gravity.CENTER
        text = "#"
        backgroundResource = selectableItemBackgroundBorderlessResource

        lifecycle.bind(offset){
            textColor = if(it == 1)
                Color.BLACK
            else
                Color.GRAY
        }

        setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    offset.value = 1
                }
                MotionEvent.ACTION_UP -> {
                    offset.value = 0
                }
            }
            true
        }
    }.lparams(dip(40), dip(40), Gravity.BOTTOM or Gravity.RIGHT)
}

fun ViewManager.layoutOctave(
        channel: Int,
        scale: ObservableProperty<Scale>,
        chord: ObservableProperty<Chord?>,
        offset:ObservableProperty<Int>,
        octave:Int = 0
) = verticalLayout {
    gravity = Gravity.CENTER

    lifecycle.bind(scale){
        removeAllViews()
        when(it.scale){
            Scales.major,
            Scales.harmonicMinor,
            Scales.naturalMinor,
            Scales.melodicMinorDown,
            Scales.melodicMinorUp -> {
                linearLayout {
                    layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[1]).lparams(0, matchParent, 1f)
                    layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[3]).lparams(0, matchParent, 1f)
                    layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[5]).lparams(0, matchParent, 1f)
                    layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[6]).lparams(0, matchParent, 1f)
                }.lparams(matchParent, 0, 1f)

                linearLayout {
                    layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[0]).lparams(0, matchParent, 1f)
                    layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[2]).lparams(0, matchParent, 1f)
                    layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[4]).lparams(0, matchParent, 1f)
                    view().lparams(0, matchParent, 1f)
                }.lparams(matchParent, 0, 1f)
            }
            else -> {
                if(it.scale.size % 2 == 0){
                    linearLayout {
                        (1..it.scale.size-1 step 2).forEach { num ->
                            layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[num]).lparams(0, matchParent, 1f)
                        }
                    }.lparams(matchParent, 0, 1f)

                    linearLayout {
                        (0..it.scale.size-2 step 2).forEach { num ->
                            layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[num]).lparams(0, matchParent, 1f)
                        }
                    }.lparams(matchParent, 0, 1f)
                } else {
                    linearLayout {
                        view().lparams(0, matchParent, .5f)
                        (1..it.scale.size-2 step 2).forEach { num ->
                            layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[num]).lparams(0, matchParent, 1f)
                        }
                        view().lparams(0, matchParent, .5f)
                    }.lparams(matchParent, 0, 1f)

                    linearLayout {
                        (0..it.scale.size-1 step 2).forEach { num ->
                            layoutNote(channel, scale, chord, offset, it.base + octave * 12 + it.scale[num]).lparams(0, matchParent, 1f)
                        }
                    }.lparams(matchParent, 0, 1f)
                }
            }
        }
    }
}

fun ViewManager.layoutNote(
        channel: Int,
        scale: ObservableProperty<Scale>,
        chord: ObservableProperty<Chord?>,
        offset:ObservableProperty<Int>,
        note: Int
) = textView {
    styleBig()
    padding = dip(8)
    gravity = Gravity.CENTER
    lifecycle.bind(offset){ offset ->
        val offsetNote = note + offset
        text = Notes.name(offsetNote)
    }
    backgroundResource = selectableItemBackgroundBorderlessResource

    lifecycle.bind(chord, offset){ chord, offset ->
        val offsetNote = note + offset
        textColor = if(chord == null || offsetNote in chord.scale){
            if(chord == null || (offsetNote modulus 12) in chord.notes.map { it modulus 12 })
                Color.BLACK
            else
                Color.BLACK.alpha(.25f)
        } else
            Color.BLACK.alpha(.05f)
    }

    var lastPlayedNote = note
    setOnTouchListener { v, event ->
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                val offsetNote = note + offset.value
                lastPlayedNote = offsetNote
                Midi.noteOn(channel, offsetNote, 1f)
            }
            MotionEvent.ACTION_UP -> {
                Midi.noteOff(channel, lastPlayedNote, 0f)
            }
        }
        true
    }
}