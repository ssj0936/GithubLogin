package com.timothy.githublogin.model

sealed class Status<T>(
    val data:T? = null,
    val message:String? = null
) {
    class TokenNormal<T>(data:T) : Status<T>(data)
    class TokenExpired<T> : Status<T?>()
}

