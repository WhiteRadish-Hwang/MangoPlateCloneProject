package com.example.mangoplate_mock_aos_radi.src.login.model

import com.google.gson.annotations.SerializedName

data class PostFacebookLoginRequest(
        @SerializedName("facebookToken") val facebookToken: String
)