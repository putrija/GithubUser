package com.dicoding.githubusersecond.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubusersecond.data.local.entity.FavoriteUserEntity
import com.dicoding.githubusersecond.data.local.room.FavoriteUserDao
import com.dicoding.githubusersecond.data.local.room.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val mFavUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>> = mFavUserDao.getAllFavoriteUser()

    fun insert(favoriteUserEntity: FavoriteUserEntity) {
        executorService.execute { mFavUserDao.insert(favoriteUserEntity) }
    }

    fun delete(favoriteUserEntity: FavoriteUserEntity) {
        executorService.execute { mFavUserDao.delete(favoriteUserEntity) }
    }

    fun getUserByUsername(username: String): LiveData<FavoriteUserEntity?> {
        return mFavUserDao.getUserByUsername(username)
    }


}

