package com.flyn.kobe.ui.viewmodel

import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flyn.kobe.KobeApplication
import com.flyn.kobe.bean.BannerData
import com.flyn.kobe.net.HttpServiceApi
import com.flyn.kobe.net.RetrofitBuilder
import com.flyn.kobe.utils.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel : ViewModel() {

    var bannerData = MutableLiveData<List<BannerData>>(ArrayList())


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
}