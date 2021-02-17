package com.example.mangoplate_mock_aos_radi.src.main.news

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGetNewsReviewItem
import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.detail.DetailsRetrofitInterface
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.ReviewRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query

class NewsStatusService (val view: NewsStatusFragmentView) {

    fun tryPatchReviewWannago(restaurantId: Int) {
        val newsRetrofitInterface = ApplicationClass.sRetrofit.create(NewsRetrofitInterface::class.java)
        newsRetrofitInterface.patchReviewWannago(restaurantId).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(TAG, "onResponse: ${response.body()}")

                            view.onPatchTotalReviewWannaGoSuccess(response = response.body()!!)

                        }
                    }

                }
            }


            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onPatchTotalReviewWannaGoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchReviewLike(reviewId: Int) {
        val newsRetrofitInterface = ApplicationClass.sRetrofit.create(NewsRetrofitInterface::class.java)
        newsRetrofitInterface.patchReviewLike(reviewId).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(TAG, "onResponse: ${response.body()}")

                            view.onPatchTotalReviewLikeSuccess(response = response.body()!!)

                        }
                    }

                }
            }


            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onPatchTotalReviewLikeFailure(t.message ?: "통신 오류")
            }
        })
    }





}