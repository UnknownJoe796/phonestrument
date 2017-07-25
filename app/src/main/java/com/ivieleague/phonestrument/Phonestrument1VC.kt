package com.ivieleague.phonestrument

import android.content.pm.ActivityInfo
import android.view.View
import com.lightningkite.kotlin.anko.getActivity
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.lifecycle.bind
import com.lightningkite.kotlin.lifecycle.listen
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import org.billthefarmer.mididriver.MidiDriver
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent

/**
 * Created by josep on 7/24/2017.
 */
class Phonestrument1VC(val stack: VCStack) : AnkoViewController() {
    val key = StandardObservableProperty<Scale>(Scale(Scales.major))
    val chord = StandardObservableProperty<Chord?>(null)

    override fun createView(ui: AnkoContext<VCContext>): View = ui.linearLayout {
        getActivity()?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        layoutBass(0, key, chord).lparams(0, matchParent, 1f)
    }
}