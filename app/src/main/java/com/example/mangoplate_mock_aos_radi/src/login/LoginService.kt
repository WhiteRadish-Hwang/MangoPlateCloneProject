package com.example.mangoplate_mock_aos_radi.src.login

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.login.model.FacebookLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.KakaoLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.PostFacebookLoginRequest
import com.example.mangoplate_mock_aos_radi.src.login.model.PostKakaoLoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService(val view: LoginActivityView) {

    fun tryPostKakaoLogin(postKakaoLoginRequest: PostKakaoLoginRequest){
        Log.d(TAG, "tryPostKakaoLogin: called")
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        homeRetrofitInterface.postKakaoLogin(postKakaoLoginRequest).enqueue(object :
            Callback<KakaoLoginResponse> {
            override fun onResponse(call: Call<KakaoLoginResponse>, response: Response<KakaoLoginResponse>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                view.onPostKakaoLoginSuccess(response.body() as KakaoLoginResponse)
            }

            override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onPostKakaoLoginFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostFacebookLogin(postFacebookRequest: PostFacebookLoginRequest){
        Log.d(TAG, "tryPostKakaoLogin: called")
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(LoginRetrofitInterface::class.java)
        homeRetrofitInterface.postFacebookLogin(postFacebookRequest).enqueue(object :
            Callback<FacebookLoginResponse> {
            override fun onResponse(call: Call<FacebookLoginResponse>, response: Response<FacebookLoginResponse>) {
                Log.d(TAG, "onResponse: ${response.body()}")
                view.onPostFacebookLoginSuccess(response.body() as FacebookLoginResponse)
            }

            override fun onFailure(call: Call<FacebookLoginResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onPostFacebookLoginFailure(t.message ?: "통신 오류")
            }
        })
    }

}