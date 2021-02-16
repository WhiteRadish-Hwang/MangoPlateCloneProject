package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal

import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse

interface EatDealLocFragmentView {

    fun onGetLocEatDealSuccess(response: EatDealResponse, eatDealList: ArrayList<EatDealResultData>)

    fun onGetLocEatDealFailure(message: String)

}