package com.example.portfolio_dio.data.repositories

import android.os.RemoteException
import com.example.portfolio_dio.data.remote.GithubApi
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val githubApi: GithubApi
): IRepoRepository {
    override suspend fun listRepositories(user: String) = flow {
        try {
            val result = githubApi.listRepositories(user)
            emit(result)
        } catch (e: HttpException) {
            throw Exception(e.message() ?: "Não foi possível realizar a busca no momento")
        }
    }
}