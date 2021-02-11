package com.example.mangoplate_mock_aos_radi.src.main.home.detail.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class DetailsResponse (
        @SerializedName("result") val result: JsonObject
): BaseResponse()