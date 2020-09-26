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

class FavViewModel : ViewModel() {


    var favBeanList = MutableLiveData<List<FavBean>>(ArrayList())


    fun getFavData(pageNum: Int) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    AppDatabase.db?.getDatabase()?.favDao()?.getAll(pageNum * 10, 10)
                }
                favBeanList.value = data
            } catch (e: Exception) {
                L.e("getFavData", e)
            }
        }
    }
}