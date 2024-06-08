package com.dicoding.githubusersecond.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteUserEntity(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey(autoGenerate = false)
    var username: String = "",

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String = ""
) : Parcelable