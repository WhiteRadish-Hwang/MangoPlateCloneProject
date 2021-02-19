package com.example.mangoplate_mock_aos_radi.src.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.FB_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_ID
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_IMG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.deviceToken
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isFacebookLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isKakaoLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.profileImageUrl
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sSharedPreferences
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_id
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_name
import com.example.mangoplate_mock_aos_radi.config.BaseActivity
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.ActivityLoginBinding
import com.example.mangoplate_mock_aos_radi.src.login.model.*
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.AccessToken.getCurrentAccessToken
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.lang.reflect.InvocationTargetException
import java.net.URL


class LoginActivity :BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {
    val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            deviceToken = task.result.toString()

//            Log.d(TAG, "token: $deviceToken")

            // Log and toast
            val msg = deviceToken
//            Log.d(TAG, "msg: $msg")
        })

//        FirebaseMessagingService.NOTIFICATION_SERVICE

//        val keyHash = Utility.getKeyHash(this)
        isKakaoLogin = SharedPreferenced.getSettingItem(KAKAO_LOGIN)?.toBoolean() ?: false
        isFacebookLogin = SharedPreferenced.getSettingItem(FB_LOGIN)?.toBoolean() ?: false

//        binding.loginBtnFacebook.setReadPermissions("user_status")
        binding.loginBtnFacebook.setOnClickListener{
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
        }
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val request: GraphRequest = GraphRequest.newMeRequest(getCurrentAccessToken(),
                    object : GraphRequest.GraphJSONArrayCallback,
                        GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(objects: JSONArray?, response: GraphResponse?) {
                            Log.d(TAG, "onCompleted jsonArray: $objects")
                        }

                        override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                            val name = `object`?.getString("name")
                            val id = `object`?.getString("id")
                            val url = URL("https://graph.facebook.com/$id/picture")
                            profileImageUrl = url.toString()
                        }

                    })

                    request.executeAsync()

                    val currentAccessToken = getCurrentAccessToken().token.toString()
                    val postFbRequest = PostFacebookLoginRequest(facebookToken = currentAccessToken, deviceToken = deviceToken)
                    Log.d(TAG, "onCreate: currentAccessToken: $currentAccessToken, postFbRequest: $postFbRequest")
                    LoginService(this@LoginActivity).tryPostFacebookLogin(postFbRequest)

                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })

       //카카오 로그인
        binding.loginBtnKakao.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this) { token, error ->
                    Log.i(TAG, "loginWithKakaoTalk $token $error")
                    val kakaoToken: String = token?.accessToken.toString()
                    val postRequest = PostKakaoLoginRequest(kakaoToken = kakaoToken, deviceToken = deviceToken)
                    showLoadingDialog(this)
                    Log.d(TAG, "onCreate: kakaoToken: $kakaoToken, postRequest: $postRequest")
                    LoginService(this).tryPostKakaoLogin(postRequest)

                }
            } else {
                LoginClient.instance.loginWithKakaoAccount(this) { token, error ->
                    Log.i(TAG, "loginWithKakaoAccount $token $error")
                    val kakaoToken: String = token?.accessToken.toString()
                    val postRequest = PostKakaoLoginRequest(kakaoToken = kakaoToken, deviceToken = deviceToken)
                    showLoadingDialog(this)
                    Log.d(TAG, "onCreate: kakaoToken: $kakaoToken, postRequest: $postRequest")
                    LoginService(this).tryPostKakaoLogin(postRequest)

                }
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateKakaoLogin() {
        UserApiClient.instance.me { user, error ->
            user?.let {
                isKakaoLogin = true
                SharedPreferenced.putSettingItem(KAKAO_LOGIN, isKakaoLogin.toString())

                profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
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
        Log.d(TAG, "onPostKakaoLoginSuccess: jwt = ${response.jwt}")
        X_ACCESS_TOKEN = response.jwt
        user_id = response.userId.toString()

        SharedPreferenced.putSettingItem("X-ACCESS-TOKEN", X_ACCESS_TOKEN)


        updateKakaoLogin()
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostFacebookLoginSuccess(response: FacebookLoginResponse) {
//        dismissLoadingDialog()
        Log.d(TAG, "onPostFacebookLoginSuccess: jwt = ${response.jwt}")
        X_ACCESS_TOKEN = response.jwt
        user_id = response.userId.toString()
        isFacebookLogin = true

        SharedPreferenced.putSettingItem("X-ACCESS-TOKEN", X_ACCESS_TOKEN)
        SharedPreferenced.putSettingItem(FB_LOGIN, isFacebookLogin.toString())

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPostFacebookLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

}