package com.flyn.kobe.utils

import android.content.Context

object ScreenUtil {
    fun getScreenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    fun getScreenDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    fun dip2px(context: Context, px: Float): Int {
        val scale = getScreenDensity(context)
        return (px * scale + 0.5).toInt()
    }
}