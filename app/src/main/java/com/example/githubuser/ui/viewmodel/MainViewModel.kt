package com.example.githubuser.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.UserResponse
import com.example.githubuser.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUsers = MutableLiveData<List<ItemsItem>?>()
    val items: MutableLiveData<List<ItemsItem>?> = _listUsers

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun searchUser(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().findUsers(username)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _listUsers.value = response.body()?.items as List<ItemsItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}