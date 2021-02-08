package com.example.mangoplate_mock_aos_radi.src.main.myPage

import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResponse
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResultData

interface MypageFragmentView {

    fun onGetMyInfoSuccess(response: MyInfoResponse, infoList: ArrayList<MyInfoResultData>)

    fun onGetMyInfoFailure(message: String)

}