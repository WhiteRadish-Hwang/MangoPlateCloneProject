package com.example.mangoplate_mock_aos_radi.src.main.visited.model

import java.io.Serializable

data class GetVisitedResultData( val area: String,
                                 val restaurantId: Int,
                                 val message: String,
                                 val restaurantName: String,
                                 val firstImageUrl: String,
                                 val restaurantFilter: String,
                                 val restaurantView: Int,
                                 val reviewCount: Int
): Serializable