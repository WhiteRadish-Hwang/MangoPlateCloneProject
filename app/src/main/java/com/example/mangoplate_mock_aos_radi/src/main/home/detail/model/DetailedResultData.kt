package com.example.mangoplate_mock_aos_radi.src.main.home.detail.model

import java.io.Serializable

data class ImgsResultData (val  imgId: Int,
                           val  reviewImgUrl: String)

data class DetailedInfoResultData ( val inspection: Int,
                                    val restaurantId: Int,
                                    val restaurantName: String,
                                    val star: String,
                                    val restaurantView: Int,
                                    val likeCount: Int,
                                    val reviewCount: Int,
                                    val userLike: Int,
                                    val uservisited: Int,
                                    val restaurantInfo: String,
                                    val restaurantLocation: String,
                                    val restaurantLatitude: String,
                                    val restaurantLongitude: String,
                                    val restaurantPhoneNumber: String,
                                    val restaurantTime: String,
                                    val restaurantHoliday: String,
                                    val restaurantRestTime: String,
                                    val restaurantPrice: String,
                                    val restaurantMenu: String)

data class MenuImgResultData (val restaurantMenuImgUrl: String)

data class KeyWordResultData (val restaurantKeyWord: String)

data class ReviewCountResultData (val reviewCount: Int,
                                  val deliciousCount: Int,
                                  val okayCount: Int,
                                  val badCount: Int)

data class ReviewResultData (val reviewId: Int,
                             val userProfileImgUrl: String,
                             val userName: String,
                             val isHolic: Int,
                             val reviewExpression: String,
                             val userReviewCount: Int,
                             val userFollower: Int,
                             val reviewContents: String,
                             val reviewLikeCount: Int,
                             val reviewReplyCount: Int,
                             val userReviewLike: Int,
                             val updatedAt: String): Serializable

data class ReviewImgResultData (val reviewId: Int,
                                val imgId: Int,
                                val reviewImgUrl: String)

data class NearRestaurantResultData (val restaurantId: Int,
                                     val restaurantName: String,
                                     val restaurantView: Int,
                                     val reviewCount: Int,
                                     val isLike: Int,
                                     val visited: Int,
                                     val star: String,
                                     val firstImageUrl: String): Serializable

data class AreaResultData (val areaName: String)