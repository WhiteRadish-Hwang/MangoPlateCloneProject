package com.example.mangoplate_mock_aos_radi.src.main.home

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentImgSlideSrcBinding

class HomeImageSlideFragment(val image: String, val text: String?): BaseFragment<FragmentImgSlideSrcBinding>(FragmentImgSlideSrcBinding::bind, R.layout.fragment_img_slide_src) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(binding.homeVpImg).load(image).into(binding.homeVpImg)
        binding.homeVpText.text = text
    }
}