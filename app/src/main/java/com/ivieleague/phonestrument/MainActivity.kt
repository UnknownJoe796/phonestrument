package com.ivieleague.phonestrument

import com.lightningkite.kotlin.anko.viewcontrollers.ViewController

import com.lightningkite.kotlin.anko.viewcontrollers.implementations.VCActivity


class MainActivity : VCActivity() {
    companion object{
        val vc:ViewController = MainVC()
    }
    override val viewController: ViewController
        get() = vc
}

