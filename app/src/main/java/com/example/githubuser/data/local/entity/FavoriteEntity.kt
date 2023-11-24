package com.example.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_users")
class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String,
)