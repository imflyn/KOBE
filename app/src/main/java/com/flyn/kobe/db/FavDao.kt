package com.flyn.kobe.db

import androidx.room.*
import com.flyn.kobe.bean.FavBean


@Dao
interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favBean: FavBean)

    @Query("select * from fav limit :limit offset :offset")
    suspend fun getAll(offset: Int, limit: Int): List<FavBean>

    @Query("select * from fav where url =:url")
    suspend fun get(url: String): FavBean

    @Delete
    suspend fun delete(favBean: FavBean)

}