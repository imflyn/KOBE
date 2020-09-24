package com.flyn.kobe.net

import com.flyn.kobe.bean.*
import retrofit2.http.GET
import retrofit2.http.Path

interface HttpServiceApi {


    @GET("v2/banners")
    suspend fun getBannerData(): BaseData<List<BannerData>>

    @GET("v2/categories/Article")
    suspend fun getCategoriesData(): BaseData<List<CategoryData>>

    @GET("v2/data/category/Article/type/{category}/page/{pageNum}/count/{pageSize}")
    suspend fun getArticleData(@Path("category") category: String, @Path("pageNum") pageNum: Int, @Path("pageSize") pageSize: Int): BaseData<List<ArticleData>>

    @GET("http://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1")
    suspend fun getHeaderUrl(): BingWallPager


}