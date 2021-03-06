package com.example.mangoplate_mock_aos_radi.src.main.detail

import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeDetailsBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.keywordItemKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.nearRestaurantArrayListKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.restaurantBreakTimeKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.restaurantClosedDateKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.restaurantLatitudeKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.restaurantLocationKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.restaurantLongitudeKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.restaurantOpeningTimeKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.restaurantPriceKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.reviewBadItemKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.reviewGoodItemKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.reviewGreatItemKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.reviewResultArrayListKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.HomeDetailsFrameFragment.Companion.reviewTitleItemKey
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.*
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeFragment
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerInnerImageAdapter
import com.example.mangoplate_mock_aos_radi.src.main.visited.VisitedFragment
import com.example.mangoplate_mock_aos_radi.src.main.visited.VisitedFragment.Companion.visitedRestaurantIdKey
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs
import kotlin.properties.Delegates

class HomeDetailsFragment: BaseFragment<FragmentHomeDetailsBinding>(FragmentHomeDetailsBinding::bind, R.layout.fragment_home_details), HomeDetailsFragmentView {
    companion object {
        const val homeDetailsKey = "restaurantId"
//        fun newInstance(restaurantId: Int) = HomeRestaurantDetailsFragment().apply {
//            arguments = Bundle().apply {
//                putInt(homeDetailsKey, restaurantId)
//            }
//            Log.d(TAG, "newInstance123: $arguments")
//        }
    }

    val imgsIdList = ArrayList<Int>()
    val imgsItemList = ArrayList<String>()
    val menuImgArrayList = ArrayList<String>()
    val keyWordArrayList = ArrayList<String>()
    val reviewArrayList = ArrayList<ReviewResultData>()
    val nearRestaurantArrayList = ArrayList<NearRestaurantResultData>()

    // ???????????? ??????
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
    var userLike by Delegates.notNull<Int>()
    var userVisited by Delegates.notNull<Int>()

    var itemArrayList = ArrayList<HomeRecyclerItems>()
    var restaurantId by Delegates.notNull<Int>()

    // ??????????????? ??????
    var deliciousCount by Delegates.notNull<Int>()
    var okayCount by Delegates.notNull<Int>()
    var badCount by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "receiveId: ${arguments?.get(homeDetailsKey)}")

        restaurantId = arguments?.get(homeDetailsKey) as Int

        //??????????????? ????????? ??????
        showLoadingDialog(context!!)
        DetailsService(this).tryGetRestaurants(restaurantId)


        binding.detailsImgBackArrow.setOnClickListener {
            (activity as MainActivity).onBackPressed()
            (activity as MainActivity).replaceFragment(HomeFragment())
        }
        
