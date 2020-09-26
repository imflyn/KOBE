package com.flyn.kobe.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArticleViewModel : ViewModel() {


    var isFav = MutableLiveData(false)


    fun toggleFavStatus(url: String, title: String, image: String) {

    }
}