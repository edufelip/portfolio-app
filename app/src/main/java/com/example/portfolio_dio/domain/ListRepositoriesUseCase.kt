package com.example.portfolio_dio.domain

import com.example.portfolio_dio.core.UseCase
import com.example.portfolio_dio.data.models.Repository
import com.example.portfolio_dio.data.repositories.IRepoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListRepositoriesUseCase @Inject constructor(
    val repository: IRepoRepository
): UseCase<String, List<Repository>>() {

    override suspend fun execute(param: String): Flow<List<Repository>> {
        return repository.listRepositories(param)
    }
}