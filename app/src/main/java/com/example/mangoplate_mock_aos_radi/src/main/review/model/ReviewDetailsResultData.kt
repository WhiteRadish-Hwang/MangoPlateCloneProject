package com.example.mangoplate_mock_aos_radi.src.main.review.model

import com.google.gson.JsonArray
import java.io.Serializable

data class ReviewDetailsResultData (val restaurantId: Int,
                                    val reviewId: Int,
                                    val userId: Int,
                                    val userName: String,
                                    val isHolic: Int,
                                    val userProfileImgUrl: String,
                                    val userReviewCount: Int,
                                    val userFollowerCount: Int,
                                    val reviewExpression: Int,
                                    val reviewContents: String,
                                    val restaurantName: String,
                                    val restaurantLocation: String,
                                    val reviewLikeCount: Int,
                                    val reviewReplyCount: Int,
                                    val updatedAt: String,
                                    val restaurantLikeStatus: Int,
                                    val reviewLikeStatus: Int

): Serializable

data class ReviewImgListResultData (
        val imgId: Int,
        val reviewImgUrl: String
)

data class ReviewReplyListResultData (
        val replyId: Int,
        val replyUserId: Int,
        val replyUserName: String,
        val isHolic: Int,
        val replyUserProfileImgUrl: String,
        val replyContents: String,
        val updatedAt: String,
        val commentUserList: ArrayList<String>
)