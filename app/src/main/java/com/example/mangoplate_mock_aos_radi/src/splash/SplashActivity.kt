package com.example.mangoplate_mock_aos_radi.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isFacebookLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isKakaoLogin
import com.example.mangoplate_mock_aos_radi.config.BaseActivity
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.ActivitySplashBinding
import com.example.mangoplate_mock_aos_radi.src.login.LoginActivity
import com.example.mangoplate_mock_aos_radi.src.login.LoginActivityView
import com.example.mangoplate_mock_aos_radi.src.login.LoginService
import com.example.mangoplate_mock_aos_radi.src.login.model.FacebookLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.KakaoLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.PostKakaoLoginRequest
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.user.UserApiClient

class SplashActivity:BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), LoginActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isKakaoLogin = SharedPreferenced.getSettingItem(ApplicationClass.KAKAO_LOGIN)?.toBoolean() ?: false
        isFacebookLogin = SharedPreferenced.getSettingItem(ApplicationClass.FB_LOGIN)?.toBoolean() ?: false

        Handler(Looper.getMainLooper()).postDelayed({
            if (isKakaoLogin) {
                loginKakao()
            }else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1000)
    }

    private fun loginKakao() {
        if (LoginClient.instance.isKakaoTalkLoginAvailable(this)){
            LoginClient.instance.loginWithKakaoTalk(this) { token, error ->
                Log.i(ApplicationClass.TAG, "loginWithKakaoTalk $token $error")
                val kakaoToken: String = token?.accessToken.toString()
                val postRequest = PostKakaoLoginRequest(kakaoToken = kakaoToken)
                showLoadingDialog(this)
                Log.d(ApplicationClass.TAG, "onCreate: kakaoToken: $kakaoToken, postRequest: $postRequest")
                LoginService(this).tryPostKakaoLogin(postRequest)

            }
        } else {
            LoginClient.instance.loginWithKakaoAccount(this) { token, error ->
                Log.i(ApplicationClass.TAG, "loginWithKakaoAccount $token $error")
                val kakaoToken: String = token?.accessToken.toString()
                val postRequest = PostKakaoLoginRequest(kakaoToken = kakaoToken)
                showLoadingDialog(this)
                Log.d(ApplicationClass.TAG, "onCreate: kakaoToken: $kakaoToken, postRequest: $postRequest")
                LoginService(this).tryPostKakaoLogin(postRequest)

            }
        }
    }

    private fun updateKakaoLogin() {
        UserApiClient.instance.me { user, error ->
            user?.let {
                Log.d(ApplicationClass.TAG, "updateKakaoLoginUi: id = ${user.id}")
                Log.d(ApplicationClass.TAG, "updateKakaoLoginUi: name = ${user.kakaoAccount?.profile?.nickname}")
                Log.d(ApplicationClass.TAG, "updateKakaoLoginUi: thumbnailImageUrl = ${user.kakaoAccount?.profile?.thumbnailImageUrl.toString()}")
                isKakaoLogin = true

                ApplicationClass.profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            error?.let {

            }
        }

    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        dismissLoadingDialog()
        Log.d(ApplicationClass.TAG, "onPostKakaoLoginSuccess: jwt = ${response.jwt}")
        Log.d(ApplicationClass.TAG, "onPostFacebookLoginSuccess: userId = ${response.userId}")
        Log.d(ApplicationClass.TAG, "onPostKakaoLoginSuccess: isSuccess = ${response.isSuccess}")
        Log.d(ApplicationClass.TAG, "onPostKakaoLoginSuccess: code = ${response.code}")
        Log.d(ApplicationClass.TAG, "onPostKakaoLoginSuccess: message = ${response.message}")
        ApplicationClass.X_ACCESS_TOKEN = response.jwt
        ApplicationClass.user_id = response.userId.toString()

        SharedPreferenced.putSettingItem("X-ACCESS-TOKEN", ApplicationClass.X_ACCESS_TOKEN)


        updateKakaoLogin()
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostFacebookLoginSuccess(response: FacebookLoginResponse) {

    }

    override fun onPostFacebookLoginFailure(message: String) {

    }
}