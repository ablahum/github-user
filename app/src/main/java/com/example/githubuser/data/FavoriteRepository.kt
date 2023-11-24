package com.example.githubuser.data

import androidx.lifecycle.LiveData
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.local.room.FavoriteDao
import com.example.githubuser.data.remote.retrofit.ApiService
import com.example.githubuser.utils.AppExecutors

class FavoriteRepository(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors,
) {
    fun getAll(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getUsers()
    }

    suspend fun insert(favorite: FavoriteEntity) {
        favoriteDao.insertUser(favorite)
    }

    suspend fun delete(username: String) {
        favoriteDao.deleteUser(username)
    }

    suspend fun isUserExist(username: String): Boolean {
        return favoriteDao.isUserExist(username)
    }


    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            dao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, dao, appExecutors)
            }.also { instance = it }
    }
}