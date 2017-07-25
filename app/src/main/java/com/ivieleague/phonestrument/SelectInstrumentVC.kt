package com.ivieleague.phonestrument

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.bindBoolean
import com.lightningkite.kotlin.anko.observable.bindInt
import com.lightningkite.kotlin.anko.selectableItemBackgroundBorderlessResource
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.lifecycle.listen
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import com.lightningkite.kotlin.observable.property.bindBlind
import org.jetbrains.anko.*

class SelectInstrumentVC(val instrument:Int, val onComplete: (Int?) -> Unit) : AnkoViewController() {

    val currentObs = StandardObservableProperty(instrument)

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {

        lifecycle.listen(Midi.requires, this)

        var lastPlayed = System.currentTimeMillis()
        lifecycle.listen(currentObs) {
            val now = System.currentTimeMillis()
            if (now < lastPlayed + 100) {
                return@listen
            }
            lastPlayed = now
            Midi.setInstrument(4, instrument)
            Midi.noteOn(4, Notes.c4, 1f)
            postDelayed({ Midi.noteOff(4, Notes.c4, 0f) }, 100)
        }

        layoutTopBar(
                title = resources.getString(R.string.select_instrument),
                showBack = true,
                onBackPressed = { onComplete.invoke(null) },
                buttons = {
                    layoutSaveButton { onComplete.invoke(currentObs.value) }
                }
        ).lparams(matchParent, wrapContent)

        scrollView {
            verticalLayout {
                padding = dip(8)

                layoutTextField(resources.getString(R.string.instrument_number)){
                    bindInt(currentObs)
                }.lparams(matchParent, wrapContent){ margin = dip(8) }

                linearLayout {
                    gravity = Gravity.CENTER
                    imageButton {
                        padding = dip(4)
                        backgroundResource = selectableItemBackgroundBorderlessResource
                        imageResource = R.drawable.ic_navigate_before
                        setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)

                        setOnClickListener {
                            currentObs.value = (currentObs.value - 1).coerceIn(0, 127)
                        }
                    }.lparams(dip(32), dip(32)){ margin = dip(8) }

                    imageButton {
                        padding = dip(4)
                        backgroundResource = selectableItemBackgroundBorderlessResource
                        imageResource = R.drawable.ic_navigate_next
                        setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)

                        setOnClickListener {
                            currentObs.value = (currentObs.value + 1).coerceIn(0, 127)
                        }
                    }.lparams(dip(32), dip(32)){ margin = dip(8) }
                }.lparams(matchParent, wrapContent)
            }
        }
    }
}