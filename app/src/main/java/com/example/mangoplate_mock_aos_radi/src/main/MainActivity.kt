package com.example.mangoplate_mock_aos_radi.src.main

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.FB_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.KAKAO_LOGIN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isFacebookLogin
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isKakaoLogin
import com.example.mangoplate_mock_aos_radi.config.BaseActivity
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.ActivityMainBinding
import com.example.mangoplate_mock_aos_radi.src.main.add.AddFragment
import com.example.mangoplate_mock_aos_radi.src.main.discount.DiscountFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeFragment
import com.example.mangoplate_mock_aos_radi.src.main.myPage.MyPageFragment
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    companion object {
        var backStack = true
        var fragmentBack: Fragment? = null
        var isOpen: Boolean = false
        var isFgInAddOpen: Boolean = false
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 앨범에 접근허용 메세지 띄움, 최초 1회만 띄움움
       ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)

        isKakaoLogin = SharedPreferenced.getSettingItem(KAKAO_LOGIN)?.toBoolean() ?: false
        isFacebookLogin = SharedPreferenced.getSettingItem(FB_LOGIN)?.toBoolean() ?: false

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


        binding.mainFbtn.setOnClickListener {
            val cx = binding.mainLayoutFrame.width / 2
            val cy2 = binding.mainLayoutFrame.height - binding.mainFbtn.height / 2
            val cy = binding.mainLayoutFrame.height
            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()


            if (!isOpen) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    isOpen = !isOpen
                    val revealAnimator: Animator = ViewAnimationUtils
                        .createCircularReveal(binding.mainLayoutFrame, cx, cy2, 0f, finalRadius)

                    addFragment(AddFragment())
                    revealAnimator.duration = 200
                    revealAnimator.start()
                }
            } else {
                isOpen = !isOpen
                val revealAnimator: Animator = ViewAnimationUtils
                    .createCircularReveal(binding.mainLayoutFrame, cx, cy2, finalRadius, 0f)
                revealAnimator.duration = 200
                revealAnimator.start()
            }
        }

        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
//                var page = 0
                when (item.itemId) {
                    R.id.menu_main_bottom_nav_home -> {
//                        page = R.id.menu_main_bottom_nav_home
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
//                    R.id.menu_main_bottom_nav_add -> {
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.main_frame, AddFragment())
//                            .commitAllowingStateLoss()
//                        return@OnNavigationItemSelectedListener true
//                    }
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

    inner class MainViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
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
        Log.d(TAG, "addFragment: $fragment")
        val fmbt = supportFragmentManager.beginTransaction()
        backStack = true
        fragmentBack = fragment

        if (isOpen){
            fmbt.add(R.id.main_layout_frame, fragment).addToBackStack("fragment").commit()
        } else {
            Log.d(TAG, "addFragment: $fragment")
            isOpen = false
            fmbt.setCustomAnimations(R.anim.enter_fragment, 0, 0, R.anim.exit_fragment)
            fmbt.add(R.id.main_layout_sub_frame, fragment).addToBackStack("fragment").commit()
        }
    }

    fun replaceFragment(fragment: Fragment) {
        val fmbt = supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.main_frame, fragment).commitAllowingStateLoss()
    }

    fun replaceFragmentInSubFrame(fragment: Fragment) {
        val fmbt = supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.main_layout_sub_frame, fragment).commitAllowingStateLoss()
    }

    override fun onBackPressed() {
        if (backStack) { //상세정보창 프래그먼트를 킨 상태면 뒤로가기했을 때 해당 프래그먼트를 삭제해줌
            fragmentBack?.let { supportFragmentManager.popBackStack() }
            backStack = false
        } else {
            super.onBackPressed()
        }
    }
    fun onBackPressedAndReplace(fragment: Fragment?) {
        if (backStack) { //상세정보창 프래그먼트를 킨 상태면 뒤로가기했을 때 해당 프래그먼트를 삭제해줌
            fragmentBack?.let { supportFragmentManager.popBackStack() }
            backStack = false
            fragment?.let { replaceFragment(fragment) }
        } else {
            super.onBackPressed()
        }
    }
}