package com.example.mangoplate_mock_aos_radi.src.main.home.model

import java.io.Serializable

data class HomeRecyclerItems(var image: String, var title: String, var areaName: String,
                             var star: String, var viewPoint: Int, var reviewCount: Int,
                             var idx: Int, var restaurantId: Int, var distanceFromUser: Float,
                             var isLike: Int, var isVisited: Int): Serializable