package com.timothy.githublogin.di

import com.timothy.githublogin.GithubLoginApplication.Companion.appContext
import com.timothy.githublogin.localdata.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SessionModule {

    @Singleton
    @Provides
    fun provideSessionManager():SessionManager{
        return SessionManager(appContext)
    }
}