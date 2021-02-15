package com.example.mangoplate_mock_aos_radi.src.main.detail

import com.example.mangoplate_mock_aos_radi.src.main.detail.model.*
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse

interface HomeDetailsFragmentView {

    fun onGetDetailsSuccess(response: DetailsResponse,
                            imgsList: ArrayList<ImgsResultData>, detailedInfoList: ArrayList<DetailedInfoResultData>,
                            menuImgList: ArrayList<MenuImgResultData>, keyWordList: ArrayList<KeyWordResultData>,
                            reviewCountList: ArrayList<ReviewCountResultData>, reviewList: ArrayList<ReviewResultData>,
                            nearRestaurantList: ArrayList<NearRestaurantResultData>)


    fun onGetDetailsFailure(message: String)

    fun onPatchWannaGoSuccess(response: PatchWannagoResponse)

    fun onPatchWannaGoFailure(message: String)

    fun onGetDetailsImageSuccess(response: DetailsImageResponse)


    fun onGetDetailsImageFailure(message: String)

}