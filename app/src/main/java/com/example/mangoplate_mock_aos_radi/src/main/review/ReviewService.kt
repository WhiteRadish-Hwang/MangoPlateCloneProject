package com.example.mangoplate_mock_aos_radi.src.main.review

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.detail.DetailsRetrofitInterface
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.*
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewImgListResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewReplyListResultData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewService (val view: ReviewDetailsFragmentView) {

    fun tryGetReviewDtails(reviewId: Int) {
        val reviewRetrofitInterface = ApplicationClass.sRetrofit.create(ReviewRetrofitInterface::class.java)
        reviewRetrofitInterface.getReviewDetails(reviewId = reviewId).enqueue(object : Callback<ReviewDetailsResponse> {
            override fun onResponse(call: Call<ReviewDetailsResponse>, response: Response<ReviewDetailsResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(ApplicationClass.TAG, "onResponse: ${response.body()}")

                            val reviewDetailsArrayList = ArrayList<ReviewDetailsResultData>()
                            val reviewImgArrayList = ArrayList<ReviewImgListResultData>()
                            val reviewReplyArrayList = ArrayList<ReviewReplyListResultData>()

                            // 리뷰 상세페이지
                            it.result.forEach { resultItem ->
                                val resultObject = resultItem.asJsonObject

                                val reviewId = resultObject.get("reviewId").asInt
                                val userId = resultObject.get("userId").asInt
                                val userName = resultObject.get("userName").asString
                                val isHolic = resultObject.get("isHolic").asInt
                                val userProfileImgUrl = resultObject.get("userProfileImgUrl").asString
                                val userReviewCount = resultObject.get("userReviewCount").asInt
                                val userFollowerCount = resultObject.get("userFollowerCount").asInt
                                val reviewExpression = resultObject.get("reviewExpression").asInt
                                val reviewContents = resultObject.get("reviewContents").asString
                                val restaurantName = resultObject.get("restaurantName").asString
                                val restaurantLocation = resultObject.get("restaurantLocation").asString
                                val reviewLikeCount = resultObject.get("reviewLikeCount").asInt
                                val reviewReplyCount = resultObject.get("reviewReplyCount").asInt
                                val updatedAt = resultObject.get("updatedAt").asString
                                val restaurantLikeStatus = resultObject.get("restaurantLikeStatus").asInt
                                val reviewLikeStatus = resultObject.get("reviewLikeStatus").asInt

                                val reviewDetailsListItem = ReviewDetailsResultData(reviewId = reviewId, userId = userId, userName = userName, isHolic = isHolic, userProfileImgUrl = userProfileImgUrl,
                                        userReviewCount = userReviewCount, userFollowerCount = userFollowerCount, reviewExpression = reviewExpression, reviewContents = reviewContents,
                                        restaurantName = restaurantName, restaurantLocation = restaurantLocation, reviewLikeCount = reviewLikeCount, reviewReplyCount = reviewReplyCount,
                                        updatedAt = updatedAt, restaurantLikeStatus = restaurantLikeStatus, reviewLikeStatus = reviewLikeStatus)

                                reviewDetailsArrayList.add(reviewDetailsListItem)

                                // 리뷰이미지
                                val reviewImgList = resultObject.get("reviewImgList").asJsonArray
                                reviewImgList.forEach { reviewImgItem ->
                                    val reviewImgObject = reviewImgItem.asJsonObject

                                    val imgId = reviewImgObject.get("imgId").asInt
                                    val reviewImgUrl = reviewImgObject.get("reviewImgUrl").asString

                                    val reviewImgListItem = ReviewImgListResultData(imgId = imgId, reviewImgUrl = reviewImgUrl)

                                    reviewImgArrayList.add(reviewImgListItem)
                                }

                                // 댓글리스트
                                val replyList = resultObject.get("reviewReplyList").asJsonArray
                                replyList.forEach { replyItem ->
                                    val replyItemObject = replyItem.asJsonObject

                                    val replyId = replyItemObject.get("replyId").asInt
                                    val replyUserId = replyItemObject.get("replyUserId").asInt
                                    val replyUserName = replyItemObject.get("replyUserName").asString
                                    val isHolic = replyItemObject.get("isHolic").asInt
                                    val replyUserProfileImgUrl = replyItemObject.get("replyUserProfileImgUrl").asString
                                    val replyContents = replyItemObject.get("replyContents").asString
                                    val updatedAt = replyItemObject.get("updatedAt").asString

                                    val commentUserList = replyItemObject.get("commentUserList").asJsonArray
                                    val commentUserNameList = ArrayList<String>()
                                    commentUserList.forEach {
                                        val commentObject = it.asJsonObject

                                        val commentUserName = commentObject.get("commentUserName").asString
                                        commentUserNameList.add(commentUserName)
                                    }

                                    val replyListItem = ReviewReplyListResultData(replyId = replyId, replyUserId = replyUserId, replyUserName = replyUserName, isHolic = isHolic,
                                    replyUserProfileImgUrl = replyUserProfileImgUrl, replyContents = replyContents, updatedAt = updatedAt, commentUserList = commentUserNameList)

                                    reviewReplyArrayList.add(replyListItem)
                                }


                            }

                            view.onGetReviewDetailsSuccess(response.body()!!, reviewList = reviewDetailsArrayList, reviewImgList = reviewImgArrayList, replyList = reviewReplyArrayList)

                        } // end body
                    } // end 200

                } // end when

            } // end onResponse

            override fun onFailure(call: Call<ReviewDetailsResponse>, t: Throwable) {
                Log.d(ApplicationClass.TAG, "onFailure: ${t.message}")
                view.onGetReviewDetailsFailure(t.message ?: "통신 오류")
            }


        })
    }


    fun tryPatchReviewWannago(restaurantId: Int) {
        val reviewRetrofitInterface = ApplicationClass.sRetrofit.create(ReviewRetrofitInterface::class.java)
        reviewRetrofitInterface.patchReviewWannago(restaurantId).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(ApplicationClass.TAG, "onResponse: ${response.body()}")

                            view.onPatchReviewWannaGoSuccess(response = response.body()!!)

                        }
                    }

                }
            }


            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(ApplicationClass.TAG, "onFailure: ${t.message}")
                view.onPatchReviewWannaGoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchReviewLike(reviewId: Int) {
        val reviewRetrofitInterface = ApplicationClass.sRetrofit.create(ReviewRetrofitInterface::class.java)
        reviewRetrofitInterface.patchReviewLike(reviewId).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(ApplicationClass.TAG, "onResponse: ${response.body()}")

                            view.onPatchReviewLikeSuccess(response = response.body()!!)

                        }
                    }

                }
            }


            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(ApplicationClass.TAG, "onFailure: ${t.message}")
                view.onPatchReviewLikeFailure(t.message ?: "통신 오류")
            }
        })
    }




}