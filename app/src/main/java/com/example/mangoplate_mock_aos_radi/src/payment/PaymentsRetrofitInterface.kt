package com.example.mangoplate_mock_aos_radi.src.payment

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.payment.model.PaymentsCompleteRequest
import retrofit2.Call
import retrofit2.http.*

interface PaymentsRetrofitInterface {

    @POST("/payments/complete")
    fun postPaymentsComplete(@Body params: PaymentsCompleteRequest) : Call<BaseResponse>

}