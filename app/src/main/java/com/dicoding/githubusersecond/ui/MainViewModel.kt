package com.dicoding.githubusersecond.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.githubusersecond.data.remote.response.ItemsItem
import com.dicoding.githubusersecond.data.remote.response.UserResponse
import com.dicoding.githubusersecond.data.remote.retrofit.ApiConfig
import com.dicoding.githubusersecond.helper.SettingPreferences
import com.dicoding.githubusersecond.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (private val pref: SettingPreferences) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    var currentUserId: String ="Alif"

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<Event<String>>()
    fun getToastMessage(): LiveData<Event<String>> = _toastMessage

    init {
        findUser()
    }

    fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUsers(currentUserId)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>, response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val userItems = responseBody.items
                        if (userItems.isNotEmpty()) {
                            _listUser.value = userItems
                        } else {
                            _toastMessage.value = Event("Username tidak ditemukan")
                        }
                    }
                } else {
                    _toastMessage.value = Event("Gagal: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                _toastMessage.value = Event("Gagal: ${t.message}")
            }
        })
    }





}