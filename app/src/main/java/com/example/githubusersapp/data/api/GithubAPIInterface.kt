package com.example.githubusersapp.data.api

import com.example.githubusersapp.data.api.GithubAPI.Companion.API_KEY
import com.example.githubusersapp.model.User
import com.example.githubusersapp.viewmodel.MyViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPIInterface {

    @Headers("Authorization: Token $API_KEY")
    @GET("users?")
    fun getUsersAll(
        @Query("since") random: Int
    ): Call<ArrayList<User>>

    @Headers("Authorization: Token $API_KEY")
    @GET("search/users?")
    fun getUsersSearch(
        @Query("q") searchKey: String?
    ): Call<MyViewModel.SearchUserResponse>

    @Headers("Authorization: Token $API_KEY")
    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String?
    ): Call<User>

    @Headers("Authorization: Token $API_KEY")
    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String?
    ): Call<ArrayList<User>>

    @Headers("Authorization: Token $API_KEY")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String?
    ): Call<ArrayList<User>>
}