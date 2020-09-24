package com.flyn.kobe.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flyn.kobe.bean.BannerData
import com.flyn.kobe.bean.CategoryData
import com.flyn.kobe.net.HttpServiceApi
import com.flyn.kobe.net.RetrofitBuilder
import com.flyn.kobe.utils.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel : ViewModel() {

    var bannerData = MutableLiveData<List<BannerData>>(ArrayList())
    var categoryData = MutableLiveData<List<CategoryData>>(ArrayList())


    fun getBannerData() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    RetrofitBuilder().buildRetrofit(HttpServiceApi::class.java).getBannerData()
                }
                bannerData.value = data.result
            }
            catch (e: Exception) {
                L.e("getBannerData", e)
            }
        }
    }

    fun getCategoryData() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    RetrofitBuilder().buildRetrofit(HttpServiceApi::class.java).getCategoriesData()
                }
                categoryData.value = data.result
            }
            catch (e: Exception) {
                L.e("getCategoryData", e)
            }
        }

    }
}