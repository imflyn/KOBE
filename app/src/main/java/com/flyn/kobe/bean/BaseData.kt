package com.flyn.kobe.bean

import com.google.gson.annotations.SerializedName

class BaseData<T> {

    @SerializedName("data")
    var result: T? = null
    var status: Int? = null
    var page: Int? = null
    var page_count: Int? = null
    var total_counts: Int? = null

}