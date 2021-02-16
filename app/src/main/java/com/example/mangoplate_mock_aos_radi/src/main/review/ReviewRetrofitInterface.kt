package com.example.mangoplate_mock_aos_radi.src.main.review

import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewRetrofitInterface {

    @GET("/reviews/{reviewId}")
    fun getReviewDetails(@Path("reviewId") reviewId: Int
    ) : Call<ReviewDetailsResponse>

}