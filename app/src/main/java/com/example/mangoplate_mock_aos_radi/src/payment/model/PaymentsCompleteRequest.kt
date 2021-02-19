package com.example.mangoplate_mock_aos_radi.src.payment.model

import com.google.gson.annotations.SerializedName

data class PaymentsCompleteRequest(
        @SerializedName("imp_uid") val imp_uid : Int,
        @SerializedName("merchant_uid") val merchant_uid : Int
)