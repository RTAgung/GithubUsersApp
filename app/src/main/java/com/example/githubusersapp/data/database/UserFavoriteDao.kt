package com.example.githubusersapp.data.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.githubusersapp.model.User

@Dao
interface UserFavoriteDao {
    @Query("SELECT EXISTS (SELECT * FROM user_favorites WHERE Id = :id)")
    fun exists(id: Int?): Boolean

    @Query("SELECT * FROM user_favorites")
    fun getAllCursor(): Cursor

    @Insert
    fun insert(userFavorites: User)

    @Delete
    fun delete(userFavorite: User)
}