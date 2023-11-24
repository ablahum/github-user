package com.example.githubuser.ui.viewmodel

//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
//import com.example.githubuser.ui.SettingPreferences
//
//class ViewModelFactory(
//    private val pref: SettingPreferences,
//) : NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ModeViewModel::class.java)) {
//            return ModeViewModel(pref) as T
//        }
//
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
//}