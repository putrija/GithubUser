package com.dicoding.githubusersecond.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.githubusersecond.data.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUserEntity: FavoriteUserEntity)

    @Delete
    fun delete(favoriteUserEntity: FavoriteUserEntity)

    @Query("SELECT * from favoriteuserentity")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>>

    @Query("SELECT * FROM favoriteuserentity WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<FavoriteUserEntity?>

}
