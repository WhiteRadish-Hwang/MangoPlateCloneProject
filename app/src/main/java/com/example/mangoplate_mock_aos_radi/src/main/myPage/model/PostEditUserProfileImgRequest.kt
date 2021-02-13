package com.example.mangoplate_mock_aos_radi.src.main.myPage.model

import com.google.gson.annotations.SerializedName

data class PostEditUserProfileImgRequest(
        @SerializedName("userProfileImgUrl") val userProfileImgUrl: String
)