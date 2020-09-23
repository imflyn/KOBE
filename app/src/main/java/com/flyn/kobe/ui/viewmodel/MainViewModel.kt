package com.flyn.kobe.ui.viewmodel

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flyn.kobe.bean.BannerData
import com.flyn.kobe.net.HttpServiceApi
import com.flyn.kobe.net.RetrofitBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel : ViewModel() {

    var version = MutableLiveData("")
    var navHeaderUrl = MutableLiveData("")
    var bannerData = MutableLiveData<List<BannerData>>(ArrayList())

    fun getVersion(context: Context) {
        try {
            val packageInfo = context.applicationContext.packageManager.getPackageInfo(context.packageName, 0)
            version.value = "Version：" + packageInfo.versionName
        }
        catch (e: PackageManager.NameNotFoundException) {
            Log.e("KOBE", "getVersion failed", e)
        }
    }

    fun getHeaderUrl() {
        navHeaderUrl.value = "https://t8.baidu.com/it/u=2247852322,986532796&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1601448823&t=15537273110362289715b2ff029e6443"
    }

    fun getBannerData() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    RetrofitBuilder().buildRetrofit(HttpServiceApi::class.java).getBannerData()
                }
                bannerData.value = data.result
            }
            catch (e: Exception) {
            }
        }

    }
}