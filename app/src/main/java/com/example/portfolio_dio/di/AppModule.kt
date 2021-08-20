package com.example.portfolio_dio.di

import android.util.Log
import com.example.portfolio_dio.data.remote.GithubApi
import com.example.portfolio_dio.data.repositories.IRepoRepository
import com.example.portfolio_dio.data.repositories.RepoRepository
import com.example.portfolio_dio.domain.ListRepositoriesUseCase
import com.example.portfolio_dio.utils.Constants.BASE_GITHUB_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGithubApi(client: OkHttpClient, factory: GsonConverterFactory): GithubApi {
        return Retrofit.Builder()
            .baseUrl(BASE_GITHUB_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()
            .create(GithubApi::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor {
            Log.e("OkHttp", it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

    @Singleton
    @Provides
    fun provideRepoRepository(
        githubApi: GithubApi
    ) = RepoRepository(githubApi) as IRepoRepository

    @Provides
    fun provideListRepoUseCase(repository: IRepoRepository): ListRepositoriesUseCase {
        return ListRepositoriesUseCase(repository)
    }
}