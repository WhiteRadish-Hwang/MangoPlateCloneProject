package com.example.mangoplate_mock_aos_radi.src.main.home.detail

import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.*

interface HomeDetailsFragmentView {

    fun onGetDetailsSuccess(response: DetailsResponse,
                            imgsList: ArrayList<ImgsResultData>, detailedInfoList: ArrayList<DetailedInfoResultData>,
                            menuImgList: ArrayList<MenuImgResultData>, keyWordList: ArrayList<KeyWordResultData>,
                            reviewCountList: ArrayList<ReviewCountResultData>, reviewList: ArrayList<ReviewResultData>,
                            reviewImgList: ArrayList<ReviewImgResultData>, nearRestaurantList: ArrayList<NearRestaurantResultData>,
                            areaResultList: ArrayList<AreaResultData>)


    fun onGetDetailsFailure(message: String)

}