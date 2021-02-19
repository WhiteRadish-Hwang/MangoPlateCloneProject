package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model

import com.google.gson.annotations.SerializedName

data class PostPaymentsRequest(
        @SerializedName("eatDealId") val eatDealId : Int
)