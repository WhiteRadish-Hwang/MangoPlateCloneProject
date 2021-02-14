package com.example.mangoplate_mock_aos_radi.src.main.register.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.annotations.SerializedName
import java.math.BigInteger

class RegisterResponse (
        @SerializedName ("restaurantId") val restaurantId: Int
):BaseResponse()