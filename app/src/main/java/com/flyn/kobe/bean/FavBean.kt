package com.flyn.kobe.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav")
class FavBean {

    @PrimaryKey
    var url = ""
    var title = ""
    var image = ""


    override fun toString(): String {
        return "FavBean(url='$url', title='$title', image='$image')"
    }

}