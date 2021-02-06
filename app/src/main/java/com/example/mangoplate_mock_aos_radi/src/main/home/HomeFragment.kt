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
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.restaurantListSize
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sortPivotSelect
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.topListSize
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.home.adapter.HomeRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.model.RestaurantsResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.TopListResultData
import kotlin.properties.Delegates

class HomeFragment  : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), HomeFragmentView{
    val NUM_PAGES = 3
    var backStack = true
    var fragmentBack: Fragment? = null
    val itemList = ArrayList<HomeRecyclerItems>()
    private lateinit var homeRecyclerAdapter: HomeRecyclerAdapter

    var isSuccessful: Boolean = false
    //topList 변수 선언
    var topArrayList = ArrayList<TopListResultData>()
    var topListId by Delegates.notNull<Int>()
    lateinit var topListImgUrl: String
    lateinit var topListName: String
    //restaurant 변수 선언
    var restaurantArrayList = ArrayList<RestaurantResultData>()
    var restaurantId by Delegates.notNull<Int>()
    var distance by Delegates.notNull<Int>()
    var restaurantView by Delegates.notNull<Int>()
    var reviewCount by Delegates.notNull<Int>()
    lateinit var restaurantName: String
    lateinit var areaName: String
    lateinit var star: String
    lateinit var firstImageUrl: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HomeService(this).tryGetRestaurants(page = 1, limit = 10, areaName = "성북", distance = 10, sort = 1)

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
//        if (isSuccessful) {
//            val pagerAdapter = ImageSlidePagerAdapter(this)
//            binding.homeVp.adapter = pagerAdapter
//        }
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
        override fun getItemCount(): Int = topListSize

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> HomeImageSlideFragment(topArrayList[position].topListImgUrl, topArrayList[position].topListName)
                else -> HomeImageSlideFragment(topArrayList[topListSize-1].topListImgUrl, topArrayList[topListSize-1].topListName)
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
        Log.d(TAG, "initData: ")
        for (i in 0 until restaurantListSize) {
            val item = HomeRecyclerItems(idx = restaurantArrayList[i].restaurantId,
                    title = restaurantArrayList[i].restaurantName,
                    location = restaurantArrayList[i].areaName,
                    grade = restaurantArrayList[i].star,
                    viewPoint = restaurantArrayList[i].restaurantView,
                    reviewCount = restaurantArrayList[i].reviewCount,
                    image = restaurantArrayList[i].firstImageUrl)
            itemList.add(item)
        }
    }

    override fun onGetRestaurantSuccess(response: RestaurantsResponse, topList: ArrayList<TopListResultData>, restaurantList: ArrayList<RestaurantResultData>) {
//        dismissLoadingDialog()
        Log.d(TAG, "onGetRestaurantSuccess: $topList")
        Log.d(TAG, "onGetRestaurantSuccess: $restaurantList")
        topListSize = topList.size
        restaurantListSize = restaurantList.size
        isSuccessful = response.isSuccess

        for (idx in 0 until topList.size){
            topArrayList.add(topList[idx])
//            topListId = topList[idx].topListId
//            topListImgUrl = topList[idx].topListImgUrl
//            topListName = topList[idx].topListName
        }

        for (idx in 0 until restaurantList.size){
            restaurantArrayList.add(restaurantList[idx])
//            restaurantId = restaurantList[idx].restaurantId
//            distance = restaurantList[idx].distance
//            restaurantView = restaurantList[idx].restaurantView
//            reviewCount = restaurantList[idx].reviewCount
//            restaurantName = restaurantList[idx].restaurantName
//            areaName = restaurantList[idx].areaName
//            star = restaurantList[idx].star
//            firstImageUrl = restaurantList[idx].firstImageUrl
        }

        val pagerAdapter = ImageSlidePagerAdapter(this)
        binding.homeVp.adapter = pagerAdapter
        setRecyclerAdapter()
    }

    override fun onGetRestaurantFailure(message: String) {

    }


}

