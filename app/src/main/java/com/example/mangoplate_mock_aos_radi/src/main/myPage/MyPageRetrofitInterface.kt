package com.example.mangoplate_mock_aos_radi.src.main.myPage

import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.*
import retrofit2.Call
import retrofit2.http.*

interface MyPageRetrofitInterface {

    @GET("/users/{userId}")
    fun getUserInfos(@Path ("userId") userId: Int) : Call<MyInfoResponse>

    @PATCH("/users/{userId}")
    fun postEditUserName (@Path ("userId") userId: Int,
                          @Query ("userinfofilter") userinfofilter: Int,
                          @Body params: PostEditUserNameRequest): Call<EditUserInfoResponse>

    @PATCH("/users/{userId}")
    fun postEditUserProfileImg (@Path ("userId") userId: Int,
                                @Query ("userinfofilter") userinfofilter: Int,
                                @Body params: PostEditUserProfileImgRequest): Call<EditUserInfoResponse>

    @PATCH("/users/{userId}")
    fun postEditUserEmail (@Path ("userId") userId: Int,
                           @Query ("userinfofilter") userinfofilter: Int,
                           @Body params: PostEditUserEmailRequest): Call<EditUserInfoResponse>

    @PATCH("/users/{userId}")
    fun postEditUserPhoneNumber (@Path ("userId") userId: Int,
                                 @Query ("userinfofilter") userinfofilter: Int,
                                 @Body params: PostEditUserPhoneNumberRequest): Call<EditUserInfoResponse>
}