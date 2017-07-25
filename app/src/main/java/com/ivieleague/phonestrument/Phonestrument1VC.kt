package com.ivieleague.phonestrument

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.getActivity
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.selectableItemBackgroundBorderlessResource
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.viewControllerDialog
import com.lightningkite.kotlin.lifecycle.bind
import com.lightningkite.kotlin.lifecycle.listen
import com.lightningkite.kotlin.observable.list.ObservableListWrapper
import com.lightningkite.kotlin.observable.list.observableListOf
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import org.billthefarmer.mididriver.MidiDriver
import org.jetbrains.anko.*

/**
 * Created by josep on 7/24/2017.
 */
class Phonestrument1VC(val stack: VCStack) : AnkoViewController() {
    val instrument = StandardObservableProperty<Int>(0)
    val key = StandardObservableProperty<Scale>(Scale(Scales.major))
    val chord = StandardObservableProperty<Chord?>(null)
    val loadout = observableListOf<Chord?>()
    init{
        loadout.replace(Chords.chords(key.value).map<Chord, Chord?> { it })
    }

    override fun createView(ui: AnkoContext<VCContext>): View = ui.frameLayout{
        linearLayout {
            getActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            layoutBass(0, key, chord, loadout).lparams(0, matchParent, 1f)

            layoutMelody(1, key, chord).lparams(0, matchParent, 2f)
        }

        linearLayout {

            textView {
                styleBig()
                gravity = Gravity.CENTER
                lifecycle.bind(key){
                    text = it.toString()
                }
                backgroundResource = selectableItemBackgroundBorderlessResource
                textColor = Color.GRAY

                setOnClickListener{
                    context.viewControllerDialog(VCStack().apply {
                        reset(SelectScaleVC(key.value, {
                            if(it != null) {
                                key.value = it
                                loadout.replace(Chords.chords(it).map<Chord, Chord?> { it })
                            }
                            pop()
                        }))
                    }, layoutParamModifier = { this.width = matchParent })
                }
            }.lparams(dip(40), dip(40))

            imageButton {
                imageResource = R.drawable.ic_music_note
                backgroundResource = selectableItemBackgroundBorderlessResource
                setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)

                setOnClickListener{
                    context.viewControllerDialog(VCStack().apply {
                        reset(SelectInstrumentVC(instrument.value, {
                            if(it != null){
                                for(i in 0 .. 15){
                                    Midi.setInstrument(i, it)
                                }
                                instrument.value = it
                            }
                            pop()
                        }))
                    }, layoutParamModifier = { this.width = matchParent })
                }
            }.lparams(dip(40), dip(40))
        }.lparams(wrapContent, wrapContent, Gravity.TOP or Gravity.RIGHT)
    }
}