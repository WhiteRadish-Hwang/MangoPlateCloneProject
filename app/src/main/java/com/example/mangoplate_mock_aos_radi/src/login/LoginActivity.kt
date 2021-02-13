package com.example.mangoplate_mock_aos_radi.src.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.FB_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_ID
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_IMG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.X_ACCESS_TOKEN
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
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.net.URL


class LoginActivity :BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {
    val callbackManager = CallbackManager.Factory.create()
    var isLoginDone: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)

//        val keyHash = Utility.getKeyHash(this)
        isKakaoLogin = SharedPreferenced.getSettingItem(KAKAO_LOGIN)?.toBoolean() ?: false
        isFacebookLogin = SharedPreferenced.getSettingItem(FB_LOGIN)?.toBoolean() ?: false

//        //자동로그인
//        if (isKakaoLogin){
//            user_id = SharedPreferenced.getSettingItem(KAKAO_ID)
//            profileImageUrl = SharedPreferenced.getSettingItem(KAKAO_IMG)
//            startActivity(intent)
//        } else if (isFacebookLogin) {
//            user_id = SharedPreferenced.getSettingItem(FB_ID)
//            startActivity(intent)
//        }

        //페이스북 로그인
        val accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        }
        // If the access token is available already assign it.
        // If the access token is available already assign it.

//        binding.loginBtnFacebook.setReadPermissions("user_status")
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
//                            user_name = name
                            val id = `object`?.getString("id")
                            val url = URL("https://graph.facebook.com/$id/picture")
//                            val profile = Profile.getCurrentProfile()
//                            val img = profile.getProfilePictureUri(200, 200).toString()
//                            profileImageUrl = url.toString()
                        }

                    })

                    request.executeAsync()

                    val currentAccessToken = getCurrentAccessToken().token.toString()
                    val postFbRequest = PostFacebookLoginRequest(facebookToken = currentAccessToken)
                    Log.d(TAG, "onCreate: currentAccessToken: $currentAccessToken, postFbRequest: $postFbRequest")
                    LoginService(this@LoginActivity).tryPostFacebookLogin(postFbRequest)

                    Thread {
                        Thread.sleep(1500)
                        try {
                            Handler(Looper.getMainLooper()).post() {
                                if (isLogin) {
                                    isLogin = false
                                    isFacebookLogin = true
                                    SharedPreferenced.putSettingItem(FB_LOGIN, isFacebookLogin.toString())

                                    isLoginDone = true
                                    Log.d(TAG, "isFacebookLogin: $isFacebookLogin")
                                    startActivity(intent)
                                    finish()
                                }
                                if (isLoginDone) throw Exception()
                            }
                        } catch (e: Exception){
                        }
                    }.start()

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
                    val postRequest = PostKakaoLoginRequest(kakaoToken = kakaoToken)
                    showLoadingDialog(this)
                    Log.d(TAG, "onCreate: kakaoToken: $kakaoToken, postRequest: $postRequest")
                    LoginService(this).tryPostKakaoLogin(postRequest)

                    Thread {
                        Thread.sleep(1500)
                        try {
                            Handler(Looper.getMainLooper()).post() {
                                if (isLogin) {
                                    isLogin = false
                                    updateKakaoLogin()
                                }
                            if (isLoginDone) throw Exception()
                            }
                        } catch (e: Exception){
                        }
                    }.start()

                }
            } else {
                LoginClient.instance.loginWithKakaoAccount(this) { token, error ->
                    Log.i(TAG, "loginWithKakaoAccount $token $error")
                    val kakaoToken: String = token?.accessToken.toString()
                    val postRequest = PostKakaoLoginRequest(kakaoToken = kakaoToken)
                    showLoadingDialog(this)
                    Log.d(TAG, "onCreate: kakaoToken: $kakaoToken, postRequest: $postRequest")
                    LoginService(this).tryPostKakaoLogin(postRequest)

                    Thread {
                        Thread.sleep(1500)
                        try {
                            Handler(Looper.getMainLooper()).post() {
                                if (isLogin) {
                                    isLogin = false
                                    updateKakaoLogin()
                                }
                                if (isLoginDone) {
                                    isLoginDone = !isLoginDone
                                    throw Exception()
                                }
                            }
                        } catch (e: Exception){
                        }
                    }.start()

                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivityResult: $requestCode $resultCode $data")
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateKakaoLogin() {
        UserApiClient.instance.me { user, error ->
            user?.let {

                Log.d(TAG, "updateKakaoLoginUi: id = ${user.id}")
                Log.d(TAG, "updateKakaoLoginUi: name = ${user.kakaoAccount?.profile?.nickname}")
//                user_name = user.kakaoAccount?.profile?.nickname.toString()
//                profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                isKakaoLogin = true
                Log.d(TAG, "updateKakaoLoginUi: id = ${user.kakaoAccount?.profile?.thumbnailImageUrl.toString()}")

//                SharedPreferenced.putSettingItem(KAKAO_LOGIN, isKakaoLogin.toString())
//                SharedPreferenced.putSettingItem(KAKAO_ID, user_id)
//                SharedPreferenced.putSettingItem(KAKAO_IMG, profileImageUrl)

                isLoginDone = true

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
        Log.d(TAG, "onPostKakaoLoginSuccess: kakaoId = ${response.jwt}")
        Log.d(TAG, "onPostKakaoLoginSuccess: isSuccess = ${response.isSuccess}")
        Log.d(TAG, "onPostKakaoLoginSuccess: code = ${response.code}")
        Log.d(TAG, "onPostKakaoLoginSuccess: message = ${response.message}")
        X_ACCESS_TOKEN = response.jwt
        SharedPreferenced.putSettingItem("X-ACCESS-TOKEN", X_ACCESS_TOKEN)
        Log.d(TAG, "onPostKakaoLoginSuccess: X_ACCESS_TOKEN = $X_ACCESS_TOKEN")
        isLogin = true
        response.message?.let { showCustomToast(it) }
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostFacebookLoginSuccess(response: FacebookLoginResponse) {
//        dismissLoadingDialog()
        Log.d(TAG, "onPostFacebookLoginSuccess: jwt = ${response.jwt}")
        Log.d(TAG, "onPostFacebookLoginSuccess: isSuccess = ${response.isSuccess}")
        Log.d(TAG, "onPostFacebookLoginSuccess: code = ${response.code}")
        Log.d(TAG, "onPostFacebookLoginSuccess: message = ${response.message}")
        X_ACCESS_TOKEN = response.jwt
        SharedPreferenced.putSettingItem("X-ACCESS-TOKEN", X_ACCESS_TOKEN)
        Log.d(TAG, "onPostFacebookLoginSuccess: X_ACCESS_TOKEN = $X_ACCESS_TOKEN")
        isLogin = true
        response.message?.let { showCustomToast(it) }
    }

    override fun onPostFacebookLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

}