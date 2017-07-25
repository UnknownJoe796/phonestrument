package com.ivieleague.phonestrument

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.selectableItemBackgroundBorderlessResource
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.viewControllerDialog
import com.lightningkite.kotlin.lifecycle.listen
import com.lightningkite.kotlin.observable.list.ObservableList
import com.lightningkite.kotlin.observable.list.ObservableListWrapper
import com.lightningkite.kotlin.observable.property.MutableObservableProperty
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import org.jetbrains.anko.*


fun ViewGroup.layoutBass(
        channel: Int = 0,
        scale: MutableObservableProperty<Scale>,
        chord: MutableObservableProperty<Chord?>,
        chordLoadout:ObservableList<Chord?> = ObservableListWrapper(Chords.chords(scale.value).map<Chord, Chord?>{it}.toMutableList())
) = verticalLayout {

    lifecycle.listen(Midi.requires, this)

    val width = 2
    val height = 4

    for(i in chordLoadout.lastIndex .. width * height - 1){
        chordLoadout.add(null)
    }

    val editMode = StandardObservableProperty(false)

    repeat(height){ y ->
        linearLayout {
            repeat(width){ x ->
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
                        if(!editMode.value) {
                            val item = itemObs.value
                            if(item != null) {
                                when (event.actionMasked) {
                                    MotionEvent.ACTION_DOWN -> {
                                        println("Playing ${System.currentTimeMillis()}")
                                        Midi.notesOn(channel, item.notes, 1f)
                                    }
                                    MotionEvent.ACTION_UP -> Midi.notesOff(channel, item.notes, 0f)
                                }
                            }
                            true
                        } else false
                    }
                    setOnClickListener {
                        if(editMode.value){
                            context.viewControllerDialog(VCStack().apply{
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
        lifecycle.bind(editMode){
            imageResource = if(it) R.drawable.ic_cancel else R.drawable.ic_edit
            setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)
        }
        setOnClickListener {
            editMode.value = !editMode.value
        }
    }.lparams(matchParent, wrapContent)
}