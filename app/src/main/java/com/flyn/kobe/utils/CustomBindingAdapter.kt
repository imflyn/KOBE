package com.flyn.kobe.utils

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.flyn.kobe.R

object CustomBindingAdapter {


    @JvmStatic
    @BindingAdapter("url")
    fun url(view: ImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.mipmap.icon_image_default)
            .error(R.mipmap.icon_image_default)
            .into(view)
    }

}