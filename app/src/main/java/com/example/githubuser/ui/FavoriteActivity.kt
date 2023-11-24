package com.example.githubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.adapter.FavoriteAdapter
import com.example.githubuser.ui.viewmodel.FavoriteViewModel
import com.example.githubuser.ui.viewmodel.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val adapter: FavoriteAdapter = FavoriteAdapter()
    private val favoriteViewModel by viewModels<FavoriteViewModel>() {
        FavoriteViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        favoriteViewModel.getAll().observe(this) { favorite ->
            showRecycleView(favorite)
        }
    }

    private fun showRecycleView(item: List<FavoriteEntity>) {
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.setHasFixedSize(true)
        adapter.submitList(item)
        binding.rvItems.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteEntity) {
                selectedUser(data)
            }
        })
    }

    private fun selectedUser(user: FavoriteEntity) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user.username)

        startActivity(intent)
    }
}