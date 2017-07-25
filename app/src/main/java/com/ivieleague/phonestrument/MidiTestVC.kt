package com.ivieleague.phonestrument

import android.view.Gravity
import android.view.View
import android.widget.SeekBar
import com.lightningkite.kotlin.anko.lifecycle
import com.lightningkite.kotlin.anko.viewcontrollers.AnkoViewController
import com.lightningkite.kotlin.anko.viewcontrollers.VCContext
import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity
import com.lightningkite.kotlin.lifecycle.bind
import com.lightningkite.kotlin.lifecycle.listen
import org.jetbrains.anko.*
import org.billthefarmer.mididriver.MidiDriver

class MidiTestVC(): AnkoViewController(){

    val driver = MidiDriver()

    override fun createView(ui: AnkoContext<VCContext>): View = ui.verticalLayout {

        padding = dip(8)

        lifecycle.bind(ui.owner.onResume){
            driver.start()
        }
        lifecycle.listen(ui.owner.onPause){
            driver.stop()
        }


        textView {
            text = "Test This Audio"
            gravity = Gravity.CENTER
        }.lparams(matchParent, wrapContent){ margin = dip(8) }

        seekBar {
            max = 16
            progress = 8
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                }
            })
        }.lparams(matchParent, wrapContent){ margin = dip(8) }

        button{
            text = "On"

            setOnClickListener {
                driver.write(byteArrayOf(0x90.toByte(), 48, 63))
                driver.write(byteArrayOf(0x90.toByte(), 52, 63))
                driver.write(byteArrayOf(0x90.toByte(), 55, 63))
            }
        }

        button{
            text = "Off"

            setOnClickListener {
                driver.write(byteArrayOf(0x90.toByte(), 48, 0))
                driver.write(byteArrayOf(0x90.toByte(), 52, 0))
                driver.write(byteArrayOf(0x90.toByte(), 55, 0))
            }
        }
    }
}