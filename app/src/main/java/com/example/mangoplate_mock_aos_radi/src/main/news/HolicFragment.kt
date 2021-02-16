package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsHolicBinding
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerAdapter
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import kotlin.properties.Delegates

class HolicFragment : BaseFragment<FragmentNewsHolicBinding>(FragmentNewsHolicBinding::bind, R.layout.fragment_news_holic){
    companion object {
        const val holicListKey = "holicListKey"
    }

    val itemList = ArrayList<TotalRecyclerItems>()
    var isExpanable: Boolean = false

    var holic_reviewList = ArrayList<TotalReviewResultData>()
    lateinit var holic_reviewObject : TotalRecyclerItems

    var holic_reviewId by Delegates.notNull<Int>()
    var holic_userId by Delegates.notNull<Int>()
    lateinit var holic_userName: String
    var holic_isHolic by Delegates.notNull<Int>()
    lateinit var holic_userProfileImgUrl: String
    var holic_userReviewCount by Delegates.notNull<Int>()
    var holic_userFollowerCount by Delegates.notNull<Int>()
    var holic_reviewExpression by Delegates.notNull<Int>()
    lateinit var holic_reviewContents: String
    lateinit var holic_restaurantName: String
    lateinit var holic_restaurantLocation: String
    var holic_reviewLikeCount by Delegates.notNull<Int>()
    var holic_reviewReplyCount by Delegates.notNull<Int>()
    lateinit var holic_updatedAt: String
    lateinit var holic_reviewImgList: ArrayList<String>
    var holic_restaurantLikeStatus by Delegates.notNull<Int>()
    var holic_reviewLikeStatus by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        holic_reviewList = arguments?.getSerializable(holicListKey) as ArrayList<TotalReviewResultData>

        // 홀릭 Expanable
        binding.holicLayoutExpanable.setOnClickListener {
            isExpanable = !isExpanable
            when (isExpanable) {
                true -> {
                    binding.holicLayoutExplain.visibility = View.VISIBLE
                }
                false -> {
                    binding.holicLayoutExplain.visibility = View.GONE
                }
            }

        }

        argViewBind()
    }

    fun argViewBind() {
        holic_reviewList.forEach { reviewResultData ->
            holic_reviewId = reviewResultData.reviewId
            holic_userId = reviewResultData.userId
            holic_userName = reviewResultData.userName
            holic_isHolic = reviewResultData.isHolic
            holic_userProfileImgUrl = reviewResultData.userProfileImgUrl
            holic_userReviewCount = reviewResultData.userReviewCount
            holic_userFollowerCount = reviewResultData.userFollowerCount
            holic_reviewExpression = reviewResultData.reviewExpression
            holic_reviewContents = reviewResultData.reviewContents
            holic_restaurantName = reviewResultData.restaurantName
            holic_restaurantLocation = reviewResultData.restaurantLocation
            holic_reviewLikeCount = reviewResultData.reviewLikeCount
            holic_reviewReplyCount = reviewResultData.reviewReplyCount
            holic_updatedAt = reviewResultData.updatedAt
            holic_restaurantLikeStatus = reviewResultData.restaurantLikeStatus
            holic_reviewLikeStatus = reviewResultData.reviewLikeStatus
            holic_reviewImgList = reviewResultData.reviewImgList

            val likeCountText = String.format(getString(R.string.review_like_count, holic_reviewLikeCount))
            val replyCountText = String.format(getString(R.string.review_reply_count, holic_reviewReplyCount))

            val expression_delicious = getString(R.string.news_text_great)
            val expression_good = getString(R.string.news_text_good)
            val expression_bad = getString(R.string.news_text_bad)

            val ul_restaurantName = String.format(getString(R.string.review_restaurant_name_val, holic_restaurantName))
            val ul_restaurantLoc = String.format(getString(R.string.review_restaurant_loc_val, holic_restaurantLocation))

            holic_reviewObject = TotalRecyclerItems(reviewImgList = holic_reviewImgList, userProfileImgUrl = holic_userProfileImgUrl, userName = holic_userName, isHolic = holic_isHolic,
                    userReviewCount = holic_userReviewCount, userFollowerCount = holic_userFollowerCount, reviewExpression = holic_reviewExpression, reviewReplyCount = replyCountText,
                    reviewLikeCount = likeCountText, restaurantName = ul_restaurantName, restaurantLocation = ul_restaurantLoc, updatedAt = holic_updatedAt,
                    reviewContents = holic_reviewContents, restaurantLikeStatus = holic_restaurantLikeStatus, reviewLikeStatus = holic_reviewLikeStatus,
                    expression_delicious = expression_delicious, expression_good = expression_good, expression_bad = expression_bad)

            itemList.add(holic_reviewObject)
        }

        setRecyclerAdapter()
    }
    fun setRecyclerAdapter(){
        binding.hollicRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = TotalRecyclerAdapter(context, itemList)
        }
    }


}