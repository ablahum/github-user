package com.example.githubuser.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.remote.response.Item
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.ui.adapter.SectionsPagerAdapter
import com.example.githubuser.ui.viewmodel.DetailViewModel
import com.example.githubuser.ui.viewmodel.FavoriteViewModel
import com.example.githubuser.ui.viewmodel.FavoriteViewModelFactory
import com.example.githubuser.ui.viewmodel.ModeViewModel
import com.example.githubuser.ui.viewmodel.ModeViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        FavoriteViewModelFactory.getInstance(application)
    }
    private var isFavorited: Boolean = false

    companion object {
        const val EXTRA_USER = "extra_user"
        var EXTRA_IMAGE = "extra_image"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val modeViewModel = ViewModelProvider(this, ModeViewModelFactory(pref)).get(
            ModeViewModel::class.java
        )

        val username = intent.getStringExtra(EXTRA_USER).toString()
        detailViewModel.getUserDetail(username)
        favoriteViewModel.isUserExist(username)

        detailViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        detailViewModel.userDetail.observe(this) { user ->
            showUserData(user)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = username
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

            val textBlack = ContextCompat.getColorStateList(this, R.color.black)
            val textWhite = ContextCompat.getColorStateList(this, R.color.white)

            modeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    binding.tabs.tabTextColors = textWhite
                } else {
                    binding.tabs.tabTextColors = textBlack
                }
            }

        }.attach()
        supportActionBar?.elevation = 0f

        binding.fabFavorite.setOnClickListener {
            if (isFavorited) {
                favoriteViewModel.delete(username)
            } else {
                val user = FavoriteEntity(
                    username, EXTRA_IMAGE
                )

                favoriteViewModel.insert(user)
            }

            favoriteViewModel.isUserExist(username)
        }

        favoriteViewModel.isExist.observe(this) { boolean ->
            isFavorited = boolean

            if (boolean == true) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_fill)
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_outline)
            }
        }

        Log.i("DetailActivity", username)
    }

    private fun showUserData(user: Item) {
        binding.tvName.text = user.name
        binding.tvUsername.text = user.username
        binding.tvTotalFollowers.text = user.followers
        binding.tvTotalFollowing.text = user.following
        EXTRA_IMAGE = user.avatarUrl
        Glide.with(this)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.ivAvatar)
    }
}