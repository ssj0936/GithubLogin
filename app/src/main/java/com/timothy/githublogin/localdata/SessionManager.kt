package com.timothy.githublogin.localdata

import android.content.Context
import android.content.SharedPreferences
import com.timothy.githublogin.R

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
        sharePreferences.edit().putString(TOKEN,token).apply()
    }
}