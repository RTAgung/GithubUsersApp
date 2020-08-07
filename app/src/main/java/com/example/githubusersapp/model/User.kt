package com.example.githubusersapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_favorites")
data class User(
    @PrimaryKey
    @ColumnInfo(index = true, name = "Id")
    val id: Int? = null,
    @ColumnInfo(name = "Login")
    val login: String? = null,
    @ColumnInfo(name = "AvatarUrl")
    val avatar_url: String? = null,
    @ColumnInfo(name = "Name")
    val name: String? = null,
    @ColumnInfo(name = "Company")
    val company: String? = null,
    @ColumnInfo(name = "Location")
    val location: String? = null,
    @ColumnInfo(name = "Bio")
    val bio: String? = null,
    @ColumnInfo(name = "PublicRepos")
    val public_repos: Int? = null,
    @ColumnInfo(name = "Followers")
    val followers: Int? = null,
    @ColumnInfo(name = "Following")
    val following: Int? = null
) : Parcelable