package com.example.mangoplate_mock_aos_radi.src.payment

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountRetrofitInterface
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model.EatDealResultData
import com.example.mangoplate_mock_aos_radi.src.payment.model.PaymentsCompleteRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentsService (val view: PaymentsView) {

    fun tryPostPaymentsComplete(params: PaymentsCompleteRequest){
        val paymentsRetrofitInterface = ApplicationClass.sRetrofit.create(PaymentsRetrofitInterface::class.java)
        paymentsRetrofitInterface.postPaymentsComplete(params = params).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(TAG, "onResponse: ${response.body()}")

                            view.onPostPaymentsCompleteSuccess(response.body()!!)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onPostPaymentsCompleteFailure(t.message ?: "통신 오류")
            }
        })
    }






}