package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import androidx.core.text.toSpannable
import androidx.core.text.toSpanned
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fBad
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fGood
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.fGreat
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsTotalBinding
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFrameFragment.Companion.reviewListKey
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.HollicRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.model.HollicRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerInnerImageItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import kotlin.properties.Delegates

class TotalFragment : BaseFragment<FragmentNewsTotalBinding>(FragmentNewsTotalBinding::bind, R.layout.fragment_news_total){
    val itemList = ArrayList<TotalRecyclerItems>()
    val innerItemList = ArrayList<TotalRecyclerInnerImageItems>()

    var total_reviewList = ArrayList<TotalReviewResultData>()
    lateinit var total_reviewObject : TotalRecyclerItems

    var total_reviewId by Delegates.notNull<Int>()
    var total_userId by Delegates.notNull<Int>()
    lateinit var total_userName: String
    var total_isHolic by Delegates.notNull<Int>()
    lateinit var total_userProfileImgUrl: String
    var total_userReviewCount by Delegates.notNull<Int>()
    var total_userFollowerCount by Delegates.notNull<Int>()
    var total_reviewExpression by Delegates.notNull<Int>()
    lateinit var total_reviewContents: String
    lateinit var total_restaurantName: String
    lateinit var total_restaurantLocation: String
    var total_reviewLikeCount by Delegates.notNull<Int>()
    var total_reviewReplyCount by Delegates.notNull<Int>()
    lateinit var total_updatedAt: String
    lateinit var total_reviewImgList: ArrayList<String>
    var total_restaurantLikeStatus by Delegates.notNull<Int>()
    var total_reviewLikeStatus by Delegates.notNull<Int>()

    lateinit var innerItem : TotalRecyclerInnerImageItems

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "arguments in Total Fragment: $arguments")
        getNewsTotalReviewItems()
    }

    fun getNewsTotalReviewItems() {
        total_reviewList = arguments?.getSerializable(reviewListKey) as ArrayList<TotalReviewResultData>

        total_reviewList.forEach {reviewResultData ->
            total_reviewId = reviewResultData.reviewId
            total_userId = reviewResultData.userId
            total_userName = reviewResultData.userName
            total_isHolic = reviewResultData.isHolic
            total_userProfileImgUrl = reviewResultData.userProfileImgUrl
            total_userReviewCount = reviewResultData.userReviewCount
            total_userFollowerCount = reviewResultData.userFollowerCount
            total_reviewExpression = reviewResultData.reviewExpression
            total_reviewContents = reviewResultData.reviewContents
            total_restaurantName = reviewResultData.restaurantName
            total_restaurantLocation = reviewResultData.restaurantLocation
            total_reviewLikeCount = reviewResultData.reviewLikeCount
            total_reviewReplyCount = reviewResultData.reviewReplyCount
            total_updatedAt = reviewResultData.updatedAt
            total_restaurantLikeStatus = reviewResultData.restaurantLikeStatus
            total_reviewLikeStatus = reviewResultData.reviewLikeStatus
            total_reviewImgList = reviewResultData.reviewImgList

            val likeCountText = String.format(getString(R.string.news_total_review_like_count, total_reviewLikeCount))
            val replyCountText = String.format(getString(R.string.news_total_review_reply_count, total_reviewReplyCount))

            val expression_delicious = getString(R.string.news_text_great)
            val expression_good = getString(R.string.news_text_good)
            val expression_bad = getString(R.string.news_text_bad)

            val ul_restaurantName = String.format(getString(R.string.news_total_review_restaurant_name_val, total_restaurantName))
            val ul_restaurantLoc = String.format(getString(R.string.news_total_review_restaurant_loc_val, total_restaurantLocation))

            total_reviewObject = TotalRecyclerItems(reviewImgList = total_reviewImgList, userProfileImgUrl = total_userProfileImgUrl, userName = total_userName, isHolic = total_isHolic,
                userReviewCount = total_userReviewCount, userFollowerCount = total_userFollowerCount, reviewExpression = total_reviewExpression, reviewReplyCount = replyCountText,
                reviewLikeCount = likeCountText, restaurantName = ul_restaurantName, restaurantLocation = ul_restaurantLoc, updatedAt = total_updatedAt,
                reviewContents = total_reviewContents, restaurantLikeStatus = total_restaurantLikeStatus, reviewLikeStatus = total_reviewLikeStatus,
                expression_delicious = expression_delicious, expression_good = expression_good, expression_bad = expression_bad)

            itemList.add(total_reviewObject)
        }

        setRecyclerAdapter()
    }

    fun setRecyclerAdapter(){
        Log.d(TAG, "itemList: $itemList")
        binding.totalRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = TotalRecyclerAdapter(context, itemList)
        }
    }

    private fun innerInitData() {
        total_reviewImgList.forEach {
            innerItem = TotalRecyclerInnerImageItems(innerImage = it)
            innerItemList.add(innerItem)
        }
    }

//    private fun initData() {
//        for (i in 0 until 2) {
//            val item1 = TotalRecyclerItems(reviewImgList = innerItemList, userProfileImgUrl = total_userProfileImgUrl, userName = total_userName, isHolic = total_isHolic,
//            userReviewCount = total_userReviewCount, userFollowerCount = total_userFollowerCount, reviewExpression = total_reviewExpression, reviewReplyCount = total_reviewReplyCount,
//            reviewLikeCount = total_reviewLikeCount, restaurantName = total_restaurantName, restaurantLocation = total_restaurantLocation, updatedAt = total_updatedAt,
//            reviewContents = total_reviewContents, restaurantLikeStatus = total_restaurantLikeStatus, reviewLikeStatus = total_reviewLikeStatus)
////            ,filter_great = fGreat, filter_good = fGood, filter_bad = fBad
//            itemList.add(item1)
//        }
//    }

}