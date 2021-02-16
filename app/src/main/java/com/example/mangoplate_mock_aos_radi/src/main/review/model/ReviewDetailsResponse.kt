package com.example.mangoplate_mock_aos_radi.src.main.review.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

class ReviewDetailsResponse (
        @SerializedName("result") val result: JsonArray
): BaseResponse()