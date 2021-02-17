package com.example.mangoplate_mock_aos_radi.src.main.news

import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealDetailsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsRetrofitInterface {

    @GET("/reviews")
    fun getTotalReviews(@Query("page") page: Int,
                       @Query("limit") limit: Int,
                       @Query("locationfilter") locationfilter1: Int,
                       @Query("locationfilter") locationfilter2: Int,
                       @Query("locationfilter") locationfilter3: Int,
                       @Query("expressionfilter") expressionfilter1: Int,
                       @Query("expressionfilter") expressionfilter2: Int,
                       @Query("expressionfilter") expressionfilter3: Int
    ) : Call<NewsResponse>




}