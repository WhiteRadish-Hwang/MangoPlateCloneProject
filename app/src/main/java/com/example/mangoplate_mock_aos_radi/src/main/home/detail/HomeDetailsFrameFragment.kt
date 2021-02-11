package com.example.mangoplate_mock_aos_radi.src.main.home.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeRestaurantDetailsFrameBinding
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsFrameBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeDetailsFrameFragment : BaseFragment<FragmentHomeRestaurantDetailsFrameBinding>(FragmentHomeRestaurantDetailsFrameBinding::bind, R.layout.fragment_home_restaurant_details_frame){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}