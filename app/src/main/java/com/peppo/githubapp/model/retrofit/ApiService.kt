package com.peppo.githubapp.model.retrofit

import com.peppo.githubapp.model.response.DetailUserResponse
import com.peppo.githubapp.model.response.GithubResponse
import com.peppo.githubapp.model.response.GithubUsersItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getListGithub(
        @Query("q") username: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getListFollowers(
        @Path("username") username: String
    ): Call<List<GithubUsersItem?>?>
    @GET("users/{username}/following")

    fun getListFollowing(
        @Path("username") username: String
    ): Call<List<GithubUsersItem?>?>
}