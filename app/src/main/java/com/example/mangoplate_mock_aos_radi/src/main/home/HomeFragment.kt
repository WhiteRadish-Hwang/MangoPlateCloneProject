package com.example.mangoplate_mock_aos_radi.src.main.home

import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.restaurantListSize
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sortPivotSelect
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.topListSize
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_id
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
    val itemList = ArrayList<HomeRecyclerItems>()
    lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    lateinit var gridLayoutManager: GridLayoutManager

    var pageNum: Int = 0
    var limit = 4
    var isEnd = false

    var isLoading = false
    var isSuccessful: Boolean = false

    //topList 변수 선언
    var topArrayList = ArrayList<TopListResultData>()
    var topListId by Delegates.notNull<Int>()
    lateinit var topListImgUrl: String
    lateinit var topListName: String
    //restaurant 변수 선언
    var restaurantArrayList = ArrayList<RestaurantResultData>()
    var reviewCount by Delegates.notNull<Int>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 홈서비스 실행
        excuteHomeService()
        // 메인 리사이클러뷰 정렬기준 선택
        setSortPivotSelect()
        binding.homeTextSortSelect.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        val anim = TranslateAnimation(binding.homeLayoutSearch.width.toFloat(), 0f, 0f, 0f)
        val anim2 = TranslateAnimation(0f, binding.homeLayoutSearch.width.toFloat(), 0f, 0f)

        binding.textView5.setOnClickListener {
            anim2.duration = 2000
            anim2.fillAfter = true
            binding.homeLayoutSearch.animation = anim
            binding.homeLayoutSearch.visibility = View.GONE
        }

        binding.homeToolbar.inflateMenu(R.menu.menu_home_toolbar)
        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.menu_home_toolbar_search -> {
                    anim.duration = 2000
                    anim.fillAfter = true
                    binding.homeLayoutSearch.animation = anim
                    binding.homeLayoutSearch.visibility = View.VISIBLE

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

        binding.homeLayoutSortSelect.setOnClickListener {
            val homeSortSelectFragment = HomeSortSelectFragment {
                when (it) {
                    0 -> {
                        sortPivotSelect = "평점순"
                        pageNum = 0
                        limit = 4
                        HomeService(this).tryGetRestaurants(page = pageNum, limit = limit, areaName = "성북", distance = 10, sort = it, userId = 1, userLatitude = 37.6511723f, userLongitude = 127.0481563f)
                    }
                    1 -> {
                        sortPivotSelect = "추천순"
                        pageNum = 0
                        limit = 4
                        HomeService(this).tryGetRestaurants(page = pageNum, limit = limit, areaName = "성북", distance = 10, sort = it, userId = 1, userLatitude = 37.6511723f, userLongitude = 127.0481563f)
                    }
                    2 -> {
                        sortPivotSelect = "리뷰순"
                        pageNum = 0
                        limit = 4
                        HomeService(this).tryGetRestaurants(page = pageNum, limit = limit, areaName = "성북", distance = 10, sort = it, userId = 1, userLatitude = 37.6511723f, userLongitude = 127.0481563f)
                    }
                    3 -> {
                        sortPivotSelect = "거리순"
                        pageNum = 0
                        limit = 4
                        HomeService(this).tryGetRestaurants(page = pageNum, limit = limit, areaName = "성북", distance = 10, sort = it, userId = 1, userLatitude = 37.6511723f, userLongitude = 127.0481563f)
                    }
                }
                Log.d(ApplicationClass.TAG, "onViewCreated: $sortPivotSelect")
                setSortPivotSelect()
            }
            homeSortSelectFragment.show((activity as MainActivity).supportFragmentManager, "BottomSheetDialog")
        }

        //무한스크롤 리스너
        var serviceCount = 1
        var callBackAt = serviceCount * (limit * 100)
        binding.homeNestedScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                v?.let {
                    if (scrollY > callBackAt && !isEnd) {
                        val visibleItemCount = binding.homeMainRecycler.childCount
                        val totalItemCount = homeRecyclerAdapter.itemCount
                        val firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()

                        if (!isLoading) {
                            if ((visibleItemCount + firstVisibleItem) <= totalItemCount) {
                                pageNum++
                                callBackAt += (++serviceCount * (restaurantListSize * 100))
                                excuteHomeService()
                            }
                        }

                    }
                }

            }

        })

    }

    private fun excuteHomeService(){
        Log.d(TAG, "excuteHomeService: $pageNum, $limit")
        HomeService(this).tryGetRestaurants(page = pageNum*limit, limit = limit, areaName = "성북", distance = 10, sort = 1, userId = 1, userLatitude = 37.6511723f, userLongitude = 127.0481563f)
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

    var idx = 1
    fun setDataAndRecyclerAdapter() {
        isLoading = true
        binding.homeProgressbarBelowRecycler.visibility = View.VISIBLE
        val start = (pageNum * limit)
        var end = (pageNum * limit) + limit - 1
        Log.d(TAG, "setDataAndRecyclerAdapter: pageNum:$pageNum, limit: $limit")
        Log.d(TAG, "setDataAndRecyclerAdapter: size: $restaurantListSize")
        Log.d(TAG, "setDataAndRecyclerAdapter: end >= restaurantListSize = ${end >= restaurantListSize}, isEnd: = $isEnd")
        if (end < restaurantListSize) {
            Log.d(TAG, "setDataAndRecyclerAdapter: start:$start, end: $end")
            for (i in start..end) {
                val item = HomeRecyclerItems(idx = restaurantArrayList[i].restaurantId,
                        title = restaurantArrayList[i].restaurantName,
                        location = restaurantArrayList[i].areaName,
                        grade = restaurantArrayList[i].star,
                        viewPoint = restaurantArrayList[i].restaurantView,
                        reviewCount = restaurantArrayList[i].reviewCount,
                        image = restaurantArrayList[i].firstImageUrl)
                itemList.add(item)
                Log.d(TAG, "initData: $item")
            }
        } else if (end >= restaurantListSize && !isEnd) {
            isEnd = true
            end = restaurantListSize-1
            Log.d(TAG, "setDataAndRecyclerAdapter: start:$start, end: $end")
            for (i in start..end) {
                val item = HomeRecyclerItems(idx = restaurantArrayList[i].restaurantId,
                        title = restaurantArrayList[i].restaurantName,
                        location = restaurantArrayList[i].areaName,
                        grade = restaurantArrayList[i].star,
                        viewPoint = restaurantArrayList[i].restaurantView,
                        reviewCount = restaurantArrayList[i].reviewCount,
                        image = restaurantArrayList[i].firstImageUrl)
                itemList.add(item)
                Log.d(TAG, "initDataEnd: $item")
            }
        }

        Handler().post{
            Log.d(TAG, "initData: ${idx++}")
            if (this::homeRecyclerAdapter.isInitialized) {
                Log.d(TAG, "initData: if")
                homeRecyclerAdapter.notifyDataSetChanged()
            } else {
                Log.d(TAG, "initData: else")
                homeRecyclerAdapter = HomeRecyclerAdapter(context, itemList)
                binding.homeMainRecycler.apply {
                    gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    setHasFixedSize(true)
                    adapter = homeRecyclerAdapter
                }
            }
            isLoading = false
            binding.homeProgressbarBelowRecycler.visibility = View.GONE
        }

    }

    override fun onGetRestaurantSuccess(response: RestaurantsResponse, topList: ArrayList<TopListResultData>, restaurantList: ArrayList<RestaurantResultData>) {
//        dismissLoadingDialog()
        Log.d(TAG, "onGetRestaurantSuccess: $topList")
        Log.d(TAG, "onGetRestaurantSuccess: $restaurantList")
        topListSize = topList.size
        isSuccessful = response.isSuccess

        for (idx in 0 until topList.size){
            topArrayList.add(topList[idx])
        }

        for (idx in 0 until restaurantList.size){
            restaurantArrayList.add(restaurantList[idx])
        }

        restaurantListSize = restaurantArrayList.size
        Log.d(TAG, "restaurantListSize: $restaurantListSize")
        Log.d(TAG, "restaurantArrayList: $restaurantArrayList")

        val pagerAdapter = ImageSlidePagerAdapter(this)
        binding.homeVp.adapter = pagerAdapter
        setDataAndRecyclerAdapter()
    }

    override fun onGetRestaurantFailure(message: String) {

    }


}

