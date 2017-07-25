package com.ivieleague.phonestrument

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.dip
import org.jetbrains.anko.wrapContent

class FlowLayout : ViewGroup {

    private var line_height: Int = 0

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    var defaultHorizontalSpacing = dip(8)
    var defaultVerticalSpacing = dip(8)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //assert (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED);

        val width = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        var height = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        val count = childCount
        var line_height = 0

        var xpos = paddingLeft
        var ypos = paddingTop

        val childHeightMeasureSpec: Int
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
        } else {
            childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        }


        for (i in 0..count - 1) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val lp = child.layoutParams
                child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST), childHeightMeasureSpec)
                val childw = child.measuredWidth
                line_height = Math.max(line_height, child.measuredHeight + defaultVerticalSpacing)

                if (xpos + childw > width) {
                    xpos = paddingLeft
                    ypos += line_height
                }

                xpos += childw + defaultHorizontalSpacing
            }
        }
        this.line_height = line_height

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            height = ypos + line_height - defaultVerticalSpacing

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if (ypos + line_height < height) {
                height = ypos + line_height - defaultVerticalSpacing
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(wrapContent, wrapContent)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        if (p is LayoutParams) {
            return true
        }
        return false
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = r - l
        var xpos = paddingLeft
        var ypos = paddingTop

        for (i in 0..count - 1) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val childw = child.measuredWidth
                val childh = child.measuredHeight
                val lp = child.layoutParams as LayoutParams
                if (xpos + childw > width) {
                    xpos = paddingLeft
                    ypos += line_height
                }
                child.layout(xpos, ypos, xpos + childw, ypos + childh)
                xpos += childw + defaultHorizontalSpacing
            }
        }
    }

    fun View.lparams(width:Int, height:Int) = LayoutParams(width, height)
}

public inline fun ViewManager.flowLayout() = flowLayout() {}
public inline fun ViewManager.flowLayout(init: FlowLayout.() -> Unit) = ankoView({ FlowLayout(it) }, 0, init)