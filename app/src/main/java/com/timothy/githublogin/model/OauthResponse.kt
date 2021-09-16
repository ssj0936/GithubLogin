package com.timothy.githublogin.model

import com.google.gson.annotations.SerializedName

data class OauthResponse(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null
)
