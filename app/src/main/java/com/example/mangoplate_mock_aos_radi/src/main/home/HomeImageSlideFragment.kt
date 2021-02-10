package com.example.mangoplate_mock_aos_radi.src.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeImgSlideSrcBinding
import kotlin.math.log

class HomeImageSlideFragment(val image: String, val text: String?): BaseFragment<FragmentHomeImgSlideSrcBinding>(FragmentHomeImgSlideSrcBinding::bind, R.layout.fragment_home_img_slide_src) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(binding.homeVpImg).load(image).into(binding.homeVpImg)
        binding.homeVpText.text = text

//        binding.homeVpLayout.setOnTouchListener { v, event ->
//
//            Log.d(TAG, "onViewCreated: ${event}")
//
//            Log.d(ApplicationClass.TAG, "homeVpImg: ${event.action}")
//            if (event.action == MotionEvent.ACTION_MOVE){
//                Log.d(ApplicationClass.TAG, "homeVpImg: ACTION_MOVE")
//            }
//            if (event.action == MotionEvent.ACTION_DOWN){
//                v.onTouchEvent(event)
//                Log.d(ApplicationClass.TAG, "homeVpImg: ACTION_DOWN")
//            }
//            if (event.action == MotionEvent.ACTION_UP){
//                Log.d(ApplicationClass.TAG, "homeVpImg: ACTION_UP")
//            }
//            return@setOnTouchListener false
//        }
    }
}