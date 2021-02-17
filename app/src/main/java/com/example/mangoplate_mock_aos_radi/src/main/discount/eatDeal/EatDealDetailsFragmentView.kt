package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal

import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealDetailsImagesItems
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealDetailsInfoItems
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealDetailsResponse

interface EatDealDetailsFragmentView {

    fun onGetEatDealDetailsSuccess(response: EatDealDetailsResponse, imagesList: ArrayList<EatDealDetailsImagesItems>, infoList: ArrayList<EatDealDetailsInfoItems>)

    fun onGetEatDealDetailsFailure(message: String)

}