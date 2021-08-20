package com.example.portfolio_dio.data.remote

import com.example.portfolio_dio.data.models.Repository
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{user}/repos")
    suspend fun listRepositories(@Path("user") user: String): List<Repository>
}