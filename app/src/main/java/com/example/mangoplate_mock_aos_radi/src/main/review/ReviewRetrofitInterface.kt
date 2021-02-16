package com.example.mangoplate_mock_aos_radi.src.main.review

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewRetrofitInterface {

    @GET("/reviews/{reviewId}")
    fun getReviewDetails(@Path("reviewId") reviewId: Int
    ) : Call<ReviewDetailsResponse>

    @PATCH("/restaurants/{restaurantId}/like")
    fun patchReviewWannago(@Path("restaurantId") restaurantId: Int) : Call<BaseResponse>

    @PATCH("/reviews/{reviewId}/like")
    fun patchReviewLike(@Path("reviewId") reviewId: Int) : Call<BaseResponse>

}