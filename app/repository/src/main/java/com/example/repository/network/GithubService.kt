package com.example.repository.network

import com.example.repository.model.Repo
import com.example.repository.model.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users/{username}/repos")
    fun listRepos(
        @Path("username") username: String
    ): Call<List<Repo>>

    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UserDto>
}