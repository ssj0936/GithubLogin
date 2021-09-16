package com.timothy.githublogin.repository

import com.timothy.githublogin.localdata.SessionManager
import com.timothy.githublogin.model.AuthUserResponse
import com.timothy.githublogin.model.OauthResponse
import com.timothy.githublogin.model.Status
import com.timothy.githublogin.network.GithubAuthService
import com.timothy.githublogin.network.GithubService
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(
    private val githubService: GithubService,
    private val githubAuthService: GithubAuthService,
    private val sessionManager: SessionManager
) {
    private suspend fun getAuthUser(token: String): Response<AuthUserResponse> {
        return githubService.getAuthUser("Bearer $token")
    }

    suspend fun getTokenAndSave(code:String){
        val response = githubAuthService.getToken(code)

        if(response.isSuccessful){
            (response.body() as OauthResponse).accessToken?.let{
                sessionManager.setToken(it)
            }
        }
    }

    suspend fun getAuthUser(): Status<AuthUserResponse?>{
        var token = sessionManager.getToken()
        return if(token==null){
            Timber.d("get token from local fail")
            //return expire signal to activity for request token
            Status.TokenExpired()
        }else {
            val response = getAuthUser(token)

            //maybe token is expired
            if(response.code()!=200){
                Timber.d("token expired, need to re-request for the token")
                Status.TokenExpired()
            }
            //valid token, return auth user
            else {
                Timber.d("valid token, return auth user data")
                Status.TokenNormal(response.body())
            }
        }
    }
}