//        binding.detailsToolbar.inflateMenu(R.menu.menu_details_toolbar)
//        binding.detailsToolbar.setOnMenuItemClickListener {
//            when (it.itemId){
//                R.id.menu_home_toolbar_search -> {
//                    showCustomToast("Clicked Camara Item")
//                    true
//                }
//                R.id.menu_home_toolbar_map -> {
//                    showCustomToast("Clicked Share Item")
//                    true
//                }
//
//                else -> false
//            }
//        }

        binding.detailsLayoutBottomWannaGo.setOnClickListener {
            showLoadingDialog(context!!)
            DetailsService(this).tryPatchWannago(restaurantId)
        }

        binding.detailsLayoutBottomVisited.setOnClickListener {
            (activity as MainActivity).addFragment(VisitedFragment().apply {
                arguments = Bundle().apply {
                    putInt(visitedRestaurantIdKey, restaurantId)
                }
            })
        }



        // ?????? ????????? ?????????
        binding.detailsLayoutAppBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) { // ????????????
                binding.detailsImgBackArrow.setColorFilter(Color.parseColor("#ffffff"))
                binding.detailsLayoutToolbar.setBackgroundResource(R.color.cliked_color)
                binding.detailsImgCamera.setImageResource(R.drawable.camera_collapse)
                binding.detailsImgShared.setColorFilter(Color.parseColor("#ffffff")) //clicked_color
                binding.detailsTextResName.visibility = View.VISIBLE
            } else {// ????????????
                binding.detailsImgBackArrow.setColorFilter(Color.parseColor("#ff8104")) //clicked_color
                binding.detailsLayoutToolbar.setBackgroundResource(R.drawable.home_toolbar_underline)
                binding.detailsImgCamera.setImageResource(R.drawable.camera)
                binding.detailsImgShared.setColorFilter(Color.parseColor("#ff8104"))
                binding.detailsTextResName.visibility = View.GONE
            }
        })


    }

    fun setArgumentsToFrame() {
        val fmbt = (activity as MainActivity).supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.details_layout_frame, HomeDetailsFrameFragment().apply {
            arguments = Bundle().apply {
                // ?????????
                putStringArrayList(keywordItemKey, keyWordArrayList)
                // ?????? ??????
                putSerializable(reviewResultArrayListKey, reviewArrayList)
                // ?????? ?????? ?????????
                putInt(reviewTitleItemKey, reviewCount)
                putInt(reviewGreatItemKey, deliciousCount)
                putInt(reviewGoodItemKey, okayCount)
                putInt(reviewBadItemKey, badCount)
                // ?????? ?????? ??????
                putString(restaurantOpeningTimeKey, restaurantTime)
                putString(restaurantBreakTimeKey, restaurantRestTime)
                putString(restaurantClosedDateKey, restaurantHoliday)
                putString(restaurantPriceKey, restaurantPrice)
                putString(restaurantLongitudeKey, restaurantLongitude)
                putString(restaurantLatitudeKey, restaurantLatitude)
                // ?????? ??????
                putString(restaurantLocationKey, restaurantLocation)
                // ?????? ??????
                putSerializable(nearRestaurantArrayListKey, nearRestaurantArrayList)
                // ?????? ??????
            }
            Log.d(TAG, "arguments: $arguments")
        }).commit()
    }

    fun viewHoldingData() {
        binding.detailsTextResName.text = restaurantName

        binding.detailsTextRestaurantName.text = restaurantName
        binding.detailsTextViewCount.text = restaurantView.toString()
        binding.detailsTextWannaGoCount.text = likeCount.toString()
        binding.detailsTextReviewCount.text = reviewCount.toString()
        if (userLike == 1){
            binding.detailsImgBottomWannaGo.setImageResource(R.drawable.wanna_go)
            binding.detailsImgBottomWannaGo.setColorFilter(Color.parseColor("#ff7101"))
        } else {
            binding.detailsImgBottomWannaGo.setImageResource(R.drawable.wanna_go)
            binding.detailsImgBottomWannaGo.colorFilter = null
        }

        Log.d(TAG, "userVisited: $userVisited")
        when (userVisited) {
            0 -> {
                val visitedText = String.format(getString(R.string.visited))
                binding.detailsTextBottomVisited.text = visitedText

            }
            else -> {
                val visitedText = String.format(getString(R.string.visited_add, userVisited))
                binding.detailsTextBottomVisited.text = visitedText
            }
        }
    }

    fun setRecyclerAdapter(){
        val innerImgRecyclerAdapter = TotalRecyclerInnerImageAdapter(context, imgsItemList)
        binding.detailsRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)

            // ?????? ????????? ????????? ?????? ?????????
            innerImgRecyclerAdapter.let {
                it.setMyInnerImgItemClickListener(object : TotalRecyclerInnerImageAdapter.MyInnerImgItemClickListener {
                    override fun onItemClick(position: Int) {
                        (activity as MainActivity).addFragment(HomeDetailsImageFragment().apply {
                            arguments = Bundle().apply {
                                putString("imageItemKey", imgsItemList[position])
                                putInt("imageIdKey", imgsIdList[position])
                            }
                        })
                        Log.d(TAG, "position = $position, review = ${imgsItemList[position]}")
                    }
                })
            } // end listener
            adapter = innerImgRecyclerAdapter
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
        nearRestaurantList: ArrayList<NearRestaurantResultData>
    ) {
        dismissLoadingDialog()
        Log.d(TAG, "onGetDetailsSuccess: ${response.result}")
        Log.d(TAG, "imgsList: $imgsList")
        Log.d(TAG, "detailedInfoList: $detailedInfoList")
        Log.d(TAG, "menuImgList: $menuImgList")
        Log.d(TAG, "keyWordList: $keyWordList")
        Log.d(TAG, "reviewCountList: $reviewCountList")
        Log.d(TAG, "reviewList: $reviewList")
        Log.d(TAG, "nearRestaurantList: $nearRestaurantList")

        // ?????? ????????? ???????????????
        for (i in 0 until imgsList.size) {
            imgsItemList.add(imgsList[i].reviewImgUrl)
            imgsIdList.add(imgsList[i].imgId)
        }

        // ?????? ???????????? --->>> inspection, restaurantId, restaurantPhoneNumber ?????? ??????
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
            userLike = detailedInfoList[i].userLike
            userVisited = detailedInfoList[i].uservisited
        }

        // ?????? ?????????
        for (i in 0 until menuImgList.size) menuImgArrayList.add(menuImgList[i].restaurantMenuImgUrl)

        // ?????????
        for (i in 0 until keyWordList.size) keyWordArrayList.add(keyWordList[i].restaurantKeyWord)

        // ???????????? ?????? ????????? --->>> reviewCount ???????????? ??? ??????
        for (i in 0 until reviewCountList.size) {
            deliciousCount = reviewCountList[i].deliciousCount
            okayCount = reviewCountList[i].okayCount
            badCount = reviewCountList[i].badCount
        }

        // ??????
        for (reviewData in reviewList) reviewArrayList.add(reviewData)

        // ?????? ??????
        for (nearRestaurantData in nearRestaurantList) nearRestaurantArrayList.add(nearRestaurantData)





        // Frame??? Arguments ??????
        setArgumentsToFrame()

        // ?????? ?????????????????? ?????????
        setRecyclerAdapter()
        // ?????? ?????? ?????????
        viewHoldingData()
    }

    override fun onGetDetailsFailure(message: String) {
        showCustomToast("?????? : $message")
    }

    override fun onPatchWannaGoSuccess(response: PatchWannagoResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPatchWannaGoSuccess: ${response.message}")
        Log.d(TAG, "onPatchWannaGoSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchWannaGoSuccess: ${response.code}")

        if (response.code == 1000){
            binding.detailsImgBottomWannaGo.setColorFilter(Color.parseColor("#ff8104"))
        } else if (response.code == 1001){
            binding.detailsImgBottomWannaGo.colorFilter = null
        }

//        for (i in 0 until itemArrayList.size) {
//            if (restaurantId == itemArrayList[i].restaurantId) {
//                if (response.code == 1000) itemArrayList[i].isLike = 1
//                else if (response.code == 1001) itemArrayList[i].isLike = 0
//            }
//        }
//        HomeRecyclerAdapter(context, itemArrayList).notifyDataSetChanged()
//        Log.d(TAG, "onPatchWannaGoSuccess: ")


    }

    override fun onPatchWannaGoFailure(message: String) {
        dismissLoadingDialog()

    }

    override fun onGetDetailsImageSuccess(response: DetailsImageResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onGetDetailsImageSuccess: ${response.result}")
    }

    override fun onGetDetailsImageFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("?????? : $message")
    }

}