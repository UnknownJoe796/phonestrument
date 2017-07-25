package com.ivieleague.phonestrument

import android.graphics.Color
import android.view.View
import com.lightningkite.kotlin.anko.selectableItemBackgroundResource
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity
import org.jetbrains.anko.*

class SelectorVC(val stack: VCStack) : AnkoViewController() {

    val options = listOf(
            "V1" to {stack.push(Phonestrument1VC(stack))}
    )

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {

        textView {
            backgroundResource = R.color.colorPrimary
            padding = dip(16)
            leftPadding = dip(48)
            textColor = Color.WHITE
            textSize = 16f

            text = "Instrument Select"
        }

        for(option in options){

            textView {
                styleDefault()
                padding = dip(8)
                backgroundResource = selectableItemBackgroundResource

                text = option.first

                setOnClickListener {
                    option.second.invoke()
                }

            }.lparams(matchParent, wrapContent)
        }
    }
}