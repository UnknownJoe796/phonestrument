package com.ivieleague.phonestrument

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.view.View
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.selectableItemBackgroundBorderlessResource
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.anko.viewcontrollers.containers.VCStack
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.lightningkite.kotlin.lifecycle.bind
import org.jetbrains.anko.*

/**
 * Created by josep on 7/24/2017.
 */
class MainVC: AnkoViewController() {

    val stack = VCStack().apply { reset(SelectorVC(this)) }

    override fun createView(ui: AnkoContext<VCContext>): View = ui.frameLayout {
        viewContainer(ui.owner, stack).lparams(matchParent, matchParent)

        imageButton {
            imageResource = R.drawable.ic_arrow_back
            backgroundResource = selectableItemBackgroundBorderlessResource
            setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)

            lifecycle.bind(stack.onSwap, stack.current){
                visibility = if(stack.size > 1) View.VISIBLE else View.GONE
            }

            setOnClickListener{
                stack.pop()
            }
        }.lparams(dip(40), dip(40), Gravity.TOP or Gravity.LEFT)
    }
}

