package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGetNewsReviewItem
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountRetrofitInterface
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealDetailsImagesItems
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealDetailsInfoItems
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EatDealDetailsService (val view: EatDealDetailsFragmentView) {


    fun tryGetEatDealDetails(eatDealId: Int){

        val discountRetrofitInterface = ApplicationClass.sRetrofit.create(DiscountRetrofitInterface::class.java)

        discountRetrofitInterface.getEatDealDetails(eatDealId).enqueue(object : Callback<EatDealDetailsResponse> {
            override fun onResponse(call: Call<EatDealDetailsResponse>, response: Response<EatDealDetailsResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            val imagesList = ArrayList<EatDealDetailsImagesItems>()
                            val infoList = ArrayList<EatDealDetailsInfoItems>()

                            Log.d(TAG, "onResponse: ${response.body()}")

                            val result = it.result.asJsonObject

                            val images = result.get("images").asJsonArray
                            images.forEach {imgItem ->
                                val imgObject = imgItem.asJsonObject
                                val imgId = imgObject.get("imgId").asInt
                                val eatDealImgUrl = imgObject.get("eatDealImgUrl").asString

                                val imgListItem = EatDealDetailsImagesItems(imgId = imgId, eatDealImgUrl = eatDealImgUrl)

                                imagesList.add(imgListItem)
                            }

                            val info = result.get("info").asJsonArray
                            info.forEach { infoItem ->
                                val infoObject = infoItem.asJsonObject

                                val message = infoObject.get("message").asString
                                val eatDealName = infoObject.get("eatDealName").asString
                                val restaurantId = infoObject.get("restaurantId").asInt
                                val eatDealOneLine = infoObject.get("eatDealOneLine").asString
                                val eatDealTerm = infoObject.get("eatDealTerm").asString
                                val eatDealBeforePrice = infoObject.get("eatDealBeforePrice").asInt
                                val eatDealAfterPrice = infoObject.get("eatDealAfterPrice").asInt
                                val eatDealDiscount = infoObject.get("eatDealDiscount").asInt
                                val eatDealPickUpPossible = infoObject.get("eatDealPickUpPossible").asInt
                                val restaurantInfo = infoObject.get("restaurantInfo").asString
                                val menuInfo = infoObject.get("menuInfo").asString
                                val noticeInfo = infoObject.get("noticeInfo").asString
                                val howToUseInfo = infoObject.get("howToUseInfo").asString
                                val refundPolicyInfo = infoObject.get("refundPolicyInfo").asString

                                val infoListItem = EatDealDetailsInfoItems(message = message, eatDealName = eatDealName, restaurantId = restaurantId, eatDealOneLine = eatDealOneLine,
                                        eatDealTerm = eatDealTerm, eatDealBeforePrice = eatDealBeforePrice, eatDealAfterPrice = eatDealAfterPrice, eatDealDiscount = eatDealDiscount,
                                        eatDealPickUpPossible = eatDealPickUpPossible, restaurantInfo = restaurantInfo, menuInfo = menuInfo, noticeInfo = noticeInfo,
                                        howToUseInfo = howToUseInfo, refundPolicyInfo = refundPolicyInfo)

                                infoList.add(infoListItem)
                            }

                            view.onGetEatDealDetailsSuccess(response.body()!!, imagesList, infoList)
                        }

                    }
                }
                isGetNewsReviewItem = true

            }

            override fun onFailure(call: Call<EatDealDetailsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetEatDealDetailsFailure(t.message ?: "통신 오류")
            }

            })
    }


}