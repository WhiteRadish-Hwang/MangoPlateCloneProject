package com.example.mangoplate_mock_aos_radi.src.main.myPage

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_id
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService (val view: MypageFragmentView) {


    fun tryGetMyInfo(userId: Int){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getUserInfos(userId).enqueue(object : Callback<MyInfoResponse> {
            override fun onResponse(call: Call<MyInfoResponse>, response: Response<MyInfoResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {myInfo ->
                            val myInfoArrayList = ArrayList<MyInfoResultData>()

                            Log.d(TAG, "tryGetMyInfo: ${response.headers()}")
                            Log.d(TAG, "tryGetMyInfo: ${myInfo.result}")
                            myInfo.result.forEach { listItem ->
                                val myInfoItemObject = listItem.asJsonObject
                                val userId = myInfoItemObject.get("userId").asInt
                                val userProfileImgUrl = myInfoItemObject.get("userProfileImgUrl").asString
                                val userName = myInfoItemObject.get("userName").asString
                                val userEmail = myInfoItemObject.get("userEmail").asString
                                val userPhoneNumber = myInfoItemObject.get("userPhoneNumber").asString
                                val userFollowerCount = myInfoItemObject.get("userFollowerCount").asInt
                                val userFollowingCount = myInfoItemObject.get("userFollowingCount").asInt
                                val userReviewCount = myInfoItemObject.get("userReviewCount").asInt
                                val userVisitedCount = myInfoItemObject.get("userVisitedCount").asInt
                                val userUploadImgCount = myInfoItemObject.get("userUploadImgCount").asInt
                                val userLikeCount = myInfoItemObject.get("userLikeCount").asInt
                                val userMyListCount = myInfoItemObject.get("userMyListCount").asInt
                                val userBookMarkCount = myInfoItemObject.get("userBookMarkCount").asInt


                                val myInfoItem = MyInfoResultData(userId = userId, userProfileImgUrl = userProfileImgUrl,
                                    userName = userName, userEmail = userEmail, userPhoneNumber = userPhoneNumber,
                                userFollowerCount = userFollowerCount, userFollowingCount = userFollowingCount, userReviewCount = userReviewCount,
                                userVisitedCount = userVisitedCount, userUploadImgCount = userUploadImgCount, userLikeCount = userLikeCount,
                                        userMyListCount = userMyListCount, userBookMarkCount = userBookMarkCount)

                                myInfoArrayList.add(myInfoItem)
                            }

                            view.onGetMyInfoSuccess(response.body()!!, myInfoArrayList)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<MyInfoResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetMyInfoFailure(t.message ?: "?????? ??????")
            }
        })
    }

    fun tryPostUserInfo(userInfoFilter: Int, bodyVlaue: String){
        Log.d(TAG, "tryPostUserInfo: called")
        when (userInfoFilter) {
            1 -> {
                val postUserNameRequest = PostEditUserNameRequest(bodyVlaue)
                val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)

                myPageRetrofitInterface.postEditUserName(userId = user_id!!.toInt(), userinfofilter = userInfoFilter, params = postUserNameRequest).enqueue(object :
                        Callback<EditUserInfoResponse> {
                    override fun onResponse(call: Call<EditUserInfoResponse>, response: Response<EditUserInfoResponse>) {
                        Log.d(TAG, "postEditUserName: ${response.body()}")
                        view.onPostEditUserInfoSuccess(response.body() as EditUserInfoResponse)
                    }

                    override fun onFailure(call: Call<EditUserInfoResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                        view.onPostEditUserInfoFailure(t.message ?: "?????? ??????")
                    }
                })

            }
            2 -> {
                val postUserProfileImgRequest = PostEditUserProfileImgRequest(bodyVlaue)
                val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)

                myPageRetrofitInterface.postEditUserProfileImg(userId = user_id!!.toInt(), userinfofilter = userInfoFilter, params = postUserProfileImgRequest).enqueue(object :
                        Callback<EditUserInfoResponse> {
                    override fun onResponse(call: Call<EditUserInfoResponse>, response: Response<EditUserInfoResponse>) {
                        Log.d(TAG, "postEditUserProfileImg: ${response.body()}")
                        view.onPostEditUserInfoSuccess(response.body() as EditUserInfoResponse)
                    }

                    override fun onFailure(call: Call<EditUserInfoResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                        view.onPostEditUserInfoFailure(t.message ?: "?????? ??????")
                    }
                })

            }
            3 -> {
                val postUserEmailRequest = PostEditUserEmailRequest(bodyVlaue)
                val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)

                myPageRetrofitInterface.postEditUserEmail(userId = user_id!!.toInt(), userinfofilter = userInfoFilter, params = postUserEmailRequest).enqueue(object :
                        Callback<EditUserInfoResponse> {
                    override fun onResponse(call: Call<EditUserInfoResponse>, response: Response<EditUserInfoResponse>) {
                        Log.d(TAG, "postEditUserEmail: ${response.body()}")
                        view.onPostEditUserInfoSuccess(response.body() as EditUserInfoResponse)
                    }

                    override fun onFailure(call: Call<EditUserInfoResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                        view.onPostEditUserInfoFailure(t.message ?: "?????? ??????")
                    }
                })

            }
            4 -> {
                val postUserPhoneNumberRequest = PostEditUserPhoneNumberRequest(bodyVlaue)
                val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)

                myPageRetrofitInterface.postEditUserPhoneNumber(userId = user_id!!.toInt(), userinfofilter = userInfoFilter, params = postUserPhoneNumberRequest).enqueue(object :
                        Callback<EditUserInfoResponse> {
                    override fun onResponse(call: Call<EditUserInfoResponse>, response: Response<EditUserInfoResponse>) {
                        Log.d(TAG, "postEditUserPhoneNumber: ${response.body()}")
                        view.onPostEditUserInfoSuccess(response.body() as EditUserInfoResponse)
                    }

                    override fun onFailure(call: Call<EditUserInfoResponse>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.message}")
                        view.onPostEditUserInfoFailure(t.message ?: "?????? ??????")
                    }
                })

            }
        }





    }

}
