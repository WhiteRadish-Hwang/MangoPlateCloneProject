package com.example.mangoplate_mock_aos_radi.src.main.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeDetailsFrameBinding
import com.example.mangoplate_mock_aos_radi.src.main.detail.adapter.DetailsKeywordRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.detail.adapter.DetailsNearRestaurantRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.detail.adapter.DetailsReviewRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.DetailsKeywordRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.NearRestaurantResultData
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.ReviewResultData
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.DetailsReviewRecyclerItems

class HomeDetailsFrameFragment : BaseFragment<FragmentHomeDetailsFrameBinding>(FragmentHomeDetailsFrameBinding::bind, R.layout.fragment_home_details_frame) {
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
    }

    lateinit var keywordRecyclerAdapter: DetailsKeywordRecyclerAdapter
    lateinit var nearRestaurantRecyclerAdapter: DetailsNearRestaurantRecyclerAdapter
    lateinit var reviewRecyclerAdapter: DetailsReviewRecyclerAdapter

    val keywordItemList = ArrayList<DetailsKeywordRecyclerItems>()
    val reviewResultArrayList = ArrayList<ReviewResultData>()
    val reviewArray = ArrayList<DetailsReviewRecyclerItems>()
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
        for (i in reviewResultArrayListArg) {
            reviewResultArrayList.add(i as ReviewResultData)
        }

        val expression_delicious = getString(R.string.news_text_great)
        val expression_good = getString(R.string.news_text_good)
        val expression_bad = getString(R.string.news_text_bad)
        var reviewId: Int = 0
        var imgIndex = 0
        for (i in 0 until reviewResultArrayList.size) {

            val likeCountText = String.format(getString(R.string.news_total_review_like_count, reviewResultArrayList[i].reviewLikeCount))
            val replyCountText = String.format(getString(R.string.news_total_review_reply_count, reviewResultArrayList[i].reviewReplyCount))

            val item = DetailsReviewRecyclerItems(reviewImgList = reviewResultArrayList[i].reviewImgList, userProfileImgUrl = reviewResultArrayList[i].userProfileImgUrl,
                    userName = reviewResultArrayList[i].userName, isHolic = reviewResultArrayList[i].isHolic, userReviewCount = reviewResultArrayList[i].userReviewCount,
                    userFollowerCount = reviewResultArrayList[i].userFollower, reviewExpression = reviewResultArrayList[i].reviewExpression, reviewReplyCount = replyCountText,
                    reviewLikeCount = likeCountText, updatedAt = reviewResultArrayList[i].updatedAt, reviewContents = reviewResultArrayList[i].reviewContents,
                    reviewLikeStatus = reviewResultArrayList[i].userReviewLike, expression_delicious = expression_delicious, expression_good = expression_good, expression_bad = expression_bad)

            reviewArray.add(item)
        }

        // 주위 인기 식당
        val nearRestaurantListArg = arguments?.getSerializable(nearRestaurantArrayListKey) as ArrayList<*>
        for (i in nearRestaurantListArg) nearRestaurantArrayListArg.add(i as NearRestaurantResultData)


        //어댑터 설정
        setRecyclerAdapter()

        binding.fDetailsTextReviewTitle.text = String.format(getString(R.string.details_text_review_title, reviewTotalCount))
        binding.fDetailsTextReviewGreat.text = String.format(getString(R.string.details_text_great, reviewGreatCount))
        binding.fDetailsTextReviewGood.text = String.format(getString(R.string.details_text_good, reviewGoodCount))
        binding.fDetailsTextReviewBad.text = String.format(getString(R.string.details_text_bad, reviewBadCount))

        if (restaurantOpeningTime != "-1") binding.fDetailsTextOpeningTimeValue.text = restaurantOpeningTime
        else binding.fDetailsTextOpeningTimeValue.text = ""
        if (restaurantBreakTime != "-1") binding.fDetailsTextBreakTimeValue.text = restaurantBreakTime
        else binding.fDetailsTextBreakTimeValue.text = ""
        if (restaurantClosedDate != "-1") binding.fDetailsTextClosedDateValue.text = restaurantClosedDate
        else binding.fDetailsTextClosedDateValue.text = ""
        if (restaurantPriceArg != "-1") binding.fDetailsTextPriceValue.text = restaurantPriceArg
        else binding.fDetailsTextPriceValue.text = ""

        binding.fDetailsTextJuso.text = restaurantLocationArg
    }

    fun setRecyclerAdapter() {

        // 주변식당 리사이클러뷰
        nearRestaurantRecyclerAdapter = DetailsNearRestaurantRecyclerAdapter(context, nearRestaurantArrayListArg)
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
        reviewRecyclerAdapter = DetailsReviewRecyclerAdapter(context, reviewArray)
        binding.fDetailsRecyclerReview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)

            // 키워드 아이템 클릭 리스너
            reviewRecyclerAdapter.let {
                it.setMyReviewItemClickListener(object : DetailsReviewRecyclerAdapter.MyReviewItemClickListener {
                    override fun onItemClick(position: Int) {
                        Log.d(TAG, "position = $position, review = ${reviewArray[position]}")
                    }
                })
            } // end listener
            adapter = reviewRecyclerAdapter
            Log.d(TAG, "setRecyclerAdapter: $adapter")
        }

        // 키워드 리사이클러뷰
        keywordRecyclerAdapter = DetailsKeywordRecyclerAdapter(context, keywordItemList)
        binding.fDetailsRecyclerKeyword.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)

            // 키워드 아이템 클릭 리스너
            keywordRecyclerAdapter.let {
                it.setMyKeywordItemClickListener(object : DetailsKeywordRecyclerAdapter.MyKeywordItemClickListener {
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
