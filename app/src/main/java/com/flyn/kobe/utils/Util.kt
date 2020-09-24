package com.flyn.kobe.utils

import android.graphics.Color

object Util {


    fun isLightColor(color: Int): Boolean {
        val darkness: Double = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness < 0.5
    }
}