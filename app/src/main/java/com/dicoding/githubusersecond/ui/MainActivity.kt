package com.dicoding.githubusersecond.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusersecond.R
import com.dicoding.githubusersecond.data.remote.response.ItemsItem
import com.dicoding.githubusersecond.databinding.ActivityMainBinding
import com.dicoding.githubusersecond.helper.SettingPreferences
import com.dicoding.githubusersecond.helper.ViewModelFactory
import com.dicoding.githubusersecond.helper.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel: MainViewModel by viewModels { ViewModelFactory(application, pref) }

        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        val layoutManager = LinearLayoutManager(this)
        binding.listUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listUsers.addItemDecoration(itemDecoration)
        val adapter = UserAdapter { item ->
            navigateToDetailActivity(item)
        }
        binding.listUsers.adapter = adapter

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.trim().toString()
                if (query.isNotEmpty()) {
                    viewModel.currentUserId = query
                    viewModel.findUser()
                }
                searchView.setupWithSearchBar(searchBar)
                searchView.hide()
                true
            }
        }

        viewModel.listUser.observe(this) { users ->
            adapter.submitList(users)
        }
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
        viewModel.getToastMessage().observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToDetailActivity(item: ItemsItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("login", item.login)
        intent.putExtra("avatar_url", item.avatar_url)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteUserActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}