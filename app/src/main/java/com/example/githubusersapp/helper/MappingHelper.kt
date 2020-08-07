package com.example.githubusersapp.helper

import android.database.Cursor
import com.example.githubusersapp.data.database.DatabaseContract.FavoriteColumn
import com.example.githubusersapp.model.User

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor): ArrayList<User> {
        val usersList = ArrayList<User>()

        cursor.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(FavoriteColumn.ID))
                val login = getString(getColumnIndexOrThrow(FavoriteColumn.LOGIN))
                val avatar = getString(getColumnIndexOrThrow(FavoriteColumn.AVATAR))
                val name = getString(getColumnIndexOrThrow(FavoriteColumn.NAME))
                val company = getString(getColumnIndexOrThrow(FavoriteColumn.COMPANY))
                val location = getString(getColumnIndexOrThrow(FavoriteColumn.LOCATION))
                val bio = getString(getColumnIndexOrThrow(FavoriteColumn.BIO))
                val repos = getInt(getColumnIndexOrThrow(FavoriteColumn.REPOS))
                val followers = getInt(getColumnIndexOrThrow(FavoriteColumn.FOLLOWERS))
                val following = getInt(getColumnIndexOrThrow(FavoriteColumn.FOLLOWING))
                usersList.add(
                    User(
                        id,
                        login,
                        avatar,
                        name,
                        company,
                        location,
                        bio,
                        repos,
                        followers,
                        following
                    )
                )
            }
        }
        return usersList
    }
}
