package com.flyn.kobe.ui.viewmodel

import android.content.pm.PackageManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flyn.kobe.KobeApplication
import com.flyn.kobe.net.HttpServiceApi
import com.flyn.kobe.net.RetrofitBuilder
import com.flyn.kobe.utils.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HostViewModel : ViewModel() {

    var version = MutableLiveData("")
    var navHeaderUrl = MutableLiveData("")

    fun getVersion() {
        try {
            val packageInfo = KobeApplication.App.getContext()?.applicationContext?.packageManager?.getPackageInfo(KobeApplication.App.getContext()!!.packageName, 0)
            version.value = "Version：" + packageInfo?.versionName
        }
        catch (e: PackageManager.NameNotFoundException) {
            L.e("getVersion failed", e)
        }
    }

    fun getHeaderUrl() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    RetrofitBuilder().buildRetrofit(HttpServiceApi::class.java).getHeaderUrl()
                }
                navHeaderUrl.value = "http://www.bing.com/" + data.images[0].url
            }
            catch (e: Exception) {
                L.e("getHeaderUrl", e)
            }
        }
    }

}