package com.example.mangoplate_mock_aos_radi.src.main.home.model

data class RestaurantResultData (val  restaurantId: Int,
                                 val  restaurantName: String,
                                 val  distanceFromUser: Int,
                                 val  areaName: String,
                                 val  restaurantView: Int,
                                 val  reviewCount: Int,
                                 val  star: String,
                                 val  firstImageUrl: String)