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
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isKakaoLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.profileImageUrl
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_id
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentMyPageBinding
import com.example.mangoplate_mock_aos_radi.src.login.LoginActivity
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.facebook.login.LoginManager
import com.kakao.sdk.user.UserApiClient

class MyPageFragment : BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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

        Glide.with(binding.myPageImgProfile).load(ApplicationClass.profileImageUrl).circleCrop().into(binding.myPageImgProfile)
        binding.myPageTextUserName.text = user_id

        binding.myPageBtnLogout.setOnClickListener {
            user_id = null
            profileImageUrl = null
            if (isKakaoLogin) {
                UserApiClient.instance.me { user, error ->
                    user?.let { UserApiClient.instance.logout {
                        isKakaoLogin = false
                        SharedPreferenced.putSettingItem(KAKAO_LOGIN, isKakaoLogin.toString())
                        SharedPreferenced.putSettingItem(KAKAO_ID, user_id)
                        SharedPreferenced.putSettingItem(KAKAO_IMG, profileImageUrl)

                        val intentLogout = Intent(context, LoginActivity::class.java)
                        startActivity(intentLogout)
                        (activity as MainActivity).finish()
                    } }
                }
            } else if (isFacebookLogin) {
                isFacebookLogin = false
                SharedPreferenced.putSettingItem(FB_LOGIN, isFacebookLogin.toString())
                SharedPreferenced.putSettingItem(FB_ID, user_id)
                SharedPreferenced.putSettingItem(FB_ID, profileImageUrl)
                LoginManager.getInstance().logOut()
                (activity as MainActivity).finish()
            }
        }

    }


}