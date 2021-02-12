package com.example.mangoplate_mock_aos_radi.src.main.home

import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRetrofitInterface {

    @GET("/restaurants")
    fun getRestaurants(@Query("page") page: Int,
                       @Query("limit") limit: Int,
                       @Query("areaName") areaName: String,
                       @Query("distance") distance: Int,
                       @Query("sort") sort: Int,
                       @Query("userLatitude") userLatitude: Float,
                       @Query("userLongitude") userLongitude: Float,
                       @Query("restaurantPriceFilter") restaurantPriceFilter1: Int?,
                       @Query("restaurantPriceFilter") restaurantPriceFilter2: Int?,
                       @Query("restaurantPriceFilter") restaurantPriceFilter3: Int?,
                       @Query("restaurantPriceFilter") restaurantPriceFilter4: Int?,
                       @Query("restaurantFilter") restaurantFilter1: Int?,
                       @Query("restaurantFilter") restaurantFilter2: Int?,
                       @Query("restaurantFilter") restaurantFilter3: Int?,
                       @Query("restaurantFilter") restaurantFilter4: Int?,
                       @Query("restaurantFilter") restaurantFilter5: Int?,
                       @Query("restaurantFilter") restaurantFilter6: Int?,
                       @Query("restaurantFilter") restaurantFilter7: Int?,
                       @Query("restaurantFilter") restaurantFilter8: Int?
    ) : Call<RestaurantsResponse>


}