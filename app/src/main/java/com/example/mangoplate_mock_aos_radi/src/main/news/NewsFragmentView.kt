package com.example.mangoplate_mock_aos_radi.src.main.news

import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData

interface NewsFragmentView {

    fun onGetTotalReviewSuccess(response: NewsResponse, reviewList: ArrayList<TotalReviewResultData>)

    fun onGetTotalReviewFailure(message: String)

}