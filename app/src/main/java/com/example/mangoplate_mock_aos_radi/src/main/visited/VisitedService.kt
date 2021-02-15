package com.example.mangoplate_mock_aos_radi.src.main.visited

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.GetVisitedResponse
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.GetVisitedResultData
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.PostVisitedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class VisitedService (val view: VisitedFragmentView) {


    fun tryGetVisited(restaurantId: Int){

        val visitedRetrofitInterface = ApplicationClass.sRetrofit.create(VisitedRetrofitInterface::class.java)
        visitedRetrofitInterface.getVisited(restaurantId).enqueue(object : Callback<GetVisitedResponse> {
            override fun onResponse(call: Call<GetVisitedResponse>, response: Response<GetVisitedResponse>) {
                Log.d(TAG, "onResponse: ${response.code()}")
                when (response.code()) {
                    4002 -> {

                    }
                    200 -> {
                        response.body()?.let { responseResult ->
                            val resultArray = ArrayList<GetVisitedResultData>()

                            when (responseResult.code) {
                                4002 -> {
                                    view.onGetVisitedSuccess(response.body()!!, resultArray)
                                }

                                1000 -> {
                                    Log.d(TAG, "onResponse: ${response.body()}")
                                    val area = responseResult.result.getAsJsonArray("area")
                                    val visitedInfo = responseResult.result.getAsJsonArray("visitedInfo")

                                    var areaName = ""
                                    area.forEach {areaItem ->
                                        val area = areaItem.asJsonObject
                                        val areaNameInArray = area.get("areaName").asString
                                        areaName = areaNameInArray
                                    }

                                    visitedInfo.forEach { resultItem ->
                                        val resultItemObject = resultItem.asJsonObject


                                        val restaurantId = resultItemObject.get("restaurantId").asInt
                                        val message = resultItemObject.get("message").asString
                                        val restaurantName = resultItemObject.get("restaurantName").asString
                                        val firstImageUrl = resultItemObject.get("firstImageUrl").asString
                                        val restaurantFilter = resultItemObject.get("restaurantFilter").asString
                                        val restaurantView = resultItemObject.get("restaurantView").asInt
                                        val reviewCount = resultItemObject.get("reviewCount").asInt

                                        val visitedListItem = GetVisitedResultData(area = areaName, restaurantId = restaurantId, message = message,
                                                restaurantName = restaurantName, firstImageUrl = firstImageUrl, restaurantFilter = restaurantFilter,
                                                restaurantView = restaurantView, reviewCount = reviewCount)

                                        resultArray.add(visitedListItem)
                                    }

                                    view.onGetVisitedSuccess(response.body()!!, resultArray)
                                }
                            }

                        }

                    }
                }

            }

            override fun onFailure(call: Call<GetVisitedResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetVisitedFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostVisitedApply (restaurantId: Int, status: Int) {

        val visitedApplyRetrofitInterface = ApplicationClass.sRetrofit.create(VisitedRetrofitInterface::class.java)
        visitedApplyRetrofitInterface.posetVisitedApply(restaurantId, status).enqueue(object : Callback<PostVisitedResponse> {
            override fun onResponse(call: Call<PostVisitedResponse>, response: Response<PostVisitedResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(TAG, "onResponse: ${response.body()}")
                            view.onPostVisitedApplySuccess(response.body()!!)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostVisitedResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetVisitedFailure(t.message ?: "통신 오류")
            }
        })

    }





}