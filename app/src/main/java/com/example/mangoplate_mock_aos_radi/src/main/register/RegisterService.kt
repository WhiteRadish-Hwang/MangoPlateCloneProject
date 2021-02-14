package com.example.mangoplate_mock_aos_radi.src.main.register

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import com.example.mangoplate_mock_aos_radi.src.main.register.model.PostRegisterRequest
import com.example.mangoplate_mock_aos_radi.src.main.register.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterService (val view: RegisterFragmentView) {

    fun tryPostRegister(params: PostRegisterRequest){
        val registerRetrofitInterface = ApplicationClass.sRetrofit.create(RegisterRetrofitInterface::class.java)

        registerRetrofitInterface.postRegister(params = params).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {

                            Log.d(TAG, "onResponse: ${response.body()}")


                            view.onPostRegisterInfoSuccess(response.body()!!)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onPostRegisterInfoFailure(t.message ?: "통신 오류")
            }
        })
    }
}