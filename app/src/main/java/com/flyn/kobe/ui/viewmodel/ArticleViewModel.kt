package com.flyn.kobe.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flyn.kobe.bean.FavBean
import com.flyn.kobe.db.AppDatabase
import com.flyn.kobe.utils.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel : ViewModel() {


    var favBean = MutableLiveData<FavBean?>(null)

    fun getFav(url: String) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    AppDatabase.db?.getDatabase()?.favDao()?.get(url)
                }
                favBean.value = data
            } catch (e: Exception) {
                L.e("getFav", e)
            }
        }
    }

    fun toggleFavStatus(url: String, title: String, image: String) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    if (favBean.value == null) {
                        val favBean = FavBean().apply {
                            FavBean@ this.url = url
                            FavBean@ this.title = title
                            FavBean@ this.image = image
                        }
                        AppDatabase.db?.getDatabase()?.favDao()?.insert(favBean)
                        favBean
                    } else {
                        AppDatabase.db?.getDatabase()?.favDao()?.delete(favBean.value!!)
                        null
                    }
                }
                favBean.value = data
            } catch (e: Exception) {
                L.e("toggleFavStatus", e)
            }
        }
    }
}