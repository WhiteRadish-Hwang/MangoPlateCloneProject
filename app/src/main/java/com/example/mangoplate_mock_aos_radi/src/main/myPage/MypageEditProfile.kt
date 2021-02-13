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
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.myPage.MypageEditName.Companion.isEditDone

class MypageEditProfile: BaseFragment<FragmentMyPageEditProfileBinding>(FragmentMyPageEditProfileBinding::bind, R.layout.fragment_my_page_edit_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (isEditDone) {
//            (activity as MainActivity).onBackPressed()
//        }

        binding.mpEditImgArrowLeft.setOnClickListener {
            (activity as MainActivity).onBackPressed()
            (activity as MainActivity).replaceFragment(MyPageFragment())
        }

        Log.d(TAG, "user_name: $user_name")
        Log.d(TAG, "profileImageUrl: $profileImageUrl")
        Log.d(TAG, "user_phone_number: $user_phone_number")
        Log.d(TAG, "user_email: $user_email")

        binding.mpTextNameValue.text = user_name

        Glide.with(binding.mpEditImgProfileImage).load(profileImageUrl).circleCrop().placeholder(R.drawable.profile).into(binding.mpEditImgProfileImage)

        if (user_phone_number != "-1" || user_phone_number.isNullOrBlank()) {
            binding.mpTextPhoneNumberValue.visibility = View.VISIBLE
            binding.mpTextPhoneNumberValue.text = user_phone_number
        }

        if (user_email != "-1" || user_email.isNullOrBlank()) {
            binding.mpTextEmailValue.visibility = View.VISIBLE
            binding.mpTextEmailValue.text = user_email
        }

        binding.mpEditLayoutName.setOnClickListener {
            (activity as MainActivity).onBackPressed()
            (activity as MainActivity).addFragment(MypageEditName())
        }
        binding.mpEditLayoutEmail.setOnClickListener {
            (activity as MainActivity).onBackPressed()
            (activity as MainActivity).addFragment(MypageEditEmail())
        }
        binding.mpEditLayoutPhonenumber.setOnClickListener {
            (activity as MainActivity).onBackPressed()
            (activity as MainActivity).addFragment(MypageEditPhonenumber())
        }


    }
}