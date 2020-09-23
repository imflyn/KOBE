package com.flyn.kobe.net

import com.flyn.kobe.bean.BannerData
import com.flyn.kobe.bean.BaseData
import retrofit2.http.GET

interface HttpServiceApi {


    @GET("v2/banners")
    suspend fun getBannerData(): BaseData<List<BannerData>>
}