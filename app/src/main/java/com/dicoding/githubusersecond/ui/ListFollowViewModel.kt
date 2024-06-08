package com.dicoding.githubusersecond.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubusersecond.data.remote.response.ItemsItem
import com.dicoding.githubusersecond.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFollowViewModel : ViewModel() {

    val listFollow = MutableLiveData<List<ItemsItem>>()
    val isLoading = MutableLiveData<Boolean>()

    fun getFollowers(username: String) {
        isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getFollowers(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    listFollow.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun getFollowing(username: String) {
        isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getFollowing(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                isLoading.value = false
                if (response.isSuccessful) {
                    listFollow.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                isLoading.value = false
            }
        })
    }
}