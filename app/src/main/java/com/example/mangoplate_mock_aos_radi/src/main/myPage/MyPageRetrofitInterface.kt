package com.example.mangoplate_mock_aos_radi.src.main.myPage

import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyPageRetrofitInterface {

    @GET("/users/profile")
    fun getUserInfos() : Call<MyInfoResponse>

}