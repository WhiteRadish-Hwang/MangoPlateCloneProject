package com.example.mangoplate_mock_aos_radi.src.main.home

import com.example.mangoplate_mock_aos_radi.src.main.home.adapter.HomeRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData

interface HomeFragmentView {

    fun onGetRestaurantSuccess(response: RestaurantsResponse, topList: ArrayList<TopListResultData>, restaurantList: ArrayList<RestaurantResultData>)

    fun onGetRestaurantFailure(message: String)

    fun onPatchWannaGoSuccess(response: PatchWannagoResponse)

    fun onPatchWannaGoFailure(message: String)

}