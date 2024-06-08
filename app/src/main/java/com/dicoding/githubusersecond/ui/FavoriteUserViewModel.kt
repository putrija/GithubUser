package com.dicoding.githubusersecond.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusersecond.data.FavoriteUserRepository
import com.dicoding.githubusersecond.data.local.entity.FavoriteUserEntity

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val mFavUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUserEntity>> {
        return mFavUserRepository.getAllFavoriteUser()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
}