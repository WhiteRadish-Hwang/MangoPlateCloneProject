package com.example.mangoplate_mock_aos_radi.src.main.home.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeRestaurantDetailsFrameBinding
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.adapter.DetailsKeywordRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.adapter.DetailsNearRestaurantRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.DetailsKeywordRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.NearRestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.ReviewResultData

class HomeDetailsFrameFragment : BaseFragment<FragmentHomeRestaurantDetailsFrameBinding>(FragmentHomeRestaurantDetailsFrameBinding::bind, R.layout.fragment_home_restaurant_details_frame) {
    companion object {
        const val keywordItemKey = "keywordItemKey"
        const val reviewTitleItemKey = "reviewTitleItemKey"
        const val reviewGreatItemKey = "reviewGreatItemKey"
        const val reviewGoodItemKey = "reviewGoodItemKey"
        const val reviewBadItemKey = "reviewBadItemKey"
        const val restaurantOpeningTimeKey = "restaurantOpeningTime"
        const val restaurantBreakTimeKey = "restaurantBreakTime"
        const val restaurantClosedDateKey = "restaurantClosedDate"
        const val restaurantPriceKey = "restaurantPrice"
        const val restaurantLocationKey = "restaurantLocationKey"
        const val reviewResultArrayListKey = "reviewResultArrayListKey"
        const val nearRestaurantArrayListKey = "nearRestaurantArrayListKey"
        const val areaNameKey = "areaNameKey"
    }

    lateinit var keywordRecyclerAdapter: DetailsKeywordRecyclerAdapter
    lateinit var nearRestaurantRecyclerAdapter: DetailsNearRestaurantRecyclerAdapter
    lateinit var reviewRecyclerAdapter: DetailsKeywordRecyclerAdapter

    val keywordItemList = ArrayList<DetailsKeywordRecyclerItems>()
    val reviewResultArrayList = ArrayList<ReviewResultData>()
    val nearRestaurantArrayListArg = ArrayList<NearRestaurantResultData>()

    var reviewTotalCount:Int? = 0
    var reviewGreatCount:Int? = 0
    var reviewGoodCount:Int? = 0
    var reviewBadCount:Int? = 0

    var restaurantOpeningTime: String? = ""
    var restaurantBreakTime: String? = ""
    var restaurantClosedDate: String? = ""
    var restaurantPriceArg: String? = ""
    var restaurantLocationArg: String? = ""
    var restaurantAreaName: String? = ""

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 키워드
        val keywordItemListArgument = arguments?.getStringArrayList(keywordItemKey)
        var keywordEach: DetailsKeywordRecyclerItems
        if (keywordItemListArgument != null) {
            for (i in 0 until keywordItemListArgument.size) {
                keywordEach = DetailsKeywordRecyclerItems(keyword = keywordItemListArgument[i])
                keywordItemList.add(keywordEach)
            }
        }

        // 식당 영업 정보
        restaurantOpeningTime = arguments?.getString(restaurantOpeningTimeKey)
        restaurantBreakTime = arguments?.getString(restaurantBreakTimeKey)
        restaurantClosedDate = arguments?.getString(restaurantClosedDateKey)
        restaurantPriceArg = arguments?.getString(restaurantPriceKey)

        // 식당 주소
        restaurantLocationArg = arguments?.getString(restaurantLocationKey)

        // 리뷰 카운트 개수
        reviewTotalCount = arguments?.getInt(reviewTitleItemKey)
        reviewGreatCount = arguments?.getInt(reviewGreatItemKey)
        reviewGoodCount = arguments?.getInt(reviewGoodItemKey)
        reviewBadCount = arguments?.getInt(reviewBadItemKey)

        //사용자 리뷰
        val reviewResultArrayListArg = arguments?.getSerializable(reviewResultArrayListKey) as ArrayList<*>
        for (i in reviewResultArrayListArg) reviewResultArrayList.add(i as ReviewResultData)

        // 주위 인기 식당
        val nearRestaurantListArg = arguments?.getSerializable(nearRestaurantArrayListKey) as ArrayList<*>
        for (i in nearRestaurantListArg) nearRestaurantArrayListArg.add(i as NearRestaurantResultData)

        // 지역 이름
        restaurantAreaName = arguments?.getString(areaNameKey)


        //어댑터 설정
        setRecyclerAdapter()

        binding.fDetailsTextReviewTitle.text = String.format(getString(R.string.details_text_review_title, reviewTotalCount))
        binding.fDetailsTextReviewGreat.text = String.format(getString(R.string.details_text_great, reviewGreatCount))
        binding.fDetailsTextReviewGood.text = String.format(getString(R.string.details_text_good, reviewGoodCount))
        binding.fDetailsTextReviewBad.text = String.format(getString(R.string.details_text_bad, reviewBadCount))

        binding.fDetailsTextOpeningTimeValue.text = restaurantOpeningTime
        binding.fDetailsTextBreakTimeValue.text = restaurantBreakTime
        binding.fDetailsTextClosedDateValue.text = restaurantClosedDate
        binding.fDetailsTextPriceValue.text = restaurantPriceArg

        binding.fDetailsTextJuso.text = restaurantLocationArg





    }

    fun setRecyclerAdapter() {

        // 주변식당 리사이클러뷰
        restaurantAreaName.let { nearRestaurantRecyclerAdapter = DetailsNearRestaurantRecyclerAdapter(context, nearRestaurantArrayListArg, restaurantAreaName!!) }
        binding.fDetailsRecyclerNearRestaurant.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            // 클릭 리스너
            nearRestaurantRecyclerAdapter.let {
                it.setMyItemClickListener(object : DetailsNearRestaurantRecyclerAdapter.MyItemClickListener {
                    override fun onItemClick(position: Int) {
                        Log.d(TAG, "position = $position, keyword = ${nearRestaurantArrayListArg[position]}")
                        showCustomToast("position = $position, keyword = ${nearRestaurantArrayListArg[position]}")
                    }
                })
            }
            adapter = nearRestaurantRecyclerAdapter
        }


        // 리뷰 리사이클러뷰


        // 키워드 리사이클러뷰
        keywordRecyclerAdapter = DetailsKeywordRecyclerAdapter(context, keywordItemList)
        binding.fDetailsRecyclerKeyword.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)

            // 키워드 아이템 클릭 리스너
            keywordRecyclerAdapter.let {
                it.setMyKeywordItemClickListener(object :
                    DetailsKeywordRecyclerAdapter.MyKeywordItemClickListener {
                    override fun onItemClick(position: Int) {
                        Log.d(TAG, "position = $position, keyword = ${keywordItemList[position]}")
                        showCustomToast("position = $position, keyword = ${keywordItemList[position]}")
                    }
                })
            } // end listener

            adapter = keywordRecyclerAdapter
        }



    } // end setAdapter

}
