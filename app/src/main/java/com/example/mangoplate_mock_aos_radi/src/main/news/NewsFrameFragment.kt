package com.example.mangoplate_mock_aos_radi.src.main.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fBad
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fGood
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fGreat
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isBad
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGood
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGreat
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsFrameBinding
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.properties.Delegates

class NewsFrameFragment : BaseFragment<FragmentNewsFrameBinding>(FragmentNewsFrameBinding::bind, R.layout.fragment_news_frame){
    companion object {
        const val reviewListKey = "reviewListKey"
    }

    var f_reviewList = ArrayList<TotalReviewResultData>()

    @SuppressLint("ResourceAsColor")
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


        binding.newBtnGreat.setOnClickListener {
            isGreat = !isGreat
            Log.d(TAG, "isGreat = $isGreat")
            when (isGreat) {
                true -> {
                    fGreat = 1
                    binding.newBtnGreat.setBackgroundResource(R.drawable.news_sort_select_text_border)
                }
                false -> {
                    fGreat = 0
                    binding.newBtnGreat.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                }
            }
        }
        binding.newBtnGood.setOnClickListener {
            isGood = !isGood
            Log.d(TAG, "isGood = $isGood")
            when (isGood) {
                true -> {
                    fGood = 1
                    binding.newBtnGood.setBackgroundResource(R.drawable.news_sort_select_text_border)
                }
                false -> {
                    fGood = 0
                    binding.newBtnGood.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                }
            }
        }
        binding.newBtnBad.setOnClickListener {
            isBad = !isBad
            Log.d(TAG, "isBad = $isBad")
            when (isBad) {
                true -> {
                    fBad = 1
                    binding.newBtnBad.setBackgroundResource(R.drawable.news_sort_select_text_border)
                }
                false -> {
                    fBad = 0
                    binding.newBtnBad.setBackgroundResource(R.drawable.news_sort_select_text_unclicked_border)
                }
            }
        }


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