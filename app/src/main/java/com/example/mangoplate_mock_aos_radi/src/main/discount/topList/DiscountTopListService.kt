package com.example.mangoplate_mock_aos_radi.src.main.discount.topList

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountRetrofitInterface
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResultData
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscountTopListService (val view: DiscountTopListFragmentView) {

    fun tryGetDiscountTopList(page: Int,
                              limit: Int){
        val discountRetrofitInterface = ApplicationClass.sRetrofit.create(DiscountRetrofitInterface::class.java)
        discountRetrofitInterface.getTopList(page, limit).enqueue(object : Callback<DiscountTopListResponse> {
            override fun onResponse(call: Call<DiscountTopListResponse>, response: Response<DiscountTopListResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(TAG, "onResponse: ${response.body()}")
                            val topListArray = ArrayList<DiscountTopListResultData>()

                            val topListObjectList = it.result.asJsonArray

                            topListObjectList.forEach { listItem ->
                                val topListItemObject = listItem.asJsonObject

                                val topListId = topListItemObject.get("topListId").asInt
                                val topListImgUrl = topListItemObject.get("topListImgUrl").asString
                                val topListView = topListItemObject.get("topListView").asInt
                                val updatedAt = topListItemObject.get("updatedAt").asString
                                val userBookMark = topListItemObject.get("userBookMark").asInt
                                val topListName = topListItemObject.get("topListName").asString
                                val topListOneLine = topListItemObject.get("topListOneLine").asString


                                val discountTopListItem = DiscountTopListResultData(topListId = topListId, topListImgUrl = topListImgUrl, topListView = topListView,
                                        updatedAt = updatedAt, userBookMark = userBookMark, topListName = topListName,
                                        topListOneLine = topListOneLine, topListSortOnTop = 0)

                                topListArray.add(discountTopListItem)
                            }


                            view.onGetTopListSuccess(response.body()!!, topListArray)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<DiscountTopListResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetTopListFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchDiscountTopList(topListId: Int){
        val discountRetrofitInterface = ApplicationClass.sRetrofit.create(DiscountRetrofitInterface::class.java)
        discountRetrofitInterface.patchTopList(topListId).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(TAG, "onResponse: ${response.body()}")

                            view.onPatchTopListSuccess(response.body()!!)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onPatchTopListFailure(t.message ?: "통신 오류")
            }
        })
    }




}