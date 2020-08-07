package com.example.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int? = null,
    val login: String? = null,
    val avatar_url: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val bio: String? = null,
    val public_repos: Int? = null,
    val followers: Int? = null,
    val following: Int? = null
) : Parcelable