package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsFollowingBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.EatDealFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.MangoPickStoryFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.TopListFragment
import com.google.android.material.tabs.TabLayoutMediator

class FollowingFragment : BaseFragment<FragmentNewsFollowingBinding>(FragmentNewsFollowingBinding::bind, R.layout.fragment_news_following){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}