package com.example.mangoplate_mock_aos_radi.src.main

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseActivity
import com.example.mangoplate_mock_aos_radi.databinding.ActivityMainBinding
import com.example.mangoplate_mock_aos_radi.src.main.add.AddFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeFragment
import com.example.mangoplate_mock_aos_radi.src.main.myPage.MyPageFragment
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    companion object {
        var backStack = true
        var fragmentBack: Fragment? = null
    }

//    private val homeFragment by lazy { HomeFragment() }
//    private val discountFragment by lazy { DiscountFragment() }
//    private val addFragment by lazy { AddFragment() }
//    private val newsFragment by lazy { NewsFragment() }
//    private val myPageFragment by lazy { MyPageFragment() }
//
//    private val fragments: List<Fragment> = listOf( homeFragment, discountFragment, addFragment, newsFragment, myPageFragment)
//
//    private val pagerAdapter: MainViewPagerAdapter by lazy { MainViewPagerAdapter(this, fragments) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commitAllowingStateLoss()
//        binding.mainVp.adapter = MainTabPagerAdapter(supportFragmentManager)

//        TabLayoutMediator(binding.mainLayoutTab, binding.mainVp) {tab, position ->
//            when (position) {
//                0 -> {
//                    tab.setIcon(R.drawable.home)
//                    tab.setText(R.string.main_bottom_nav_home_title)
//                }
//                1 -> {
//                    tab.setIcon(R.drawable.discount)
//                    tab.setText(R.string.main_bottom_nav_discount_title)
//                }
//                2 -> {
//                    tab.setIcon(R.drawable.mango_add)
//                }
//                3 -> {
//                    tab.setIcon(R.drawable.news)
//                    tab.setText(R.string.main_bottom_nav_news_title)
//                }
//                4 -> {
//                    tab.setIcon(R.drawable.mypage)
//                    tab.setText(R.string.main_bottom_nav_my_page_title)
//                }
//            }
//        }.attach()

        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                var page = 0
                when (item.itemId) {
                    R.id.menu_main_bottom_nav_home -> {
                        page = R.id.menu_main_bottom_nav_home
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame, HomeFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_bottom_nav_discount -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame, DiscountFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_bottom_nav_add -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame, AddFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_bottom_nav_news -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame, NewsFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_bottom_nav_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frame, MyPageFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                }

                false
            })

//        binding.mainBtmNav.run{
//            setOnNavigationItemSelectedListener { item ->
//                val page = when (item.itemId) {
//                    R.id.menu_main_bottom_nav_home -> 0
//                    R.id.menu_main_bottom_nav_discount -> 1
//                    R.id.menu_main_bottom_nav_add -> 2
//                    R.id.menu_main_bottom_nav_news -> 3
//                    R.id.menu_main_bottom_nav_my_page -> 4
//                    else -> 0
//                }
//                if (page != binding.mainVp.currentItem) {
//                    binding.mainVp.currentItem = page
//                }
//
//                true
//            }
//            selectedItemId = R.id.menu_main_bottom_nav_home
//        }
//
//
//        binding.mainVp.run {
//            adapter = pagerAdapter
//            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    val navigation = when(position) {
//                        R.id.menu_main_bottom_nav_home -> 0
//                        R.id.menu_main_bottom_nav_discount -> 1
//                        R.id.menu_main_bottom_nav_add -> 2
//                        R.id.menu_main_bottom_nav_news -> 3
//                        R.id.menu_main_bottom_nav_my_page -> 4
//                        else -> 0
//                    }
//                    if (binding.mainBtmNav.selectedItemId != navigation) {
//                        binding.mainBtmNav.selectedItemId = navigation
//                    }
//                }
//            })
//        }

    }

    inner class MainViewPagerAdapter(activity: AppCompatActivity, private val fragments: List<Fragment>) : FragmentStateAdapter(activity) {
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
        Log.d(ApplicationClass.TAG, "addFragment: backstck= $backStack")
        Log.d(ApplicationClass.TAG, "addFragment: $fragment")
        fmbt.setCustomAnimations(R.anim.enter_fragment,0, 0, R.anim.exit_fragment)
        fmbt.add(R.id.main_layout_frame, fragment).addToBackStack("fragment").commit()
    }

    override fun onBackPressed() {
        Log.d(ApplicationClass.TAG, "onBackPressed: backstck= $backStack")
        if (backStack) { //상세정보창 프래그먼트를 킨 상태면 뒤로가기했을 때 해당 프래그먼트를 삭제해줌
            fragmentBack?.let { supportFragmentManager.popBackStack() }
            backStack = false
        } else {
            super.onBackPressed()
        }
    }

}