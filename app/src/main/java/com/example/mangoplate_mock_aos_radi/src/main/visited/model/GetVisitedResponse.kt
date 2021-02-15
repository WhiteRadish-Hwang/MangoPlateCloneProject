package com.example.mangoplate_mock_aos_radi.src.main.visited.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class GetVisitedResponse (
        @SerializedName("result") val result: JsonObject
): BaseResponse()