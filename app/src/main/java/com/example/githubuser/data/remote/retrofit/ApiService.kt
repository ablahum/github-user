package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.data.remote.response.Item
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun findUsers(
        @Query("q") keyword: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<Item>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}