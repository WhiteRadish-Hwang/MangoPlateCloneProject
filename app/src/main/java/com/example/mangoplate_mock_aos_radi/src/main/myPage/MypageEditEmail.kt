package com.example.mangoplate_mock_aos_radi.src.main.myPage

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.profileImageUrl
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_email
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_name
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_phone_number
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentMyPageEditProfileBinding
import com.example.mangoplate_mock_aos_radi.databinding.FragmentMyPageEditProfileEmailBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity

class MypageEditEmail: BaseFragment<FragmentMyPageEditProfileEmailBinding>(FragmentMyPageEditProfileEmailBinding::bind, R.layout.fragment_my_page_edit_profile_email) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }
}