package com.example.mangoplate_mock_aos_radi.src.main.myPage.model

import com.google.gson.annotations.SerializedName

data class PostEditUserPhoneNumberRequest(
        @SerializedName("userPhonNumber") val userPhonNumber: String
)