package com.example.mangoplate_mock_aos_radi.src.main.myPage

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.profileImageUrl
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_email
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_name
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_phone_number
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentMyPageEditProfileBinding
import com.example.mangoplate_mock_aos_radi.databinding.FragmentMyPageEditProfileNameBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.EditUserInfoResponse
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResponse
import com.example.mangoplate_mock_aos_radi.src.main.myPage.model.MyInfoResultData

class MypageEditName: BaseFragment<FragmentMyPageEditProfileNameBinding>(FragmentMyPageEditProfileNameBinding::bind, R.layout.fragment_my_page_edit_profile_name), MypageFragmentView {
    companion object {
        var isEditDone: Boolean = false
    }
    var changedUserName: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mpEditEtName.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "onTextChanged: ${s?.length!!}")
                if (s?.length!! < 2) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.mpEditEtName.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.red)
                        binding.mpEditBtnApply.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.uncliked_color)
//                        binding.mpEditBtnApply.background = ContextCompat.getDrawable(requireContext(), R.drawable.mp_btn_background_unclickable)
                        binding.mpEditBtnApply.isEnabled = false
                    }
                    binding.mpEditTextNameError.visibility = View.VISIBLE
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.mpEditEtName.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.black)
                        binding.mpEditBtnApply.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.cliked_color)
//                        binding.mpEditBtnApply.background = ContextCompat.getDrawable(requireContext(), R.drawable.mp_btn_background_clickable)
                        binding.mpEditBtnApply.isEnabled = true
                    }

                    binding.mpEditTextNameError.visibility = View.GONE
                }

            }

        })

        binding.mpEditBtnApply.setOnClickListener {
            showLoadingDialog(context!!)
            changedUserName = binding.mpEditEtName.text.toString()
            Log.d(TAG, "changedUserName: $changedUserName")
            MyPageService(this).tryPostUserInfo(1, changedUserName)
            (activity as MainActivity).onBackPressed()
            (activity as MainActivity).addFragment(MypageEditProfile())
            isEditDone = true
            user_name = changedUserName
        }

        binding.mpEditNameImgArrowLeft.setOnClickListener {
            (activity as MainActivity).onBackPressed()
            (activity as MainActivity).addFragment(MypageEditProfile())
        }


    }

    override fun onGetMyInfoSuccess(response: MyInfoResponse, infoList: ArrayList<MyInfoResultData>) {

    }

    override fun onGetMyInfoFailure(message: String) {

    }

    override fun onPostEditUserInfoSuccess(response: EditUserInfoResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPostEditUserInfoSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPostEditUserInfoSuccess: ${response.code}")
        Log.d(TAG, "onPostEditUserInfoSuccess: ${response.message}")

    }

    override fun onPostEditUserInfoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}