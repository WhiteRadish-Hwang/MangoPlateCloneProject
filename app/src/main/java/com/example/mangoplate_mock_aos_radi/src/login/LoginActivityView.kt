package com.example.mangoplate_mock_aos_radi.src.login

import com.example.mangoplate_mock_aos_radi.src.login.model.FacebookLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.KakaoLoginResponse

interface LoginActivityView {

    fun onPostKakaoLoginSuccess(response: KakaoLoginResponse)

    fun onPostKakaoLoginFailure(message: String)

    fun onPostFacebookLoginSuccess(response: FacebookLoginResponse)

    fun onPostFacebookLoginFailure(message: String)
}