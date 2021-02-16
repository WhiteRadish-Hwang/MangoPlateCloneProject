package com.example.mangoplate_mock_aos_radi.src.main.discount

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentDiscountBinding
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealFragment.Companion.isEatDealLoc
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealFragment.Companion.isEatDealTotal
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealLocFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.EatDealTotalFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.mgPick.MangoPickStoryFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.TopListFragment
import com.google.android.material.tabs.TabLayoutMediator

class DiscountFragment : BaseFragment<FragmentDiscountBinding>(FragmentDiscountBinding::bind, R.layout.fragment_discount){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.discountVp.adapter = DiscountTabPagerAdapter(this)

        TabLayoutMediator(binding.discountTabLayout, binding.discountVp) {tab, position ->
            when (position) {
                0 -> {
                    tab.setText(R.string.eat_deal_tab_name)
                }
                1 -> {
                    tab.setText(R.string.mango_pick_story_tab_name)
                }
                2 -> {
                    tab.setText(R.string.top_list_tab_name)
                }
            }
        }.attach()



    }

    private inner class DiscountTabPagerAdapter(fragment: DiscountFragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> {
                    when {
                        isEatDealTotal -> {
                            EatDealTotalFragment()
                        }
                        isEatDealLoc -> {
                            EatDealLocFragment()

                        }
                        else -> {
                            EatDealFragment()
                        }

                    }

                }
                1 -> MangoPickStoryFragment()
                else -> TopListFragment()
            }
        }
    }
}