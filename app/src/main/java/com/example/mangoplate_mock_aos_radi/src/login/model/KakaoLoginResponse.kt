package com.example.mangoplate_mock_aos_radi.src.login.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class KakaoLoginResponse (
        @SerializedName("jwt") val jwt: String
):BaseResponse()