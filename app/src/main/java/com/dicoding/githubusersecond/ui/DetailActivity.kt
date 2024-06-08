package com.dicoding.githubusersecond.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dicoding.githubusersecond.R
import com.dicoding.githubusersecond.data.local.entity.FavoriteUserEntity
import com.dicoding.githubusersecond.data.remote.response.DetailUserResponse
import com.dicoding.githubusersecond.databinding.ActivityDetailBinding
import com.dicoding.githubusersecond.helper.SettingPreferences
import com.dicoding.githubusersecond.helper.ViewModelFactory
import com.dicoding.githubusersecond.helper.dataStore
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val detailViewModel: DetailViewModel by viewModels { ViewModelFactory(application, pref) }

        supportActionBar?.hide()

        showLoading(true)

        val userId = intent.getStringExtra("login")
        val avatar_url = intent.getStringExtra("avatar_url")

        Log.d("DetailActivity", "UserId: $userId")
        Log.d("DetailActivity", "avatar_url: $avatar_url")
        if (userId != null) {
            detailViewModel.getDetailData(userId)
            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            sectionsPagerAdapter.username = userId
            binding.viewPager.adapter = sectionsPagerAdapter
        } else {
            Toast.makeText(this, "Username tidak ditemukan!", Toast.LENGTH_SHORT).show()
        }


        binding.fab.setOnClickListener {
            if (userId != null && avatar_url != null) {
                val favoriteUserEntity = FavoriteUserEntity(userId, avatar_url)
                val userLiveData = detailViewModel.getUserByUsername(userId)
                userLiveData.observe(this) { user ->
                    if (user != null) {
                        detailViewModel.delete(favoriteUserEntity)
                    } else {
                        detailViewModel.insert(favoriteUserEntity)
                    }
                    userLiveData.removeObservers(this)
                }
            } else {
                Toast.makeText(this, "Data null", Toast.LENGTH_SHORT).show()
            }
        }

        if (userId != null) {
            detailViewModel.getUserByUsername(userId).observe(this) { user ->
                if (user != null) {
                    binding.fab.setImageResource(R.drawable.ic_favorite)
                } else {
                    binding.fab.setImageResource(R.drawable.ic_favorite_border)
                }
            }
        }


        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Followers"
                1 -> "Following"
                else -> ""
            }
        }.attach()


        detailViewModel.userDetails.observe(this) { userDetails ->
            userDetails?.let { updateUI(it) }
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        detailViewModel.getToastMessage().observe(this) { event ->
            event.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(userDetails: DetailUserResponse) {
        binding.tvUsername.text = userDetails.login
        binding.tvName.text = userDetails.name
        Glide.with(this)
            .load(userDetails.avatarUrl)
            .into(binding.ivPhoto)
        binding.tvFollowers.text = "${userDetails.followers} Followers"
        binding.tvFollowing.text = "${userDetails.following} Following"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}