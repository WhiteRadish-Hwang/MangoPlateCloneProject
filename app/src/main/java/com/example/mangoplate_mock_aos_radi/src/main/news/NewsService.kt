package com.example.mangoplate_mock_aos_radi.src.main.news

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGetNewsReviewItem
import com.example.mangoplate_mock_aos_radi.src.main.detail.DetailsRetrofitInterface
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.ReviewRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class NewsService (val view: NewsFragmentView) {


    fun tryGetRestaurants(page: Int,
                          limit: Int,
                          locationfilter1: Int = 1,
                          locationfilter2: Int = 2,
                          locationfilter3: Int = 3,
                          expressionfilter_delicious: Int = 2,
                          expressionfilter_good: Int = 1,
                          expressionfilter_bad: Int = -1

    ){

        val newsRetrofitInterface = ApplicationClass.sRetrofit.create(NewsRetrofitInterface::class.java)

        newsRetrofitInterface.getTotalReviews(page, limit, locationfilter1, locationfilter2, locationfilter3, expressionfilter_delicious, expressionfilter_good, expressionfilter_bad)
            .enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val reviewList = ArrayList<TotalReviewResultData>()


                            Log.d(TAG, "onResponse: ${response.body()}")

                            it.result.forEach {resultItem ->
                                val resultItemObject = resultItem.asJsonObject
                                val reviewId = resultItemObject.get("reviewId").asInt
                                val userId = resultItemObject.get("userId").asInt
                                val userName = resultItemObject.get("userName").asString
                                val isHolic = resultItemObject.get("isHolic").asInt
                                val userProfileImgUrl = resultItemObject.get("userProfileImgUrl").asString
                                val userReviewCount = resultItemObject.get("userReviewCount").asInt
                                val userFollowerCount = resultItemObject.get("userFollowerCount").asInt
                                val reviewExpression = resultItemObject.get("reviewExpression").asInt
                                val reviewContents = resultItemObject.get("reviewContents").asString
                                val restaurantName = resultItemObject.get("restaurantName").asString
                                val restaurantLocation = resultItemObject.get("restaurantLocation").asString
                                val reviewLikeCount = resultItemObject.get("reviewLikeCount").asInt
                                val reviewReplyCount = resultItemObject.get("reviewReplyCount").asInt
                                val updatedAt = resultItemObject.get("updatedAt").asString
                                val restaurantLikeStatus = resultItemObject.get("restaurantLikeStatus").asInt
                                val reviewLikeStatus = resultItemObject.get("reviewLikeStatus").asInt

                                val reviewImgList = resultItemObject.get("reviewImgList").asJsonArray
                                val reviewImgArrayList = ArrayList<String>()
                                val reviewImgIdArrayList = ArrayList<Int>()
                                reviewImgList.forEach {reviewImgItem ->
                                    val reviewImgListItem = reviewImgItem.asJsonObject
                                    val reviewImgId = reviewImgListItem.get("imgId").asInt
                                    val reviewImgUrl = reviewImgListItem.get("reviewImgUrl").asString

                                    reviewImgIdArrayList.add(reviewImgId)
                                    reviewImgArrayList.add(reviewImgUrl)
                                }

                                val reviewListItem = TotalReviewResultData(reviewId = reviewId, userId = userId, userName = userName, isHolic = isHolic, userProfileImgUrl = userProfileImgUrl,
                                userReviewCount = userReviewCount, userFollowerCount = userFollowerCount, reviewExpression = reviewExpression, reviewContents = reviewContents,
                                restaurantName = restaurantName, restaurantLocation = restaurantLocation, reviewLikeCount = reviewLikeCount, reviewReplyCount = reviewReplyCount,
                                updatedAt = updatedAt, restaurantLikeStatus = restaurantLikeStatus, reviewLikeStatus = reviewLikeStatus, reviewImgList = reviewImgArrayList,
                                        reviewImgIdList = reviewImgIdArrayList)

                                reviewList.add(reviewListItem)
                            }
                            view.onGetTotalReviewSuccess(response.body()!!, reviewList)
                        }

                    }
                }
                isGetNewsReviewItem = true

            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetTotalReviewFailure(t.message ?: "통신 오류")
            }

            })
    }


}