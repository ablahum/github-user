package com.example.githubuser.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<ItemsItem?>? = null
)

data class ItemsItem(
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("followers_url")
    val followersUrl: String? = null,

    @field:SerializedName("following_url")
    val followingUrl: String? = null,
)

data class Item(
    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("followers")
    val followers: String,

    @field:SerializedName("following")
    val following: String
)