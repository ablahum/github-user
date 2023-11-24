package com.example.githubuser.ui.viewmodel

//import com.example.githubuser.di.Injection
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.FavoriteRepository
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.di.Injection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavoriteViewModelFactory? = null

        fun getInstance(context: Context): FavoriteViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favoriteRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : ViewModel() {
    private val _isExist = MutableLiveData<Boolean>()
    val isExist: LiveData<Boolean> = _isExist

    fun getAll() = favoriteRepository.getAll()

    fun insert(favorite: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.insert(favorite)

        }

    }

    fun delete(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteRepository.delete(username)
        }
    }

    fun isUserExist(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isExist.postValue(
                favoriteRepository.isUserExist(username)
            )
        }
    }
}