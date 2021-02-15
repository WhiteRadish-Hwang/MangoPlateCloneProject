package com.example.mangoplate_mock_aos_radi.src.main.detail

import com.example.mangoplate_mock_aos_radi.src.main.detail.model.DetailsImageResponse
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.DetailsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface DetailsRetrofitInterface {

    @GET("/restaurants/{restaurantId}")
    fun getDetails(@Path("restaurantId") restaurantId: Int) : Call<DetailsResponse>

    @PATCH("/restaurants/{restaurantId}/like")
    fun patchWannago(@Path("restaurantId") restaurantId: Int) : Call<PatchWannagoResponse>

    @GET("/reviews/images/{imgId}")
    fun getDetailsImage(@Path("imgId") imgId: Int) : Call<DetailsImageResponse>
}