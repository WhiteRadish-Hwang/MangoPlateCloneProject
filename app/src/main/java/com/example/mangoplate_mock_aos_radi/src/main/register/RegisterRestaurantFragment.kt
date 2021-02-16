package com.example.mangoplate_mock_aos_radi.src.main.register

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentRegisterRestaurantBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.register.model.PostRegisterRequest
import com.example.mangoplate_mock_aos_radi.src.main.register.model.RegisterResponse

class RegisterRestaurantFragment: BaseFragment<FragmentRegisterRestaurantBinding>(FragmentRegisterRestaurantBinding::bind, R.layout.fragment_register_restaurant), RegisterFragmentView{
    var isFillResName: Boolean = false
    var isFillResLoc: Boolean = false
    var isCanApply: Boolean = false

    var filterNum: Int = 0

    var registerIsFilterSelect: Boolean = false

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerImgArrow.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        binding.registerTextRestaurantName.setOnClickListener {
            if (!isFillResLoc) {
                binding.registerTextRestaurantName.setTextSize(Dimension.SP, 12f)
                binding.registerEtRestaurantName.visibility = View.VISIBLE
                binding.registerTextRestaurantLoc.setTextSize(Dimension.SP, 15f)
                binding.registerEtRestaurantLoc.visibility = View.GONE
            } else {
                binding.registerTextRestaurantName.setTextSize(Dimension.SP, 12f)
                binding.registerEtRestaurantName.visibility = View.VISIBLE
            }
        }

        binding.registerTextRestaurantLoc.setOnClickListener {
            if (!isFillResName) {
                binding.registerTextRestaurantLoc.setTextSize(Dimension.SP, 12f)
                binding.registerEtRestaurantLoc.visibility = View.VISIBLE
                binding.registerTextRestaurantName.setTextSize(Dimension.SP, 15f)
                binding.registerEtRestaurantName.visibility = View.GONE
            } else {
                binding.registerTextRestaurantLoc.setTextSize(Dimension.SP, 25f)
                binding.registerEtRestaurantLoc.visibility = View.VISIBLE
            }
        }

        binding.registerEtRestaurantPhoneNumber.setOnClickListener {
            if (!isFillResName) {
                binding.registerTextRestaurantName.setTextSize(Dimension.SP, 15f)
                binding.registerEtRestaurantName.visibility = View.GONE
            }
            if (!isFillResLoc){
                binding.registerTextRestaurantLoc.setTextSize(Dimension.SP, 15f)
                binding.registerEtRestaurantLoc.visibility = View.GONE
            }
        }

        binding.registerEtRestaurantName.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isFillResName = s?.length!! > 0
                Log.d(TAG, "isFillResName: $isFillResName")
                isCheckCanRegister()
            }

        })
        binding.registerEtRestaurantLoc.addTextChangedListener ( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isFillResLoc = s?.length!! > 0
                Log.d(TAG, "isFillResLoc: $isFillResLoc")
                isCheckCanRegister()
            }

        })

        binding.registerLayoutHansik.setOnClickListener {
            binding.registerImgHansik.setImageResource(R.drawable.res_hansik_click)
            binding.registerTextHansik.setTextColor(Color.parseColor("#ff8104"))
            filterNum = 1
        }
        binding.registerLayoutIlsik.setOnClickListener {
            binding.registerImgIlsik.setImageResource(R.drawable.res_ilsik_click)
            binding.registerTextIlsik.setTextColor(Color.parseColor("#ff8104"))
            filterNum = 2
        }
        binding.registerLayoutJoongsik.setOnClickListener {
            binding.registerImgJoongsik.setImageResource(R.drawable.res_joongsik_click)
            binding.registerTextJoongsik.setTextColor(Color.parseColor("#ff8104"))
            filterNum = 3
        }
        binding.registerLayoutYangsik.setOnClickListener {
            binding.registerImgYangsik.setImageResource(R.drawable.res_joongsik_click)
            binding.registerTextYangsik.setTextColor(Color.parseColor("#ff8104"))
            filterNum = 4
        }
        binding.registerLayoutWorld.setOnClickListener {
            binding.registerImgWorld.setImageResource(R.drawable.res_world_click)
            binding.registerTextWorld.setTextColor(Color.parseColor("#ff8104"))
            filterNum = 5
        }
        binding.registerLayoutBuffet.setOnClickListener {
            binding.registerImgBuffet.setImageResource(R.drawable.res_buffet_click)
            binding.registerTextBuffet.setTextColor(Color.parseColor("#ff8104"))
            filterNum = 6
        }
        binding.registerLayoutCafe.setOnClickListener {
            binding.registerImgCafe.setImageResource(R.drawable.res_cafe_click)
            binding.registerTextCafe.setTextColor(Color.parseColor("#ff8104"))
            filterNum = 7
        }
        binding.registerLayoutBar.setOnClickListener {
            binding.registerImgBar.setImageResource(R.drawable.res_bar_click)
            binding.registerTextBar.setTextColor(Color.parseColor("#ff8104"))
            filterNum = 8
        }

        binding.registerBtnAddApply.setOnClickListener {
            if (isCanApply) executeRegisterService()
        }

    }

    fun executeRegisterService() {
        val restaurantName: String = binding.registerEtRestaurantName.text.toString()
        val restaurantLatitude: String = "37.588237"
        val restaurantLongitude: String = "127.017248"
        val restaurantPhoneNumber: String = binding.registerEtRestaurantPhoneNumber.text.toString()
        val restaurantFilter: Int = filterNum

        val registerParams = PostRegisterRequest(restaurantName, restaurantLatitude, restaurantLongitude, restaurantPhoneNumber, restaurantFilter)

        showLoadingDialog(context!!)
        RegisterService(this).tryPostRegister(registerParams)
    }

    fun isCheckCanRegister() {
        if (isFillResName && isFillResLoc) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.registerBtnAddApply.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.cliked_color)
                binding.registerBtnAddApply.isEnabled = true
                isCanApply = true
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                binding.registerBtnAddApply.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.more_light_gray)
                binding.registerBtnAddApply.isEnabled = false
            }
        }
    }

    override fun onPostRegisterInfoSuccess(response: RegisterResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPostEditUserInfoSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPostEditUserInfoSuccess: ${response.code}")
        Log.d(TAG, "onPostEditUserInfoSuccess: ${response.message}")
        Log.d(TAG, "onPostEditUserInfoSuccess: ${response.restaurantId}")

        showCustomToast("식당 등록 성공")

        (activity as MainActivity).onBackPressed()
    }

    override fun onPostRegisterInfoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

}