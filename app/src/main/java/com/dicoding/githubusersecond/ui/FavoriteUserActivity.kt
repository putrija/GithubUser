package com.dicoding.githubusersecond.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubusersecond.data.remote.response.ItemsItem
import com.dicoding.githubusersecond.databinding.ActivityFavoriteUserBinding
import com.dicoding.githubusersecond.helper.SettingPreferences
import com.dicoding.githubusersecond.helper.ViewModelFactory
import com.dicoding.githubusersecond.helper.dataStore

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel: FavoriteUserViewModel by viewModels { ViewModelFactory(application, pref) }


        val layoutManager = LinearLayoutManager(this)
        binding.listFavUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listFavUsers.addItemDecoration(itemDecoration)

        val adapter = UserAdapter { item ->
            navigateToDetailActivity(item)
        }
        binding.listFavUsers.adapter = adapter

        showLoading(true)

        viewModel.getAllFavoriteUsers().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatar_url = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)

            Log.d("FavUserActivity", "Menerima ${users.size} users from database")

            showLoading(false)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
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
}
