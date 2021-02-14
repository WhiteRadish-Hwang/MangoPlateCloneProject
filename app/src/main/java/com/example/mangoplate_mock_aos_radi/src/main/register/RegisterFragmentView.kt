package com.example.mangoplate_mock_aos_radi.src.main.register

import com.example.mangoplate_mock_aos_radi.src.login.model.FacebookLoginResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.EditUserInfoResponse
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResponse
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResultData
import com.example.mangoplate_mock_aos_radi.src.main.register.model.RegisterResponse

interface RegisterFragmentView {

    fun onPostRegisterInfoSuccess(response: RegisterResponse)

    fun onPostRegisterInfoFailure(message: String)

}