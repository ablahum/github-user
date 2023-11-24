package com.example.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.response.Item
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<Item>()
    val userDetail: LiveData<Item> = _userDetail

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    val listFollowers: LiveData<List<ItemsItem>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserDetail(username: String) {
        try {
            _isLoading.value = true

            val client = ApiConfig.getApiService().getUser(username)
            client.enqueue(object : Callback<Item> {
                override fun onResponse(
                    call: Call<Item>,
                    response: Response<Item>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _userDetail.value = response.body()
                    } else {
                        Log.e(DetailViewModel.TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(DetailViewModel.TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }

    fun getUserFollowers(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.e(DetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUserFollowing(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.e(DetailViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(DetailViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}