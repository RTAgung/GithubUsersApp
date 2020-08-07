package com.example.githubusersapp.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubusersapp.data.database.DatabaseContract.AUTHORITY
import com.example.githubusersapp.data.database.DatabaseContract.FavoriteColumn.Companion.TABLE_NAME
import com.example.githubusersapp.data.database.FavoriteDatabase
import com.example.githubusersapp.data.database.UserFavoriteDao

class FavoriteProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        context?.let { userFavoriteDao = FavoriteDatabase.init(it).userFavoriteDao() }
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            FAVORITE_ID -> userFavoriteDao.getAllCursor()
            else -> null
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    companion object {
        private const val FAVORITE_ID = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userFavoriteDao: UserFavoriteDao

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE_ID)
        }
    }
}