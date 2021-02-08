package com.example.mangoplate_mock_aos_radi.src.login

import com.example.mangoplate_mock_aos_radi.src.login.model.FacebookLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.KakaoLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.PostFacebookLoginRequest
import com.example.mangoplate_mock_aos_radi.src.login.model.PostKakaoLoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitInterface {
    @POST("/kakao-login")
    fun postKakaoLogin(@Body params: PostKakaoLoginRequest): Call<KakaoLoginResponse>

    @POST("/facebook-login")
    fun postFacebookLogin(@Body params: PostFacebookLoginRequest): Call<FacebookLoginResponse>
}