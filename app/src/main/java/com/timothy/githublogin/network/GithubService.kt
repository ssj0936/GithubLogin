package com.timothy.githublogin.network

import com.timothy.githublogin.model.AuthUserResponse
import com.timothy.githublogin.model.OauthResponse
import com.timothy.githublogin.utils.GITHUB_CLIENT_ID
import com.timothy.githublogin.utils.GITHUB_CLIENT_SECRET
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GithubService {

    @GET("/user")
    suspend fun getAuthUser(@Header("Authorization") token:String):Response<AuthUserResponse>
}

interface GithubAuthService {

    @GET("/login/oauth/access_token?")
    suspend fun getToken(
        @Query("code") code:String,
        @Query("client_id") id:String = GITHUB_CLIENT_ID,
        @Query("client_secret") secret:String = GITHUB_CLIENT_SECRET,
        @Header("Accept") accept:String = "application/vnd.github.v3+json",
    ):Response<OauthResponse>
}