package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class PaymentsResponse (
        @SerializedName("merchant_uid") val merchant_uid: Int,
        @SerializedName("buyerName") val buyerName: String
):BaseResponse()