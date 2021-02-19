package com.example.mangoplate_mock_aos_radi.src.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Paint
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.LOC_LIST
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.myLatitude
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.myLongitude
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.restaurantListSize
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sortPivotSelect
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.topListSize
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.SharedPreferenced
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeBinding
import com.example.mangoplate_mock_aos_radi.src.main.FirebaseImgtest
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.home.adapter.HomeRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFragment
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFragment.Companion.homeDetailsKey
import com.example.mangoplate_mock_aos_radi.src.main.home.location.GangbukFragment.Companion.locList
import com.example.mangoplate_mock_aos_radi.src.main.home.location.LocationSelectFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.model.*
import com.example.mangoplate_mock_aos_radi.src.main.home.search.HomeSearchFragment
import kotlin.properties.Delegates


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), HomeFragmentView{
    val itemList = ArrayList<HomeRecyclerItems>()
    lateinit var homeRecyclerAdapter: HomeRecyclerAdapter
    lateinit var gridLayoutManager: GridLayoutManager

    var pageNum: Int = 0
    var limit = 10
    var itemIndex = 1
    var isCalled = false
    var isLoading = false
    var isSuccessful: Boolean = false
    var isClear: Boolean = false

    //topList 변수 선언
    var topArrayList = ArrayList<TopListResultData>()
    //restaurant 변수 선언
    var restaurantArrayList = ArrayList<RestaurantResultData>()
    var reviewCount by Delegates.notNull<Int>()

    var locationFilter_sungBuk: Int = 0
    var locationFilter_suYu: Int = 0
    var locationFilter_noWon: Int = 0

    var getLongitude by Delegates.notNull<Double>()
    var getLatitude by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferenced.getArrayStringItem(LOC_LIST).let { locList = it!! }
        Log.d(TAG, "locList: $locList")

        for (loc in locList) {
            when (loc) {
                "성북구" -> {
                    locationFilter_sungBuk = 1
                }
                "수유/도봉/강북" -> {
                    locationFilter_suYu = 2
                }
                "노원구" -> {
                    locationFilter_noWon = 3
                }
                "전체" -> {
                    locationFilter_sungBuk = 1
                    locationFilter_suYu = 2
                    locationFilter_noWon = 3
                }
            }
        }

        if (locList.isEmpty()) {
            locationFilter_sungBuk = 1
            locationFilter_suYu = 2
            locationFilter_noWon = 3
        }

//        // 현재 위치 가져오기
//        val Im = (activity as MainActivity).getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val isGPSEnabled: Boolean = Im.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        val isNetworkEnabled: Boolean = Im.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//
//        Log.d(TAG, "onCreate1: ${Build.VERSION.SDK_INT}")
//        Log.d(TAG, "onCreate1: ${ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)}")
//        Log.d(TAG, "onCreate1: ${PackageManager.PERMISSION_GRANTED}")
//        if (Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
//        } else {
//            when {
//                isGPSEnabled -> {
//                    val location = Im.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//                    getLatitude = location?.longitude!!
//                    getLatitude = location.latitude
//                    showCustomToast("GPS 현재위치를 불러옴 $getLatitude, $getLongitude")
//                }
//                isNetworkEnabled -> {
//                    val location = Im.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//                    getLongitude = location?.longitude!!
//                    getLatitude = location.latitude
//                    showCustomToast("NET 현재위치를 불러옴 $getLatitude, $getLongitude")
//                }
//
//                else -> {
//
//                }
//            }
//
//        }

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
                    (activity as MainActivity).addFragment(HomeSearchFragment())
                    true
                }
                R.id.menu_home_toolbar_map -> {
                    showCustomToast("Clicked Map Item")
                    (activity as MainActivity).addFragment(FirebaseImgtest())
                    true
                }

                else -> false
            }
        }

        binding.homeToolbarTvLocChangedText.setOnClickListener {
            val locSelectFragment = LocationSelectFragment()
            locSelectFragment.show((activity as MainActivity).supportFragmentManager, "LocSel")
        }

        val totalLocTitle  = String.format(getString(R.string.toolbar_tv_loc_changed_text_ex))
        when (locList.size) {
            0 -> binding.homeToolbarTvLocChangedText.text = totalLocTitle
            1 -> binding.homeToolbarTvLocChangedText.text = locList[0]
            else -> {
                val manyLocTitle = String.format(getString(R.string.location_select_text_many_loc_title, locList[0], locList.size - 1))
                binding.homeToolbarTvLocChangedText.text = manyLocTitle
            }
        }

        binding.homeLayoutSortSelect.setOnClickListener {
            val homeSortSelectFragment = HomeSortSelectFragment {
                when (it) {
                    0 -> {
                        sortPivotSelect = "평점순"
                        clearFilter(restaurantArrayList, 4)
                    }
                    1 -> {
                        sortPivotSelect = "추천순"
                        clearFilter(restaurantArrayList, 2)
                    }
                    2 -> {
                        sortPivotSelect = "리뷰순"
                        clearFilter(restaurantArrayList, 3)
                    }
                    3 -> {
                        sortPivotSelect = "거리순"
                        clearFilter(restaurantArrayList, 1)
                    }
                }
                Log.d(TAG, "onViewCreated: $sortPivotSelect")
                setSortPivotSelect()
            }
            homeSortSelectFragment.show((activity as MainActivity).supportFragmentManager, "BottomSheetDialog")
        }

    }

    private fun excuteHomeService(){
        Log.d(TAG, "excuteHomeService: $pageNum, $limit")
        Log.d(TAG, "excuteHomeService: $locationFilter_sungBuk")
        Log.d(TAG, "excuteHomeService: $locationFilter_suYu")
        Log.d(TAG, "excuteHomeService: $locationFilter_noWon")
        Log.d(TAG, "excuteHomeService: $myLatitude")
        Log.d(TAG, "excuteHomeService: $myLongitude")
        HomeService(this).tryGetRestaurants(page = pageNum*limit, limit = limit, locationfilter1 = locationFilter_sungBuk, locationfilter2 = locationFilter_suYu, locationfilter3 = locationFilter_noWon,
                distance = 10000, sort = 4, userLatitude = myLatitude!!.toFloat(), userLongitude = myLongitude!!.toFloat())
    }

    fun<T> clearFilter(itemList: ArrayList<T>, sort: Int) {
        isClear = true
        topArrayList.clear()
        itemList.clear()
        pageNum = 0
        limit = 10
        itemIndex = 1
        homeRecyclerAdapter.clearItemList()
        HomeService(this).tryGetRestaurants(page = pageNum*limit, limit = limit, locationfilter1 = 1, locationfilter2 = 2, locationfilter3 = 3,
                distance = 10000, sort = sort, userLatitude = myLatitude!!.toFloat(), userLongitude = myLongitude!!.toFloat())
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
                1 -> HomeImageSlideFragment(topArrayList[position].topListImgUrl, topArrayList[position].topListName)
                2 -> HomeImageSlideFragment(topArrayList[position].topListImgUrl, topArrayList[position].topListName)
                3 -> HomeImageSlideFragment(topArrayList[position].topListImgUrl, topArrayList[position].topListName)
                else -> HomeImageSlideFragment(topArrayList[topListSize-1].topListImgUrl, topArrayList[topListSize-1].topListName)
            }
        }
    }

    fun setDataAndRecyclerAdapter() {
        isLoading = true
        binding.homeProgressbarBelowRecycler.visibility = View.VISIBLE
        val start = (pageNum * limit)
        var end = start + limit - 1
        if (end > restaurantListSize) end = restaurantListSize -1

        for (i in start .. end) {
            val item = HomeRecyclerItems(idx = itemIndex++,
                    title = restaurantArrayList[i].restaurantName,
                    areaName = restaurantArrayList[i].areaName,
                    star = restaurantArrayList[i].star,
                    viewPoint = restaurantArrayList[i].restaurantView,
                    reviewCount = restaurantArrayList[i].reviewCount,
                    image = restaurantArrayList[i].firstImageUrl,
                    distanceFromUser = restaurantArrayList[i].distanceFromUser,
                    restaurantId = restaurantArrayList[i].restaurantId,
                    isLike = restaurantArrayList[i].isLike,
                    isVisited = restaurantArrayList[i].visited)
            itemList.add(item)
        }

        Handler().postDelayed({
            if (this::homeRecyclerAdapter.isInitialized) {
                homeRecyclerAdapter.notifyDataSetChanged()
            } else {
                homeRecyclerAdapter = HomeRecyclerAdapter(context, itemList)
                binding.homeMainRecycler.apply {
                    gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    layoutManager = gridLayoutManager

                    // 무한스크롤 리스너
                    binding.homeMainRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)

                            val layoutManager = gridLayoutManager
                            val totalItemCount: Int = layoutManager.itemCount
                            val lastVisible: Int = layoutManager.findLastCompletelyVisibleItemPosition ()

                            if (lastVisible >= totalItemCount - 3 && isCalled && !isClear) {
                                isCalled = false
                                if (!isLoading) {
                                    pageNum++
                                    excuteHomeService()
                                }
                            }
                        }
                    })

                    // 메인 리사이클러 아이템클릭 리스터
                    homeRecyclerAdapter.let {
                        it.setMyItemClickListener(object :
                            HomeRecyclerAdapter.MyItemClickListener {
                            override fun onItemClick(position: Int) {
                                (activity as MainActivity).addFragment(HomeDetailsFragment().apply {
                                    arguments = Bundle().apply {
                                        putInt(homeDetailsKey, restaurantArrayList[position].restaurantId)
                                        putSerializable("itemListKey",itemList)
                                    }
                                })
                            }
                        })
                    }

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
        topListSize = topList.size
        isSuccessful = response.isSuccess

        for (idx in 0 until topList.size){
            topArrayList.add(topList[idx])
        }

        for (idx in 0 until restaurantList.size){
            restaurantArrayList.add(restaurantList[idx])
        }

        restaurantListSize = restaurantArrayList.size

        val pagerAdapter = ImageSlidePagerAdapter(this)
        binding.homeVp.adapter = pagerAdapter

        binding.homeVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.homeVpIndicator1.setImageResource(R.drawable.vp_indicator_circle)
                binding.homeVpIndicator2.setImageResource(R.drawable.vp_indicator_circle)
                binding.homeVpIndicator3.setImageResource(R.drawable.vp_indicator_circle)
                binding.homeVpIndicator4.setImageResource(R.drawable.vp_indicator_circle)
                binding.homeVpIndicator5.setImageResource(R.drawable.vp_indicator_circle)

                when (position) {
                    0 -> binding.homeVpIndicator1.setImageResource(R.drawable.vp_indicator_circle_clicked)
                    1 -> binding.homeVpIndicator2.setImageResource(R.drawable.vp_indicator_circle_clicked)
                    2 -> binding.homeVpIndicator3.setImageResource(R.drawable.vp_indicator_circle_clicked)
                    3 -> binding.homeVpIndicator4.setImageResource(R.drawable.vp_indicator_circle_clicked)
                    4 -> binding.homeVpIndicator5.setImageResource(R.drawable.vp_indicator_circle_clicked)
                }

            }
        })

        isCalled = true
        isClear = false
        setDataAndRecyclerAdapter()
    }

    override fun onGetRestaurantFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPatchWannaGoSuccess(response: PatchWannagoResponse) {
        homeRecyclerAdapter.notifyDataSetChanged()
    }

    override fun onPatchWannaGoFailure(message: String) {

    }

}

