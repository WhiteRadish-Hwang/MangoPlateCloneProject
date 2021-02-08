package com.example.mangoplate_mock_aos_radi.src.login.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

data class FacebookLoginResponse (
    @SerializedName("userId") val userId: Int,
    @SerializedName("facebookId") val facebookId: BigInteger
):BaseResponse()