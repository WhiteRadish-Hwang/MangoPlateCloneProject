package com.example.mangoplate_mock_aos_radi.src.login

import com.example.mangoplate_mock_aos_radi.src.login.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginRetrofitInterface {
    @POST("/kakao-login")
    fun postKakaoLogin(@Body params: PostKakaoLoginRequest): Call<KakaoLoginResponse>

    @POST("/facebook-login")
    fun postFacebookLogin(@Body params: PostFacebookLoginRequest): Call<FacebookLoginResponse>
}