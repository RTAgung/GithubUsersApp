package com.example.githubusersapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubusersapp.model.User

@Database(entities = [User::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object {
        private var favoriteDatabase: FavoriteDatabase? = null
        fun init(context: Context): FavoriteDatabase {
            if (favoriteDatabase == null) favoriteDatabase =
                Room.databaseBuilder(context, FavoriteDatabase::class.java, "user_favorites")
                    .allowMainThreadQueries().build()
            return favoriteDatabase as FavoriteDatabase
        }
    }
}