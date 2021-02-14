package com.example.mangoplate_mock_aos_radi.src.main.register

import com.example.mangoplate_mock_aos_radi.src.login.model.*
import com.example.mangoplate_mock_aos_radi.src.main.register.model.PostRegisterRequest
import com.example.mangoplate_mock_aos_radi.src.main.register.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterRetrofitInterface {
    @POST("/restaurants")
    fun postRegister(@Body params: PostRegisterRequest): Call<RegisterResponse>

}