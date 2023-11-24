package com.example.githubuser.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.remote.response.ItemsItem
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.ui.adapter.ItemAdapter
import com.example.githubuser.ui.viewmodel.MainViewModel
import com.example.githubuser.ui.viewmodel.ModeViewModel
import com.example.githubuser.ui.viewmodel.ModeViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val adapter: ItemAdapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val modeViewModel = ViewModelProvider(this, ModeViewModelFactory(pref)).get(
            ModeViewModel::class.java
        )

        modeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchBar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu1 -> {
                        favorite()

                        true
                    }

                    R.id.menu2 -> {
                        setting()

                        true
                    }

                    else -> false
                }
            }

            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text);
                    searchView.hide()

                    val query = searchView.text.toString()
                    mainViewModel.searchUser(query)

                    false
                }
        }

        mainViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        mainViewModel.items.observe(this) { items ->
            if (items != null) {
                showRecycleView(items)
            }
        }
    }

    private fun showRecycleView(item: List<ItemsItem>) {
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.setHasFixedSize(true)
        adapter.submitList(item)
        binding.rvItems.adapter = adapter

        adapter.setOnItemClickCallback(object : ItemAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                selectedUser(data)
            }
        })
    }

    private fun selectedUser(user: ItemsItem) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user.login)

        startActivity(intent)
    }

    private fun favorite() {
        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)

        startActivity(intent)
    }

    private fun setting() {
        val intent = Intent(this@MainActivity, ModeActivity::class.java)

        startActivity(intent)
    }
}