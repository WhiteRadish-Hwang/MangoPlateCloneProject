package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsTotalBinding
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerInnerImageItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import kotlin.properties.Delegates

class TotalFragment : BaseFragment<FragmentNewsTotalBinding>(FragmentNewsTotalBinding::bind, R.layout.fragment_news_total){
    companion object {
        const val totalListKey = "totalListKey"
    }

    val itemList = ArrayList<TotalRecyclerItems>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        total_reviewList = arguments?.getSerializable(totalListKey) as ArrayList<TotalReviewResultData>

        argViewBind()
    }

    fun argViewBind() {
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

            val likeCountText = String.format(getString(R.string.review_like_count, total_reviewLikeCount))
            val replyCountText = String.format(getString(R.string.review_reply_count, total_reviewReplyCount))

            val expression_delicious = getString(R.string.news_text_great)
            val expression_good = getString(R.string.news_text_good)
            val expression_bad = getString(R.string.news_text_bad)

            val ul_restaurantName = String.format(getString(R.string.review_restaurant_name_val, total_restaurantName))
            val ul_restaurantLoc = String.format(getString(R.string.review_restaurant_loc_val, total_restaurantLocation))

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
        binding.totalRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = TotalRecyclerAdapter(context, itemList)
        }
    }


}