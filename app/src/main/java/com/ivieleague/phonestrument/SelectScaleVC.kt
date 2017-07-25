package com.ivieleague.phonestrument

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.observable.adapter.standardAdapter
import com.lightningkite.kotlin.anko.selectableItemBackgroundBorderlessResource
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.observable.property.StandardObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import org.jetbrains.anko.*

class SelectScaleVC(val scale: Scale, val onComplete: (Scale?) -> Unit) : AnkoViewController() {

    val scaleObs = StandardObservableProperty<List<Int>>(scale.scale)
    val baseObs = StandardObservableProperty<Int>(scale.base)

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {

        layoutTopBar(
                title = resources.getString(R.string.select_scale),
                showBack = true,
                onBackPressed = { onComplete.invoke(null) },
                buttons = {
                    layoutSaveButton { onComplete.invoke(Scale(scaleObs.value, baseObs.value)) }
                }
        ).lparams(matchParent, wrapContent)

        scrollView {
            verticalLayout {
                padding = dip(8)

                layoutField(resources.getString(R.string.scale_type)) {
                    spinner {
                        adapter = standardAdapter(Scales.scales, scaleObs) { itemObs ->
                            textView {
                                styleDefault()
                                padding = dip(8)
                                lifecycle.bind(itemObs) {
                                    textResource = Scales.scaleToStringResource[it] ?: R.string.app_name
                                }
                            }
                        }
                    }
                }.lparams(matchParent, wrapContent) { margin = dip(8) }

                layoutField(resources.getString(R.string.base_note)) {
                    spinner {
                        adapter = standardAdapter((Notes.c4..Notes.b4).toList(), baseObs) { itemObs ->
                            textView {
                                styleDefault()
                                padding = dip(8)
                                lifecycle.bind(itemObs) {
                                    text = Notes.letter(it)
                                }
                            }
                        }
                    }
                }.lparams(matchParent, wrapContent) { margin = dip(8) }

                linearLayout {
                    gravity = Gravity.CENTER
                    imageButton {
                        padding = dip(4)
                        backgroundResource = selectableItemBackgroundBorderlessResource
                        imageResource = R.drawable.ic_navigate_before
                        setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)

                        setOnClickListener {
                            val new = Scale(scaleObs.value, baseObs.value).previous()
                            baseObs.value = new.base
                            scaleObs.value = new.scale
                        }
                    }.lparams(dip(32), dip(32)){ margin = dip(8) }

                    imageButton {
                        padding = dip(4)
                        backgroundResource = selectableItemBackgroundBorderlessResource
                        imageResource = R.drawable.ic_navigate_before
                        setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY)

                        setOnClickListener {
                            val new = Scale(scaleObs.value, baseObs.value).next()
                            baseObs.value = new.base
                            scaleObs.value = new.scale
                        }
                    }.lparams(dip(32), dip(32)){ margin = dip(8) }
                }

            }.lparams(matchParent, wrapContent)
        }
    }
}