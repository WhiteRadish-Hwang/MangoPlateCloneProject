package com.example.mangoplate_mock_aos_radi.src.main.news.model

import java.io.Serializable

data class TotalReviewResultData (val restaurantId: Int,
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
                                  val reviewImgList: ArrayList<String>,
                                  val reviewImgIdList: ArrayList<Int>,
                                  val restaurantLikeStatus: Int,
                                  val reviewLikeStatus: Int): Serializable

