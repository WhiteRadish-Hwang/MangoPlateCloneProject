package com.example.mangoplate_mock_aos_radi.src.main.myPage.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class MyInfoResponse (
        @SerializedName("result") val result: JsonArray
): BaseResponse()