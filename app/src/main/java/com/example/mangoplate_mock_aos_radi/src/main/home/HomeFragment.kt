package com.example.mangoplate_mock_aos_radi.src.main.home

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sortPivotSelect
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.home.adapter.HomeRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse

class HomeFragment  : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), HomeFragmentView{
    val NUM_PAGES = 3
    var backStack = true
    var fragmentBack: Fragment? = null
    val itemList = ArrayList<HomeRecyclerItems>()
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerAdapter()
        setSortPivotSelect()
        binding.homeTextSortSelect.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.homeToolbar.inflateMenu(R.menu.menu_home_toolbar)
        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.menu_home_toolbar_search -> {
                    showCustomToast("Clicked Search Item")
                    true
                }

                R.id.menu_home_toolbar_map -> {
                    showCustomToast("Clicked Map Item")
                    true
                }

                else -> false
            }
        }

        // 뷰페이저로 구현한 이미지 슬라이더, 어답터
        val pagerAdapter = ImageSlidePagerAdapter(this)
        binding.homeVp.adapter = pagerAdapter

        binding.homeLayoutSortSelect.setOnClickListener {
            val homeSortSelectFragment = HomeSortSelectFragment {
                when (it) {
                    0 -> {
                        sortPivotSelect = "평점순"
                        showCustomToast("평점순")
                    }
                    1 -> {
                        sortPivotSelect = "추천순"
                        showCustomToast("추천순")
                    }
                    2 -> {
                        sortPivotSelect = "리뷰순"
                        showCustomToast("리뷰순")
                    }
                    3 -> {
                        sortPivotSelect = "거리순"
                        showCustomToast("거리순")
                    }
                }
                Log.d(ApplicationClass.TAG, "onViewCreated: $sortPivotSelect")
                setSortPivotSelect()
            }
            homeSortSelectFragment.show((activity as MainActivity).supportFragmentManager, "BottomSheetDialog")
        }


    }

    fun setSortPivotSelect(){
        when(sortPivotSelect) {
            "평점순" -> {
                binding.homeTextSortSelect.text = "평점순"
            }
            "추천순" -> {
                binding.homeTextSortSelect.text = "추천순"
            }
            "리뷰순" -> {
                binding.homeTextSortSelect.text = "리뷰순"
            }
            "거리순" -> {
                binding.homeTextSortSelect.text = "거리순"
            }
            else -> {
                binding.homeTextSortSelect.text = "평점순"
            }
        }
    }

    private inner class ImageSlidePagerAdapter(fragment: HomeFragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> HomeImageSlideFragment(R.drawable.home_vp_img1, getString(R.string.home_vp_dummy_text2))
                1 -> HomeImageSlideFragment(R.drawable.home_vp_img2, null)
                else -> HomeImageSlideFragment(R.drawable.home_vp_img3, getString(R.string.home_vp_dummy_text1))
            }
        }
    }

    fun setRecyclerAdapter(){
        homeRecyclerAdapter = HomeRecyclerAdapter(context, itemList)
        initData()
        binding.homeMainRecycler.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = homeRecyclerAdapter
        }
    }

    fun initData(){
        HomeService(this).tryGetRestaurants(page = 1, limit = 10, areaName = "성북", distance = 10, sort = 1)

        val checkedLoc = binding.homeToolbarTvLocChangedText.text.toString()

        for (i in 0..10) {
            val item1 = HomeRecyclerItems(idx = i+1, title = "쉐프마인드", location = checkedLoc, grade = "4.3", viewPoint = 11111, reviewCount = 11, image = "https://images.unsplash.com/photo-1499028344343-cd173ffc68a9?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80")
            itemList.add(item1)
        }
    }

    override fun onGetRestaurantSuccess(response: RestaurantsResponse) {
//        dismissLoadingDialog()
        for (restaurant in response.result) {
            Log.d(TAG, "onGetRestaurantSuccess: $restaurant")
        }
    }

    override fun onGetRestaurantFailure(message: String) {

    }


}

