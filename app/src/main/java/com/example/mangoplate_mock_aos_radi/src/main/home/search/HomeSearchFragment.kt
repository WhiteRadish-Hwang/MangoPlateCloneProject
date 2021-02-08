package com.example.mangoplate_mock_aos_radi.src.main.home.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeSearchBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.add.AddFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.adapter.EatDealRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.adapter.HomeSearchRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeSearchRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.myPage.MyPageFragment
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class HomeSearchFragment: BaseFragment<FragmentHomeSearchBinding>(FragmentHomeSearchBinding::bind, R.layout.fragment_home_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.home_search_layout_frame, HomeSearchRecommendFragment())
                .commitAllowingStateLoss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeSearchImgBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        binding.homeSearchNav.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.menu_home_search_nav_recommend -> {
                            (activity as MainActivity).supportFragmentManager.beginTransaction()
                                    .replace(R.id.home_search_layout_frame,HomeSearchRecommendFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.menu_home_search_nav_recent -> {
                            (activity as MainActivity).supportFragmentManager.beginTransaction()
                                    .replace(R.id.home_search_layout_frame,HomeSearchRecentFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.menu_home_search_nav_look_for_friend -> {
                            (activity as MainActivity).supportFragmentManager.beginTransaction()
                                    .replace(R.id.home_search_layout_frame,HomeSearchLookForFriendFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }
                    }
                    false
                })



    }


    private inner class HomeSearchTabPagerAdapter(fragment: HomeSearchFragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> HomeFragment()
                1 -> DiscountFragment()
                else -> MyPageFragment()
            }
        }
    }

}