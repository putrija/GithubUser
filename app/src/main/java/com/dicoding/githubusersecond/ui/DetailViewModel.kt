package com.dicoding.githubusersecond.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusersecond.data.FavoriteUserRepository
import com.dicoding.githubusersecond.data.local.entity.FavoriteUserEntity
import com.dicoding.githubusersecond.data.remote.response.DetailUserResponse
import com.dicoding.githubusersecond.data.remote.retrofit.ApiConfig
import com.dicoding.githubusersecond.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val mFavUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _userDetails = MutableLiveData<DetailUserResponse?>()
    val userDetails: MutableLiveData<DetailUserResponse?> get() = _userDetails

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toastMessage = MutableLiveData<Event<String>>()
    fun getToastMessage(): LiveData<Event<String>> = _toastMessage

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetailData(userId: String) {
        val client = ApiConfig.getApiService().getDetailUser(userId)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userDetails = response.body()
                    if (userDetails != null) {
                        _userDetails.value = userDetails
                    }
                } else {
                    _toastMessage.value = Event("Gagal: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _toastMessage.value = Event("Gagal: ${t.message}")
            }
        })
    }

    fun insert(favoriteUserEntity: FavoriteUserEntity) {
        mFavUserRepository.insert(favoriteUserEntity)
    }

    fun delete(favoriteUserEntity: FavoriteUserEntity) {
        mFavUserRepository.delete(favoriteUserEntity)
    }

    fun getUserByUsername(username: String): LiveData<FavoriteUserEntity?> {
        return mFavUserRepository.getUserByUsername(username)
    }


}