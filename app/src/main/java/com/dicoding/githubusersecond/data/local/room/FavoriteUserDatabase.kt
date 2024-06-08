package com.dicoding.githubusersecond.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.githubusersecond.data.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: FavoriteUserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteUserDatabase {
            if (instance == null) {
                synchronized(FavoriteUserDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserDatabase::class.java, "favorite_user_database")
                        .build()
                }
            }
            return instance as FavoriteUserDatabase
        }
    }
}
