package com.flyn.kobe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.flyn.kobe.bean.FavBean


@Database(entities = [FavBean::class], version = 1)
abstract class AppDatabase : RoomDatabase() {


    companion object {
        var db: AppDatabase? = null

        fun initDatabase(context: Context): AppDatabase? {
            if (db == null) {
                synchronized(AppDatabase::class.java) {
                    if (db == null) {
                        db = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "main.db"
                        ).build()
                    }
                }
            }
            return db
        }
    }

    open fun getDatabase(): AppDatabase? {
        return db
    }


    abstract fun favDao(): FavDao
}