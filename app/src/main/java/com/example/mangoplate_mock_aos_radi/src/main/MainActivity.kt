package com.example.mangoplate_mock_aos_radi.src.main

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseActivity
import com.example.mangoplate_mock_aos_radi.databinding.ActivityMainBinding
import com.example.mangoplate_mock_aos_radi.src.main.add.AddFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.EatDealFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.MangoPickStoryFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.TopListFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeFragment
import com.example.mangoplate_mock_aos_radi.src.main.myPage.MyPageFragment
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.sdk.common.util.Utility
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    companion object {
        var backStack = true
        var fragmentBack: Fragment? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mainVp.adapter = MainTabPagerAdapter(this)

        TabLayoutMediator(binding.mainLayoutTab, binding.mainVp) {tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.home)
                    tab.setText(R.string.main_bottom_nav_home_title)
                }
                1 -> {
                    tab.setIcon(R.drawable.discount)
                    tab.setText(R.string.main_bottom_nav_discount_title)
                }
                2 -> {
                    tab.setIcon(R.drawable.mango_add)
                }
                3 -> {
                    tab.setIcon(R.drawable.news)
                    tab.setText(R.string.main_bottom_nav_news_title)
                }
                4 -> {
                    tab.setIcon(R.drawable.mypage)
                    tab.setText(R.string.main_bottom_nav_my_page_title)
                }
            }
        }.attach()

//        binding.mainBtmNav.setOnNavigationItemSelectedListener(
//            BottomNavigationView.OnNavigationItemSelectedListener { item ->
//                when (item.itemId) {
//                    R.id.menu_main_bottom_nav_home -> {
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frame, HomeFragment())
//                            .commitAllowingStateLoss()
//                        return@OnNavigationItemSelectedListener true
//                    }
//                    R.id.menu_main_bottom_nav_discount -> {
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frame, DiscountFragment())
//                            .commitAllowingStateLoss()
//                        return@OnNavigationItemSelectedListener true
//                    }
//                    R.id.menu_main_bottom_nav_add -> {
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frame, AddFragment())
//                            .commitAllowingStateLoss()
//                        return@OnNavigationItemSelectedListener true
//                    }
//                    R.id.menu_main_bottom_nav_news -> {
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frame, NewsFragment())
//                            .commitAllowingStateLoss()
//                        return@OnNavigationItemSelectedListener true
//                    }
//                    R.id.menu_main_bottom_nav_my_page -> {
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frame, MyPageFragment())
//                            .commitAllowingStateLoss()
//                        return@OnNavigationItemSelectedListener true
//                    }
//                }
//                false
//            })
    }

    private inner class MainTabPagerAdapter(fa: MainActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> HomeFragment()
                1 -> DiscountFragment()
                2 -> AddFragment()
                3 -> NewsFragment()
                else -> MyPageFragment()
            }
        }
    }

    fun addFragment(fragment: Fragment) {
        val fmbt = supportFragmentManager.beginTransaction()
        backStack = true
        fragmentBack = fragment
        fmbt.setCustomAnimations(R.anim.enter_fragment,0, 0, R.anim.exit_fragment)
        fmbt.add(R.id.main_layout_frame, fragment).addToBackStack("fragment").commit()
    }

    fun replaceFragment(fragment: Fragment) {
        val fmbt = supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.main_layout_frame, fragment).commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (backStack) { //상세정보창 프래그먼트를 킨 상태면 뒤로가기했을 때 해당 프래그먼트를 삭제해줌
            fragmentBack?.let { supportFragmentManager.popBackStack() }
            backStack = false
        } else {
            super.onBackPressed()
        }
    }
}