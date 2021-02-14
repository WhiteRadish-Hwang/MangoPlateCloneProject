package com.example.mangoplate_mock_aos_radi.src.main.home.detail

import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.DetailsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsRetrofitInterface {

    @GET("/restaurants/{restaurantId}")
    fun getDetails(@Path("restaurantId") restaurantId: Int) : Call<DetailsResponse>

    @PATCH("/restaurants/{restaurantId}/like")
    fun patchWannago(@Path("restaurantId") restaurantId: Int) : Call<PatchWannagoResponse>
}