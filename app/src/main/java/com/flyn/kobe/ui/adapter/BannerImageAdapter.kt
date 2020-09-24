package com.flyn.kobe.ui.adapter

import android.graphics.Bitmap
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

class BannerImageAdapter(imageUrls: List<String>) : BannerAdapter<String, BannerImageAdapter.ImageHolder>(imageUrls) {

    val drawableLiveData = MutableLiveData<Bitmap?>()

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(parent!!.context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //通过裁剪实现圆角
        BannerUtils.setBannerRound(imageView, 0f)
        return ImageHolder(imageView)
    }

    override fun onBindView(holder: ImageHolder?, data: String?, position: Int, size: Int) {
        Glide.with(holder!!.itemView)
            .asBitmap()
            .load(data)
            .addListener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    drawableLiveData.postValue(resource)
                    return false
                }
            })
            .into(holder.imageView)
    }


    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view as ImageView
    }

}

