package com.timothy.githublogin.di

import com.timothy.githublogin.network.GithubAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import com.timothy.githublogin.network.GithubService
import retrofit2.converter.scalars.ScalarsConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object Module {
    @Singleton
    @Provides
    fun provideGithubService() : GithubService{
        val baseURL = "https://api.github.com/"

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthService(): GithubAuthService {
        val baseURL = "https://github.com/"

        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(ScalarsConverterFactory.create())

            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubAuthService::class.java)
    }
}