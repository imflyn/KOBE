package com.flyn.kobe.net

import com.flyn.kobe.bean.BannerData
import com.flyn.kobe.bean.BaseData
import com.flyn.kobe.bean.BingWallPager
import retrofit2.http.GET

interface HttpServiceApi {


    @GET("v2/banners")
    suspend fun getBannerData(): BaseData<List<BannerData>>

    @GET("http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1")
    suspend fun getHeaderUrl(): BingWallPager
}