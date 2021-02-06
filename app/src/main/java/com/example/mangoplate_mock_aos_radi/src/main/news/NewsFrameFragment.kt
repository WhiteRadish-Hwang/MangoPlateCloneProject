package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsFrameBinding
import com.google.android.material.tabs.TabLayoutMediator

class NewsFrameFragment : BaseFragment<FragmentNewsFrameBinding>(FragmentNewsFrameBinding::bind, R.layout.fragment_news_frame){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsVp.adapter = DiscountTabPagerAdapter(this)

        TabLayoutMediator(binding.newsTabLayout, binding.newsVp) {tab, position ->
            when (position) {
                0 -> {
                    tab.setText(R.string.total_tab_name)
                }
                1 -> {
                    tab.setText(R.string.following)
                }
                2 -> {
                    tab.setText(R.string.hollic_tab_name)
                }
            }
        }.attach()



    }

    private inner class DiscountTabPagerAdapter(fragment: NewsFrameFragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> TotalFragment()
                1 -> FollowingFragment()
                else -> HolicFragment()
            }
        }
    }
}