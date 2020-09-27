package com.flyn.kobe.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flyn.kobe.bean.ArticleData
import com.flyn.kobe.bean.CategoryData
import com.flyn.kobe.net.HttpServiceApi
import com.flyn.kobe.net.RetrofitBuilder
import com.flyn.kobe.utils.L
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleListViewModel : ViewModel() {


    var articleData = MutableLiveData<List<ArticleData>>(ArrayList())


    fun getArticleData(pageNum: Int, category: CategoryData) {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.IO) {
                    RetrofitBuilder().buildRetrofit(HttpServiceApi::class.java).getArticleData(category.type, pageNum, 10)
                }
                articleData.value = data.result
            }
            catch (e: Exception) {
                L.e("getBannerData", e)
            }
        }
    }
}