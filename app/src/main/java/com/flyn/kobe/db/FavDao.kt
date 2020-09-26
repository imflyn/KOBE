package com.flyn.kobe.db

import androidx.room.*
import com.flyn.kobe.bean.FavBean


@Dao
interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favBean: FavBean)

    @Query("select * from fav")
    suspend fun getAll(): List<FavBean>

    @Query("select * from fav where url =:url")
    suspend fun get(url: String): FavBean

    @Delete
    suspend fun delete(favBean: FavBean)

}