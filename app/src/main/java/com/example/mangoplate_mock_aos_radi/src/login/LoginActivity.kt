package com.example.mangoplate_mock_aos_radi.src.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.FB_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isFacebookLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isKakaoLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.profileImageUrl
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_id
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_name
import com.example.mangoplate_mock_aos_radi.config.BaseActivity
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.ActivityLoginBinding
import com.example.mangoplate_mock_aos_radi.src.login.model.FacebookLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.KakaoLoginResponse
import com.example.mangoplate_mock_aos_radi.src.login.model.PostFacebookLoginRequest
import com.example.mangoplate_mock_aos_radi.src.login.model.PostKakaoLoginRequest
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class LoginActivity :BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate), LoginActivityView {
    val callbackManager = CallbackManager.Factory.create();



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)

        val keyHash = Utility.getKeyHash(this)
        Log.d(ApplicationClass.TAG, "keyHash: $keyHash")
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
        val accessToken = AccessToken.getCurrentAccessToken()

        Log.d(TAG, "token: $accessToken, tokenTracker: $accessTokenTracker")

//        binding.loginBtnFacebook.setReadPermissions("user_status")
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    val request: GraphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    object : GraphRequest.GraphJSONArrayCallback,
                        GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(objects: JSONArray?, response: GraphResponse?) {
                            Log.d(TAG, "onCompleted jsonArray: $objects")
                        }

                        override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                            val name = `object`?.getString("name")
                            user_name = name
                            val id = `object`?.getString("id")
                            val url = URL("https://graph.facebook.com/$id/picture")
//                            val profile = Profile.getCurrentProfile()
//                            val img = profile.getProfilePictureUri(200, 200).toString()
                            profileImageUrl = url.toString()
                            Log.d(TAG, "onCompleted: $name $url")
                        }

                    })
                    request.executeAsync()
                    showLoadingDialog(this@LoginActivity)
                    val currentAccessToken = AccessToken.getCurrentAccessToken().token.toString()
                    val postFbRequest = PostFacebookLoginRequest(facebookToken = currentAccessToken)
                    Log.d(TAG, "onCreate: currentAccessToken: $currentAccessToken, postFbRequest: $postFbRequest")
                    LoginService(this@LoginActivity).tryPostFacebookLogin(postFbRequest)

                    isFacebookLogin = true
                    SharedPreferenced.putSettingItem(FB_LOGIN, isFacebookLogin.toString())

                    startActivity(intent)
                    finish()
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

                    updateKakaoLogin()
                }
            } else {
                LoginClient.instance.loginWithKakaoAccount(this) { token, error ->
                    Log.i(TAG, "loginWithKakaoAccount $token $error")
                    val kakaoToken: String = token?.accessToken.toString()
                    val postRequest = PostKakaoLoginRequest(kakaoToken = kakaoToken)
                    showLoadingDialog(this)
                    Log.d(TAG, "onCreate: kakaoToken: $kakaoToken, postRequest: $postRequest")
                    LoginService(this).tryPostKakaoLogin(postRequest)

                    updateKakaoLogin()
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
//                Log.d(TAG, "updateKakaoLoginUi: id = ${user.id}")
//                Log.d(TAG, "updateKakaoLoginUi: id = ${user.kakaoAccount?.profile?.nickname}")
//                user_name = user.kakaoAccount?.profile?.nickname.toString()
//                profileImageUrl = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                isKakaoLogin = true

//                SharedPreferenced.putSettingItem(KAKAO_LOGIN, isKakaoLogin.toString())
//                SharedPreferenced.putSettingItem(KAKAO_ID, user_id)
//                SharedPreferenced.putSettingItem(KAKAO_IMG, profileImageUrl)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            error?.let {

            }
        }

    }

    override fun onPostKakaoLoginSuccess(response: KakaoLoginResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.kakaoId}")
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.userId}")
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.isSuccess}")
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.code}")
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.message}")
        user_id = response.userId.toString()
        response.message?.let { showCustomToast(it) }
    }

    override fun onPostKakaoLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostFacebookLoginSuccess(response: FacebookLoginResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.userId}")
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.facebookId}")
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.isSuccess}")
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.code}")
        Log.d(TAG, "onPostSignUpSuccess: result = ${response.message}")
        response.message?.let { showCustomToast(it) }
    }

    override fun onPostFacebookLoginFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }


}