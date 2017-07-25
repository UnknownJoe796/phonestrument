package com.ivieleague.phonestrument

import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.bindBoolean
import com.lightningkite.kotlin.anko.selectableItemBackgroundBorderlessResource
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.lifecycle.listen
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import com.lightningkite.kotlin.observable.property.bindBlind
import org.jetbrains.anko.*

class SelectChordVC(val scale: Scale, val chord: Chord, val onComplete: (Chord?) -> Unit) : AnkoViewController() {

    val scaleObs = StandardObservableProperty<Scale>(scale)

    val baseChordObs = StandardObservableProperty<Chord>(chord)

    val sevenObs = StandardObservableProperty<Boolean>(false)
    val susFourObs = StandardObservableProperty<Boolean>(false)

    val resultObs = StandardObservableProperty<Chord>(chord)

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {

        lifecycle.listen(Midi.requires, this)

        lifecycle.bindBlind(baseChordObs, sevenObs, susFourObs) {
            resultObs.value = make()
        }
        var lastPlayed = System.currentTimeMillis()
        lifecycle.listen(resultObs) {
            val now = System.currentTimeMillis()
            if (now < lastPlayed + 100) {
                return@listen
            }
            lastPlayed = now
            Midi.notesOn(0, it.notes, 1f)
            postDelayed({ Midi.notesOff(0, it.notes, 0f) }, 100)
        }

        layoutTopBar(
                title = resources.getString(R.string.select_chord),
                showBack = true,
                onBackPressed = { onComplete.invoke(null) },
                buttons = {
                    layoutSaveButton { onComplete.invoke(make()) }
                }
        ).lparams(matchParent, wrapContent)

        scrollView {
            verticalLayout {
                padding = dip(8)

                layoutField(resources.getString(R.string.base_chord)) {
                    flowLayout {
                        lifecycle.bind(scaleObs) {
                            removeAllViews()
                            for (chord in Chords.chords(it)) {
                                textView {
                                    styleBig()
                                    gravity = Gravity.CENTER
                                    backgroundResource = selectableItemBackgroundBorderlessResource
                                    text = chord.name ?: "-"
                                    setOnClickListener {
                                        baseChordObs.value = chord
                                    }
                                    minimumWidth = dip(75)
                                    minimumHeight = dip(75)
                                }
                            }
                        }
                    }
                }.lparams(matchParent, wrapContent) { margin = dip(8) }

                layoutToggle {
                    textResource = R.string.seven
                    bindBoolean(sevenObs)
                }.lparams(matchParent, wrapContent) { margin = dip(8) }

                layoutToggle {
                    textResource = R.string.sus_4
                    bindBoolean(susFourObs)
                }.lparams(matchParent, wrapContent) { margin = dip(8) }

                layoutField(resources.getString(R.string.result)) {
                    textView {
                        styleDefault()

                        lifecycle.bind(resultObs) {
                            text = it.name + " (" + it.notes.joinToString { Notes.letter(it) } + ")"
                        }
                    }
                }.lparams(matchParent, wrapContent) { margin = dip(8) }
            }
        }
    }

    fun make(): Chord = baseChordObs.value.copy()
            .let { if (sevenObs.value) it.add7().sub3() else it }
            .let { if (susFourObs.value) it.sus4() else it }
}