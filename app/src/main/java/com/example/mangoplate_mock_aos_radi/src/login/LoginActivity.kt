package com.example.mangoplate_mock_aos_radi.src.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.profileImageUrl
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_id
import com.example.mangoplate_mock_aos_radi.config.BaseActivity
import com.example.mangoplate_mock_aos_radi.databinding.ActivityLoginBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.user.UserApiClient

class LoginActivity :BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.loginBtnKakao.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(applicationContext)){
                LoginClient.instance.loginWithKakaoTalk(applicationContext) { token, error ->
                    Log.i(TAG, "loginWithKakaoTalk $token $error")
                    updateKakaoLogin()
                }
            } else {
                LoginClient.instance.loginWithKakaoAccount(applicationContext) { token, error ->
                    Log.i(TAG, "loginWithKakaoAccount $token $error")
                    updateKakaoLogin()
                }
            }
        }


    }
    private fun updateKakaoLogin() {
        UserApiClient.instance.me { user, error ->
            user?.let { Log.d(TAG, "updateKakaoLoginUi: id = ${user.id}")
                Log.d(TAG, "updateKakaoLoginUi: id = ${user.kakaoAccount?.profile?.nickname}")
                user_id = user.kakaoAccount?.profile?.nickname.toString()
                profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            error?.let {

            }
        }

    }


}