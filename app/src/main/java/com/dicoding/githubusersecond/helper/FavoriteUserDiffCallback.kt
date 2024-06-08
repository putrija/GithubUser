package com.dicoding.githubusersecond.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.githubusersecond.data.local.entity.FavoriteUserEntity

class FavoriteUserDiffCallback(private val oldFavUserList: List<FavoriteUserEntity>, private val newFavUserList:List<FavoriteUserEntity>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavUserList.size
    override fun getNewListSize(): Int = newFavUserList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavUserList[oldItemPosition].username == newFavUserList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavUser = oldFavUserList[oldItemPosition]
        val newFavUser = newFavUserList[newItemPosition]
        return oldFavUser.username == newFavUser.username && oldFavUser.avatarUrl == newFavUser.avatarUrl
    }
}