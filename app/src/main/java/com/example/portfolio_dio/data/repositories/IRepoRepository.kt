package com.example.portfolio_dio.data.repositories

import com.example.portfolio_dio.data.models.Repository
import kotlinx.coroutines.flow.Flow

interface IRepoRepository {
    suspend fun listRepositories(user: String): Flow<List<Repository>>
}