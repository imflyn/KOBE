package com.flyn.kobe.ui.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.flyn.kobe.R
import com.flyn.kobe.bean.BannerData
import com.youth.banner.adapter.BannerAdapter


class BannerImageAdapter(bannerData: List<BannerData>) : BannerAdapter<BannerData, BannerImageAdapter.BannerViewHolder>(bannerData) {

    val drawableLiveData = MutableLiveData<Bitmap?>()

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.banner_image_title, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindView(holder: BannerViewHolder?, data: BannerData?, position: Int, size: Int) {
        Glide.with(holder!!.itemView)
            .asBitmap()
            .load(data?.image)
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

        holder.title.text = data?.title
    }


    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.image)
        var title: TextView = view.findViewById(R.id.bannerTitle)
    }


}

