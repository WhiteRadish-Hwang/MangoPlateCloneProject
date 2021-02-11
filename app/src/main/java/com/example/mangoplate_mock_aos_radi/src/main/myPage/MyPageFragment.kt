package com.example.mangoplate_mock_aos_radi.src.main.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.FB_ID
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.FB_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_ID
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_IMG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isFacebookLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGetMyInfo
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isKakaoLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.profileImageUrl
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_id
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_name
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentMyPageBinding
import com.example.mangoplate_mock_aos_radi.src.login.LoginActivity
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResponse
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResultData
import com.facebook.login.LoginManager
import com.kakao.sdk.user.UserApiClient

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page), MypageFragmentView{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isGetMyInfo) excuteGetMyInfo()

        user_name?.let {
            Glide.with(binding.myPageImgProfile).load(profileImageUrl).circleCrop().override(110, 110).into(binding.myPageImgProfile)
            binding.myPageTextUserName.text = user_name
        }

        binding.myPageToolbar.inflateMenu(R.menu.menu_my_page_toolbar)
        binding.myPageToolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.menu_home_toolbar_search -> {
                    showCustomToast("Clicked Bell Item")
                    Log.d(TAG, "Clicked Bell Item")
                    true
                }
                else -> false
            }
        }



        binding.myPageBtnLogout.setOnClickListener {
            user_id = null
            user_name = null
            profileImageUrl = null
            Log.d(TAG, "isKakaoLogin: $isKakaoLogin, isFacebookLogin: $isFacebookLogin")
            if (isKakaoLogin) {
                UserApiClient.instance.me { user, error ->
                    user?.let { UserApiClient.instance.logout {
                        isKakaoLogin = false
                        SharedPreferenced.putSettingItem(KAKAO_LOGIN, isKakaoLogin.toString())
                        SharedPreferenced.putSettingItem(KAKAO_ID, user_name)
                        SharedPreferenced.putSettingItem(KAKAO_IMG, profileImageUrl)

                        val intentLogout = Intent(context, LoginActivity::class.java)
                        startActivity(intentLogout)
                        (activity as MainActivity).finish()
                    } }
                }
            } else {
                isFacebookLogin = false
                SharedPreferenced.putSettingItem(FB_LOGIN, isFacebookLogin.toString())
                SharedPreferenced.putSettingItem(FB_ID, user_name)
                SharedPreferenced.putSettingItem(FB_ID, profileImageUrl)
                LoginManager.getInstance().logOut()

                val intentLogout = Intent(context, LoginActivity::class.java)
                startActivity(intentLogout)
                (activity as MainActivity).finish()
            }
        }

    }

    fun excuteGetMyInfo() {
        showLoadingDialog(context!!)
        MyPageService(this).tryGetMyInfo()
    }

    override fun onGetMyInfoSuccess(response: MyInfoResponse, infoList: ArrayList<MyInfoResultData>) {
        dismissLoadingDialog()
        Log.d(TAG, "onGetMyInfoSuccess: result = ${infoList}")
        Log.d(TAG, "onGetMyInfoSuccess: result = ${response.isSuccess}")
        Log.d(TAG, "onGetMyInfoSuccess: result = ${response.code}")
        Log.d(TAG, "onGetMyInfoSuccess: result = ${response.message}")

        if (!infoList.isNullOrEmpty()) {
            user_id = infoList[0].userId.toString()
            user_name = infoList[0].userName
            profileImageUrl = infoList[0].userProfileImgUrl
        }
        Log.d(TAG, "onGetMyInfoSuccess: user_id = ${user_id}")
        Log.d(TAG, "user_name: user_name = ${user_name}}")
        Log.d(TAG, "onGetMyInfoSuccess: profileImageUrl = ${profileImageUrl}}")

        Glide.with(binding.myPageImgProfile).load(profileImageUrl).circleCrop().override(110, 110).into(binding.myPageImgProfile)
        binding.myPageTextUserName.text = user_name

        response.message?.let { showCustomToast(it) }
        isGetMyInfo = true
    }

    override fun onGetMyInfoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }


}