package com.example.mangoplate_mock_aos_radi.src.main.register.model

import com.google.gson.annotations.SerializedName

data class PostRegisterRequest(
        @SerializedName("restaurantName") val restaurantName: String,
        @SerializedName("restaurantLatitude") val restaurantLatitude: String,
        @SerializedName("restaurantLongitude") val restaurantLongitude: String,
        @SerializedName("restaurantPhoneNumber") val restaurantPhoneNumber: String,
        @SerializedName("restaurantFilter") val restaurantFilter: Int
)