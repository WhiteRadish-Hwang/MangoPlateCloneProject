package com.example.mangoplate_mock_aos_radi.src.main.home

import android.os.Bundle
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentImgSlideSrcBinding

class HomeImageSlideFragment(val image: Int, val text: String?): BaseFragment<FragmentImgSlideSrcBinding>(FragmentImgSlideSrcBinding::bind, R.layout.fragment_img_slide_src) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeVpImg.setImageResource(image)
        binding.homeVpText.text = text
    }
}