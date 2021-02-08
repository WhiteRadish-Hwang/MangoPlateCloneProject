package com.example.mangoplate_mock_aos_radi.src.main.myPage

import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageRetrofitInterface {

    @GET("/users/:{user_id}")
    fun getUserInfos(@Path("user_id") user_id: String) : Call<MyInfoResponse>

}