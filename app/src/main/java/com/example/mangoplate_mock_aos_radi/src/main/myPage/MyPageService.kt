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


    fun tryGetMyInfo(){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getUserInfos().enqueue(object : Callback<MyInfoResponse> {
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

                                val myInfoItem = MyInfoResultData(userId = userId, userProfileImgUrl = userProfileImgUrl,
                                    userName = userName, userEmail = userEmail, userPhoneNumber = userPhoneNumber)

                                myInfoArrayList.add(myInfoItem)
                            }

                            view.onGetMyInfoSuccess(response.body()!!, myInfoArrayList)
                        }

                    }
                }

            }

            override fun onFailure(call: Call<MyInfoResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetMyInfoFailure(t.message ?: "통신 오류")
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
                        view.onPostEditUserInfoFailure(t.message ?: "통신 오류")
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
                        view.onPostEditUserInfoFailure(t.message ?: "통신 오류")
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
                        view.onPostEditUserInfoFailure(t.message ?: "통신 오류")
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
                        view.onPostEditUserInfoFailure(t.message ?: "통신 오류")
                    }
                })

            }
        }





    }

}
