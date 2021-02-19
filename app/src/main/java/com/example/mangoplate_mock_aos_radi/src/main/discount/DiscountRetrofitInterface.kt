package com.example.mangoplate_mock_aos_radi.src.main.discount

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealDetailsResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.PaymentsResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.PostPaymentsRequest
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResponse
import retrofit2.Call
import retrofit2.http.*

interface DiscountRetrofitInterface {

    @GET("/eat-deals/users")
    fun getEatDeal(@Query("page") page: Int,
                   @Query("limit") limit: Int,
                   @Query("userlatitude") userlatitude: Float,
                   @Query("userlongtitude") userlongtitude: Float
    ) : Call<EatDealResponse>

    @GET("/eat-deals-all")
    fun getTotalEatDeal(@Query("page") page: Int,
                        @Query("limit") limit: Int
    ) : Call<EatDealResponse>

    @GET("/eat-deals")
    fun getLocEatDeal(@Query("page") page: Int,
                      @Query("limit") limit: Int,
                      @Query("locationfilter") locationfilter_sungbuk: Int,
                      @Query("locationfilter") locationfilter_suyu: Int,
                      @Query("locationfilter") locationfilter_nowon: Int
    ) : Call<EatDealResponse>

    @GET("/top-list")
    fun getTopList(@Query("page") page: Int,
                   @Query("limit") limit: Int
    ) : Call<DiscountTopListResponse>

    @PATCH("/top-list/{topListId}")
    fun patchTopList(@Path("topListId") topListId: Int) : Call<BaseResponse>

    @GET("/eat-deals/{eatDealId}")
    fun getEatDealDetails(@Path ("eatDealId") eatDealId: Int) : Call<EatDealDetailsResponse>

    @POST("/payments")
    fun postPayments(@Body params: PostPaymentsRequest) : Call<PaymentsResponse>
}