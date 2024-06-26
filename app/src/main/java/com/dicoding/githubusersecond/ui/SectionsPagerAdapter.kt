package com.dicoding.githubusersecond.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""

    override fun createFragment(position: Int): Fragment {
        val fragment = ListFollowFragment()
        Log.d("SectionsPagerAdapter", "Username: $username")
        fragment.arguments = Bundle().apply {
            putInt(ListFollowFragment.ARG_POSITION, position + 1)
            putString(ListFollowFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}
