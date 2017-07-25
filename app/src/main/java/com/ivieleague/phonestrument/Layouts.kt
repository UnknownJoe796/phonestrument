package com.ivieleague.phonestrument

import android.graphics.Color
import android.view.*
import android.widget.CompoundButton
import android.widget.EditText
import com.lightningkite.kotlin.anko.*
import com.lightningkite.kotlin.anko.observable.adapter.listAdapter
import com.lightningkite.kotlin.observable.property.MutableObservableProperty
import com.lightningkite.kotlin.observable.property.bind
import org.billthefarmer.mididriver.MidiDriver
import org.jetbrains.anko.*
import org.jetbrains.anko.design.textInputLayout

inline fun ViewManager.layoutTextField(hint: String, setup: EditText.() -> Unit): View = textInputLayout() {
    this.hint = hint
    textInputEditText {
        styleDefault()
        setup()
    }
}

inline fun ViewManager.layoutField(hint: String, setup: ViewManager.() -> View) = verticalLayout {
    leftPadding = dip(4)
    textView {
        styleTILLabel()
        text = hint
    }.lparams(matchParent, wrapContent)
    setup().lparams(matchParent, wrapContent)
}

inline fun ViewGroup.layoutTopBar(
        title: String,
        showBack: Boolean,
        crossinline onBackPressed: View.() -> Unit,
        buttons: _FrameLayout.() -> Unit
) = linearLayout {
    backgroundColor = resources.getColorCompat(R.color.colorPrimary)
    padding = dip(4)
    gravity = Gravity.CENTER_VERTICAL

    imageButton {
        backgroundResource = selectableItemBackgroundBorderlessResource
        imageResource = R.drawable.ic_arrow_back
        contentDescription = resources.getString(R.string.back)
        setOnClickListener { onBackPressed.invoke(this) }
        visibility = if (showBack) View.VISIBLE else View.INVISIBLE
    }.lparams(wrapContent, wrapContent) { margin = dip(4) }

    textView {
        text = title
        textColor = Color.WHITE
    }.lparams(wrapContent, wrapContent) { margin = dip(4) }

    frameLayout {
        buttons()
    }.lparams(0, wrapContent, 1f) { margin = dip(4) }
}

inline fun _FrameLayout.layoutSaveButton(crossinline action: () -> Unit) {
    imageButton {
        backgroundResource = selectableItemBackgroundBorderlessResource
        imageResource = R.drawable.ic_check
        contentDescription = resources.getString(R.string.save)
        setOnClickListener { action.invoke() }

    }.lparams(wrapContent, wrapContent, Gravity.RIGHT)
}

inline fun ViewManager.layoutToggle(binding: CompoundButton.() -> Unit) =
        checkBox {
            styleDefault()
            binding()
        }
