package com.timothy.githublogin.localdata

import android.content.Context
import android.content.SharedPreferences
import com.timothy.githublogin.R
import timber.log.Timber

class SessionManager(context: Context) {
    companion object{
        const val TOKEN = "token"
    }

    private val sharePreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE)

    fun getToken():String?{
        return sharePreferences.getString(TOKEN,null)
    }

    fun setToken(token:String){
        Timber.d("1Thread:${Thread.currentThread().name}/${Thread.currentThread().id}")
        sharePreferences.edit().putString(TOKEN,token).apply()
    }
}