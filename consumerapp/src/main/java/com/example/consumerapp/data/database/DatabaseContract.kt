package com.example.consumerapp.data.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.githubusersapp"
    const val SCHEME = "content"

    class FavoriteColumn : BaseColumns {

        companion object {
            const val TABLE_NAME = "user_favorites"
            const val ID = "Id"
            const val LOGIN = "Login"
            const val AVATAR = "AvatarUrl"
            const val NAME = "Name"
            const val COMPANY = "Company"
            const val LOCATION = "Location"
            const val BIO = "Bio"
            const val REPOS = "PublicRepos"
            const val FOLLOWERS = "Followers"
            const val FOLLOWING = "Following"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}
