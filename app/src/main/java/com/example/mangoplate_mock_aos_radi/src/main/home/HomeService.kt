package com.example.mangoplate_mock_aos_radi.src.main.home

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService (val view: HomeFragmentView) {


    fun tryGetRestaurants(page: Int,
                          limit: Int,
                          areaName: String,
                          distance: Int,
                          sort: Int,
                          userLatitude: Float,
                          userLongitude:Float,
                          restaurantPriceFilter1: Int? = 1,
                          restaurantPriceFilter2: Int? = 2,
                          restaurantPriceFilter3: Int? = 3,
                          restaurantPriceFilter4: Int? = 4,
                          restaurantFilter1: Int? = 1,
                          restaurantFilter2: Int? = 2,
                          restaurantFilter3: Int? = 3,
                          restaurantFilter4: Int? = 4,
                          restaurantFilter5: Int? = 5,
                          restaurantFilter6: Int? = 6,
                          restaurantFilter7: Int? = 7,
                          restaurantFilter8: Int? = 8
    ){

        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getRestaurants(page, limit, areaName, distance, sort, userLatitude, userLongitude,
            restaurantPriceFilter1, restaurantPriceFilter2, restaurantPriceFilter3, restaurantPriceFilter4,
            restaurantFilter1, restaurantFilter2, restaurantFilter3, restaurantFilter4, restaurantFilter5,
            restaurantFilter6, restaurantFilter7, restaurantFilter8).enqueue(object : Callback<RestaurantsResponse> {
            override fun onResponse(call: Call<RestaurantsResponse>, response: Response<RestaurantsResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val restaurantArray = ArrayList<RestaurantResultData>()
                            val topListArray = ArrayList<TopListResultData>()

                            Log.d(TAG, "onResponse: ${response.body()}")
                            val topList = it.result.getAsJsonArray("topList")
                            val restaurant = it.result.getAsJsonArray("restaurant")
                            Log.d(TAG, "topList: $topList")
                            Log.d(TAG, "restaurant: $restaurant")

                            topList.forEach { listItem ->
                                val topListItemObject = listItem.asJsonObject
                                val topListId = topListItemObject.get("topListId").asInt
                                val topListImgUrl = topListItemObject.get("topListImgUrl").asString
                                val topListName = topListItemObject.get("topListName").asString

                                val topListItem = TopListResultData(topListId = topListId, topListImgUrl = topListImgUrl, topListName = topListName)

                                topListArray.add(topListItem)
                            }

                            restaurant.forEach {listItem ->
                                val restaurantItemObject = listItem.asJsonObject
                                val  restaurantId = restaurantItemObject.get("restaurantId").asInt
                                val  restaurantName = restaurantItemObject.get("restaurantName").asString
                                val  distanceFromUser = restaurantItemObject.get("distanceFromUser").asInt
                                val  areaName = restaurantItemObject.get("areaName").asString
                                val  restaurantView = restaurantItemObject.get("restaurantView").asInt
                                val  reviewCount = restaurantItemObject.get("reviewCount").asInt
                                val  isLike = restaurantItemObject.get("isLike").asInt
                                val  visited = restaurantItemObject.get("visited").asInt
                                val  star = restaurantItemObject.get("star").asString
                                val  firstImageUrl = restaurantItemObject.get("firstImageUrl").asString

                                val restaurantListItem = RestaurantResultData(
                                        restaurantId = restaurantId,
                                        restaurantName = restaurantName,
                                        distanceFromUser = distanceFromUser,
                                        areaName = areaName,
                                        restaurantView = restaurantView,
                                        reviewCount = reviewCount,
                                        isLike = isLike,
                                        visited = visited,
                                        star = star,
                                        firstImageUrl = firstImageUrl)

                                restaurantArray.add(restaurantListItem)
                            }

                            view.onGetRestaurantSuccess(response.body()!!, topListArray, restaurantArray)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<RestaurantsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetRestaurantFailure(t.message ?: "통신 오류")
            }
        })
    }
}