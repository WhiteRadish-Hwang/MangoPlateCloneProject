package com.example.mangoplate_mock_aos_radi.src.main.home

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mangoplate_mock_aos_radi.R
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
import com.example.mangoplate_mock_aos_radi.src.main.home.search.HomeSearchFragment
import com.example.mangoplate_mock_aos_radi.src.main.location.LocationSelectFragment
import kotlin.properties.Delegates

class HomeFragment  : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), HomeFragmentView{
    val itemList = ArrayList<HomeRecyclerItems>()
    lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    lateinit var gridLayoutManager: GridLayoutManager


    var pageNum: Int = 0
    var limit = 4
    var isEnd = false
    var itemIndex = 1

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showCustomToast("onCreate")
        // 홈서비스 실행
        excuteHomeService()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 메인 리사이클러뷰 정렬기준 선택
        setSortPivotSelect()
        binding.homeTextSortSelect.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        binding.homeToolbar.inflateMenu(R.menu.menu_home_toolbar)
        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.menu_home_toolbar_search -> {
                    showCustomToast("Clicked Search Item")
                    binding.homeLayoutFrame.visibility = View.VISIBLE
                    (activity as MainActivity).addFragment(HomeSearchFragment())
//                    (activity as MainActivity).supportFragmentManager.beginTransaction()
//                            .replace(R.id.home_layout_search_frame,HomeFragment()).addToBackStack("search").commit()
                    true
                }
                R.id.menu_home_toolbar_map -> {
                    showCustomToast("Clicked Map Item")
                    true
                }

                else -> false
            }
        }

        binding.homeToolbarTvLocChangedText.setOnClickListener {
            val locSelectFragment = LocationSelectFragment()
            locSelectFragment.show((activity as MainActivity).supportFragmentManager, "LocSel")
        }

        binding.homeVp.setOnTouchListener { v, event ->
            v.dispatchTouchEvent(event)
            return@setOnTouchListener false
        }


        binding.homeLayoutSortSelect.setOnClickListener {
            val homeSortSelectFragment = HomeSortSelectFragment {
                when (it) {
                    0 -> {
                        sortPivotSelect = "평점순"
                        clearFilter(restaurantArrayList, it + 1)
                    }
                    1 -> {
                        sortPivotSelect = "추천순"
                        clearFilter(restaurantArrayList, it + 1)
                    }
                    2 -> {
                        sortPivotSelect = "리뷰순"
                        clearFilter(restaurantArrayList, it + 1)
                    }
                    3 -> {
                        sortPivotSelect = "거리순"
                        clearFilter(restaurantArrayList, it + 1)
                    }
                }
                Log.d(TAG, "onViewCreated: $sortPivotSelect")
                setSortPivotSelect()
            }
            homeSortSelectFragment.show((activity as MainActivity).supportFragmentManager, "BottomSheetDialog")
        }

        //무한스크롤 리스너
        var serviceCount = 1
        var callBackAt = serviceCount * (limit * 100)
        binding.homeNestedScrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                Log.d(TAG, "onScrollChange: height: ${v?.height} scrollY: $scrollY, callBackAt: $callBackAt")
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

        Thread {
            Thread.sleep(1500)
            Handler(Looper.getMainLooper()).post(){
                showLoadingDialog(context!!)
            }
            // 메인 리사이클러 아이템클릭 리스터
            homeRecyclerAdapter.let {
                homeRecyclerAdapter.setMyItemClickListener(object :
                    HomeRecyclerAdapter.MyItemClickListener {
                    override fun onItemClick(position: Int) {
                        showCustomToast("position = $position")

                        restaurantArrayList[position]

//                MainActivity.backStack = true
//                MainActivity.fragmentBack = HomeRestaurantDetailsFragment()
//                binding.homeLayoutFrame.visibility = View.VISIBLE
//                (activity as MainActivity).supportFragmentManager.beginTransaction()
//                    .replace(R.id.home_layout_frame,HomeRestaurantDetailsFragment().apply {
//                        arguments = Bundle().apply {
//                            putInt("position", position)
//                        }
//                    }).setCustomAnimations(R.anim.enter_fragment,0, 0, R.anim.exit_fragment)
//                    .addToBackStack("fragment").commit()

                        (activity as MainActivity).addFragment(HomeRestaurantDetailsFragment().apply {
                            arguments = Bundle().apply {
                                putInt("position", position)
                            }
                        })

                    }

                })
            }
            Handler(Looper.getMainLooper()).post(){
                dismissLoadingDialog()
            }
        }.start()

    }

    private fun excuteHomeService(){
        Log.d(TAG, "excuteHomeService: $pageNum, $limit")
        HomeService(this).tryGetRestaurants(page = pageNum*limit, limit = limit, areaName = "성북", distance = 10, sort = 1, userId = 1, userLatitude = 37.6511723f, userLongitude = 127.0481563f)
    }

    fun<T> clearFilter(itemList: ArrayList<T>, sort: Int) {
        topArrayList.clear()
        itemList.clear()
        pageNum = 0
        limit = 4
        homeRecyclerAdapter.clearItemList()
        HomeService(this).tryGetRestaurants(page = pageNum, limit = limit, areaName = "성북", distance = 10, sort = sort, userId = 1, userLatitude = 37.6511723f, userLongitude = 127.0481563f)
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

    fun setDataAndRecyclerAdapter() {
        isLoading = true
        binding.homeProgressbarBelowRecycler.visibility = View.VISIBLE
        val start = (pageNum * limit)
        var end = start + limit - 1
        if (end < restaurantListSize) {
            for (i in start..end) {
                val item = HomeRecyclerItems(idx = itemIndex++,
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

        Handler().postDelayed({
            if (this::homeRecyclerAdapter.isInitialized) {
                homeRecyclerAdapter.notifyDataSetChanged()
            } else {
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
        }, 300)

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

