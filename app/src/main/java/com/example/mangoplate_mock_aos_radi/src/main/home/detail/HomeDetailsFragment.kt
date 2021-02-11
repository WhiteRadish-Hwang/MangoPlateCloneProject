package com.example.mangoplate_mock_aos_radi.src.main.home.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeRestaurantDetailsBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.*
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerInnerImageAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerInnerImageItems
import kotlin.properties.Delegates

class HomeDetailsFragment: BaseFragment<FragmentHomeRestaurantDetailsBinding>(FragmentHomeRestaurantDetailsBinding::bind, R.layout.fragment_home_restaurant_details), HomeDetailsFragmentView {
    companion object {
        const val homeDetailsKey = "restaurantId"
//        fun newInstance(restaurantId: Int) = HomeRestaurantDetailsFragment().apply {
//            arguments = Bundle().apply {
//                putInt(homeDetailsKey, restaurantId)
//            }
//            Log.d(TAG, "newInstance123: $arguments")
//        }
    }

    val imgsItemList = ArrayList<TotalRecyclerInnerImageItems>()
    val menuImgArrayList = ArrayList<String>()
    val keyWordArrayList = ArrayList<String>()
    val reviewArrayList = ArrayList<ReviewResultData>()

    // 상세정보 변수
    lateinit var restaurantName: String
    lateinit var star: String
    var restaurantView by Delegates.notNull<Int>()
    var likeCount by Delegates.notNull<Int>()
    var reviewCount by Delegates.notNull<Int>()
    lateinit var restaurantInfo: String
    lateinit var restaurantLocation: String
    lateinit var restaurantLatitude: String
    lateinit var restaurantLongitude: String
    lateinit var restaurantTime: String
    lateinit var restaurantHoliday: String
    lateinit var restaurantRestTime: String
    lateinit var restaurantPrice: String
    lateinit var restaurantMenu: String

    // 리뷰카운트 변수
    var deliciousCount by Delegates.notNull<Int>()
    var okayCount by Delegates.notNull<Int>()
    var badCount by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "receiveId: ${arguments?.get(homeDetailsKey)}")
        val restaurantId = arguments?.get(homeDetailsKey)
        //상세페이지 서비스 호출
        DetailsService(this).tryGetRestaurants(restaurantId as Int)


        binding.detailsImgBackArrow.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
        
        binding.detailsToolbar.inflateMenu(R.menu.menu_details_toolbar)
        binding.detailsToolbar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.menu_home_toolbar_search -> {
                    showCustomToast("Clicked Camara Item")
                    true
                }
                R.id.menu_home_toolbar_map -> {
                    showCustomToast("Clicked Share Item")
                    true
                }

                else -> false
            }
        }


        val fmbt = (activity as MainActivity).supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.details_layout_frame, HomeDetailsFrameFragment()).commit()
    }

    fun viewHoldingData() {
        binding.detailsTextRestaurantName.text = restaurantName
        binding.detailsTextViewCount.text = restaurantView.toString()
        binding.detailsTextWannaGoCount.text = likeCount.toString()
        binding.detailsTextReviewCount.text = reviewCount.toString()

    }

    fun setRecyclerAdapter(){

        binding.detailsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = TotalRecyclerInnerImageAdapter(context, imgsItemList)
        }
    }

    override fun onGetDetailsSuccess(
        response: DetailsResponse,
        imgsList: ArrayList<ImgsResultData>,
        detailedInfoList: ArrayList<DetailedInfoResultData>,
        menuImgList: ArrayList<MenuImgResultData>,
        keyWordList: ArrayList<KeyWordResultData>,
        reviewCountList: ArrayList<ReviewCountResultData>,
        reviewList: ArrayList<ReviewResultData>,
        reviewImgList: ArrayList<ReviewImgResultData>,
        nearRestaurantList: ArrayList<NearRestaurantResultData>,
        areaResultList: ArrayList<AreaResultData>
    ) {
        Log.d(TAG, "onGetDetailsSuccess: ${response.result}")
        Log.d(TAG, "imgsList: $imgsList")
        Log.d(TAG, "detailedInfoList: $detailedInfoList")
        Log.d(TAG, "menuImgList: $menuImgList")
        Log.d(TAG, "keyWordList: $keyWordList")
        Log.d(TAG, "reviewCountList: $reviewCountList")
        Log.d(TAG, "reviewList: $reviewList")
        Log.d(TAG, "reviewImgList: $reviewImgList")
        Log.d(TAG, "nearRestaurantList: $nearRestaurantList")
        Log.d(TAG, "areaResultList: $areaResultList")

        // 이너 이미지 데이터입력
        for (i in 0 until imgsList.size) imgsItemList.add(TotalRecyclerInnerImageItems(innerImage = imgsList[i].reviewImgUrl))

        // 식당 상세정보 --->>> inspection, restaurantId, userLike, uservisited, restaurantPhoneNumber 변수 없음
        for (i in 0 until detailedInfoList.size) {
            restaurantName = detailedInfoList[i].restaurantName
            star = detailedInfoList[i].star
            restaurantView = detailedInfoList[i].restaurantView
            likeCount = detailedInfoList[i].likeCount
            reviewCount = detailedInfoList[i].reviewCount
            restaurantInfo = detailedInfoList[i].restaurantInfo
            restaurantLocation = detailedInfoList[i].restaurantLocation
            restaurantLatitude = detailedInfoList[i].restaurantLatitude
            restaurantLongitude = detailedInfoList[i].restaurantLongitude
            restaurantTime = detailedInfoList[i].restaurantTime
            restaurantHoliday = detailedInfoList[i].restaurantHoliday
            restaurantRestTime = detailedInfoList[i].restaurantRestTime
            restaurantPrice = detailedInfoList[i].restaurantPrice
            restaurantMenu = detailedInfoList[i].restaurantMenu
        }

        // 메뉴 이미지
        for (i in 0 until menuImgList.size) menuImgArrayList.add(menuImgList[i].restaurantMenuImgUrl)

        // 키워드
        for (i in 0 until menuImgList.size) keyWordArrayList.add(keyWordList[i].restaurantKeyWord)

        // 맛깔나는 리뷰 카운트 --->>> reviewCount 중복이라 안 받음
        for (i in 0 until reviewCountList.size) {
            deliciousCount = reviewCountList[i].deliciousCount
            okayCount = reviewCountList[i].okayCount
            badCount = reviewCountList[i].badCount
        }

//        // 리뷰
//        for (i in 0 until reviewList.size) {
//
//        }

        // 이너 리사이클러뷰 어답터
        setRecyclerAdapter()
        // 식당 정보 데이터
        viewHoldingData()
    }

    override fun onGetDetailsFailure(message: String) {
        showCustomToast("오류 : $message")
    }

}