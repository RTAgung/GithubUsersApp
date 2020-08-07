package com.example.githubusersapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersapp.data.api.GithubAPI
import com.example.githubusersapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MyViewModel : ViewModel() {
    private var githubAPI = GithubAPI()
    private var user = User()
    private var listUsers = MutableLiveData<ArrayList<User>>()
    private var error: String? = null
    var isLoading = true
    var haveMainData = false

    data class SearchUserResponse(
        var items: ArrayList<User>
    )

    fun setUsers() {
        error = null
        isLoading = true
        val random = (1..RANDOM_LIMIT).random()
        githubAPI.githubAPIInterface?.getUsersAll(random)
            ?.enqueue(object : Callback<ArrayList<User>> {
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    error = t.message.toString()
                    isLoading = false
                }

                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val listItems: ArrayList<User> = usersResponse
                            listUsers.postValue(listItems)
                        }
                    } catch (e: Exception) {
                        error = e.message.toString()
                    } finally {
                        isLoading = false
                    }
                }

            })
    }

    fun setUsers(search: String) {
        error = null
        isLoading = true
        githubAPI.githubAPIInterface?.getUsersSearch(search)
            ?.enqueue(object : Callback<SearchUserResponse> {
                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    error = t.message.toString()
                    isLoading = false
                }

                override fun onResponse(
                    call: Call<SearchUserResponse>,
                    response: Response<SearchUserResponse>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val listItems: ArrayList<User> = usersResponse.items
                            listUsers.postValue(listItems)
                        }
                    } catch (e: Exception) {
                        error = e.message.toString()
                    } finally {
                        isLoading = false
                    }
                }

            })
    }

    fun setUserDetail(username: String?) {
        error = null
        isLoading = true
        githubAPI.githubAPIInterface?.getUserDetail(username)
            ?.enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    error = t.message.toString()
                    isLoading = false
                }

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    try {
                        val userResponse = response.body()
                        if (userResponse != null) {
                            user = userResponse
                        }
                    } catch (e: Exception) {
                        error = e.message.toString()
                    } finally {
                        isLoading = false
                    }
                }
            })
    }

    fun setUserFollowers(username: String) {
        error = null
        isLoading = true
        githubAPI.githubAPIInterface?.getUserFollowers(username)
            ?.enqueue(object : Callback<ArrayList<User>> {
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    error = t.message.toString()
                    isLoading = false
                }

                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val listItems: ArrayList<User> = usersResponse
                            listUsers.postValue(listItems)
                        }
                    } catch (e: Exception) {
                        error = e.message.toString()
                    } finally {
                        isLoading = false
                    }
                }

            })
    }

    fun setUserFollowing(username: String) {
        error = null
        isLoading = true
        githubAPI.githubAPIInterface?.getUserFollowing(username)
            ?.enqueue(object : Callback<ArrayList<User>> {
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    error = t.message.toString()
                    isLoading = false
                }

                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    try {
                        val usersResponse = response.body()
                        if (usersResponse != null) {
                            val listItems: ArrayList<User> = usersResponse
                            listUsers.postValue(listItems)
                        }
                    } catch (e: Exception) {
                        error = e.message.toString()
                    } finally {
                        isLoading = false
                    }
                }

            })
    }

    fun getUsers(): LiveData<ArrayList<User>> = listUsers

    fun getUserDetail(): User = user

    fun getError(): String? = error

    companion object {
        const val RANDOM_LIMIT = 10000000
    }
}