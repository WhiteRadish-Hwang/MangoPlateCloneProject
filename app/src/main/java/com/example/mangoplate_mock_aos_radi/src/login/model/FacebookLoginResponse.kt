package com.example.mangoplate_mock_aos_radi.src.login.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class FacebookLoginResponse (
        @SerializedName("jwt") val jwt: String
):BaseResponse()