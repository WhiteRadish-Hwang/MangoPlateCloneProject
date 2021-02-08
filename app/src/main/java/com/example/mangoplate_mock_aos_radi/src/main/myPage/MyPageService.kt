package com.example.mangoplate_mock_aos_radi.src.main.myPage

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResponse
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResultData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService (val view: MypageFragmentView) {


    fun tryGetMyInfo(userId: String){

        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getUserInfos(userId).enqueue(object : Callback<MyInfoResponse> {
            override fun onResponse(call: Call<MyInfoResponse>, response: Response<MyInfoResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {myInfo ->
                            val myInfoArrayList = ArrayList<MyInfoResultData>()

                            Log.d(TAG, "onResponse: ${response.body()}")
                            Log.d(TAG, "tryGetMyInfo: ${myInfo.result}")
                            myInfo.result.forEach { listItem ->
                                val myInfoItemObject = listItem.asJsonObject
                                val userId = myInfoItemObject.get("userId").asInt
                                val userProfileImgUrl = myInfoItemObject.get("userProfileImgUrl").asString
                                val userName = myInfoItemObject.get("userName").asString

                                val myInfoItem = MyInfoResultData(userId = userId, userProfileImgUrl = userProfileImgUrl, userName = userName)

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
}
