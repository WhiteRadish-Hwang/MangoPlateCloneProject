package com.example.mangoplate_mock_aos_radi.src.main.discount

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EatDealLocService (val view: EatDealLocFragmentView) {

    fun tryGetLocEatDeal(page: Int,
                         limit: Int,
                         locationfilter_sungbuk: Int,
                         locationfilter_suyu: Int,
                         locationfilter_nowon: Int){
        val discountRetrofitInterface = ApplicationClass.sRetrofit.create(DiscountRetrofitInterface::class.java)
        discountRetrofitInterface.getLocEatDeal(page, limit, locationfilter_sungbuk, locationfilter_suyu, locationfilter_nowon).enqueue(object : Callback<EatDealResponse> {
            override fun onResponse(call: Call<EatDealResponse>, response: Response<EatDealResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(TAG, "onResponse: ${response.body()}")
                            val eatDealArray = ArrayList<EatDealResultData>()

                            val eatDealObjectList = it.result.asJsonArray

                            eatDealObjectList.forEach { listItem ->
                                val eatDealItemObject = listItem.asJsonObject

                                val eatDealId = eatDealItemObject.get("eatDealId").asInt
                                val firstImageUrl = eatDealItemObject.get("firstImageUrl").asString
                                val eatDealDiscount = eatDealItemObject.get("eatDealDiscount").asInt
                                val eatDealBeforePrice = eatDealItemObject.get("eatDealBeforePrice").asInt
                                val eatDealAfterPrice = eatDealItemObject.get("eatDealAfterPrice").asInt
                                val eatDealName = eatDealItemObject.get("eatDealName").asString
                                val eatDealOneLine = eatDealItemObject.get("eatDealOneLine").asString
                                val eatDealPickUpPossible = eatDealItemObject.get("eatDealPickUpPossible").asInt


                                val eatDealListItem = EatDealResultData(eatDealId = eatDealId, firstImageUrl = firstImageUrl, eatDealDiscount = eatDealDiscount,
                                        eatDealBeforePrice = eatDealBeforePrice, eatDealAfterPrice = eatDealAfterPrice, eatDealName = eatDealName,
                                        eatDealOneLine = eatDealOneLine, eatDealPickUpPossible = eatDealPickUpPossible)

                                eatDealArray.add(eatDealListItem)
                            }


                            view.onGetLocEatDealSuccess(response.body()!!, eatDealArray)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<EatDealResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetLocEatDealFailure(t.message ?: "통신 오류")
            }
        })
    }






}