package com.example.mangoplate_mock_aos_radi.src.main.review

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewReplyResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewReplyResultData
import retrofit2.Call
import retrofit2.http.*

interface ReviewRetrofitInterface {

    @GET("/reviews/{reviewId}")
    fun getReviewDetails(@Path("reviewId") reviewId: Int
    ) : Call<ReviewDetailsResponse>

    @PATCH("/restaurants/{restaurantId}/like")
    fun patchReviewWannago(@Path("restaurantId") restaurantId: Int) : Call<BaseResponse>

    @PATCH("/reviews/{reviewId}/like")
    fun patchReviewLike(@Path("reviewId") reviewId: Int) : Call<BaseResponse>

    @POST("/reviews/{reviewId}/replies")
    fun postReviewReply(@Path("reviewId") reviewId: Int,
                        @Body params: ReviewReplyResultData) : Call<ReviewReplyResponse>
}