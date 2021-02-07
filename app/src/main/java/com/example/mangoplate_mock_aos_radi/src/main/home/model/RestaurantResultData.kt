package com.example.mangoplate_mock_aos_radi.src.main.home.model

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class RestaurantResultData (val  restaurantId: Int,
                                 val  restaurantName: String,
                                 val  distanceFromUser: Int,
                                 val  areaName: String,
                                 val  restaurantView: Int,
                                 val  reviewCount: Int,
                                 val  star: String,
                                 val  firstImageUrl: String)