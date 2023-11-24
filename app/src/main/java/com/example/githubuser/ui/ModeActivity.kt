package com.example.githubuser.ui

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.databinding.ActivityModeBinding
import com.example.githubuser.ui.viewmodel.ModeViewModel
import com.example.githubuser.ui.viewmodel.ModeViewModelFactory

class ModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val modeViewModel = ViewModelProvider(this, ModeViewModelFactory(pref)).get(
            ModeViewModel::class.java
        )

        modeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            modeViewModel.saveThemeSetting(isChecked)
        }
    }
}