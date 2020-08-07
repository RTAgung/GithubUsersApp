package com.example.githubusersapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubAPI {
    private var retrofit: Retrofit? = null
    val githubAPIInterface: GithubAPIInterface?
        get() {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit?.create(GithubAPIInterface::class.java)
        }

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val API_KEY = "3ea4d0dde85e0245d9bee6d88b2afa8eecda109c"
    }
}