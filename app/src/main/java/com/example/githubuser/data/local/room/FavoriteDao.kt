package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * from favorite_users")
    fun getUsers(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite_users WHERE username = :username")
    suspend fun deleteUser(username: String)

    @Query("SELECT EXISTS (SELECT * FROM favorite_users WHERE username = :username)")
    suspend fun isUserExist(username: String): Boolean
}
