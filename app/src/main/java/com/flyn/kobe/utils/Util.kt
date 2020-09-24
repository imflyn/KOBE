package com.flyn.kobe.utils

import android.graphics.Color
import com.flyn.kobe.KobeApplication

object Util {


    fun isLightColor(color: Int): Boolean {
        val darkness: Double = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness < 0.5
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resId = KobeApplication.App.getContext()?.resources?.getIdentifier("status_bar_height", "dimen", "android")
        if (resId!! > 0) {
            result = KobeApplication.App.getContext()?.resources!!.getDimensionPixelOffset(resId)
        }
        return result
    }
}