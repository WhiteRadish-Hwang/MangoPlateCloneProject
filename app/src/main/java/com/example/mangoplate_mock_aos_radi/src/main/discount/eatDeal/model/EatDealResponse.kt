package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class EatDealResponse (
        @SerializedName("result") val result: JsonArray
): BaseResponse()