package com.example.mangoplate_mock_aos_radi.src.main.myPage.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class MyInfoResultData (val userId: Int,
                             val userProfileImgUrl: String,
                             val userName: String,
                             val userEmail: String?,
                             val userPhoneNumber: String?,
                             val userFollowerCount: Int,
                             val userFollowingCount: Int,
                             val userReviewCount: Int,
                             val userVisitedCount: Int,
                             val userUploadImgCount: Int,
                             val userLikeCount: Int,
                             val userMyListCount: Int,
                             val userBookMarkCount: Int)