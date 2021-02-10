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
                       @Query("userLongitude") userLongitude: Float
//                       @Query("restaurantPriceFilter") restaurantPriceFilter1: Int? = 0,
//                       @Query("restaurantPriceFilter") restaurantPriceFilter2: Int? = 0,
//                       @Query("restaurantPriceFilter") restaurantPriceFilter3: Int? = 0,
//                       @Query("restaurantPriceFilter") restaurantPriceFilter4: Int? = 0,
//                       @Query("restaurantFilter") restaurantFilter1: Int? = 0,
//                       @Query("restaurantFilter") restaurantFilter2: Int? = 0,
//                       @Query("restaurantFilter") restaurantFilter3: Int? = 0,
//                       @Query("restaurantFilter") restaurantFilter4: Int? = 0,
//                       @Query("restaurantFilter") restaurantFilter5: Int? = 0,
//                       @Query("restaurantFilter") restaurantFilter6: Int? = 0,
//                       @Query("restaurantFilter") restaurantFilter7: Int? = 0,
//                       @Query("restaurantFilter") restaurantFilter8: Int? = 0
    ) : Call<RestaurantsResponse>

//    @POST("/users")
//    fun postSignUp(@Body params: PostSignUpRequest): Call<SignUpResponse>

}