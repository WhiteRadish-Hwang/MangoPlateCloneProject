package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal

import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResultData

interface EatDealFragmentView {

    fun onGetEatDealSuccess(response: EatDealResponse, eatDealList: ArrayList<EatDealResultData>)

    fun onGetEatDealFailure(message: String)

}