package com.example.mangoplate_mock_aos_radi.src.main.discount

import com.example.mangoplate_mock_aos_radi.src.main.detail.model.*
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse

interface EatDealLocFragmentView {

    fun onGetLocEatDealSuccess(response: EatDealResponse, eatDealList: ArrayList<EatDealResultData>)

    fun onGetLocEatDealFailure(message: String)

}