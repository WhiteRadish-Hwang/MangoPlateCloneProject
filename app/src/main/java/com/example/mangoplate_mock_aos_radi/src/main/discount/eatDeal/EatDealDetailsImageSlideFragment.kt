package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountEatDealDetailsImgSlideSrcBinding

class EatDealDetailsImageSlideFragment(val image: String): BaseFragment<FragmentDiscountEatDealDetailsImgSlideSrcBinding>(FragmentDiscountEatDealDetailsImgSlideSrcBinding::bind, R.layout.fragment_discount_eat_deal_details_img_slide_src) {
    init {
        Log.d(TAG, "EatDealDetailsImageSlideFragment: called")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(binding.dealDetailsVpImg).load(image).into(binding.dealDetailsVpImg)
    }

}