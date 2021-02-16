package com.example.mangoplate_mock_aos_radi.src.main.discount.topList

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResultData

interface DiscountTopListFragmentView {

    fun onGetTopListSuccess(response: DiscountTopListResponse, topListList: ArrayList<DiscountTopListResultData>)

    fun onGetTopListFailure(message: String)

    fun onPatchTopListSuccess(response: BaseResponse)

    fun onPatchTopListFailure(message: String)
}