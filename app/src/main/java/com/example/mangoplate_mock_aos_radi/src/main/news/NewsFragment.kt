package com.example.mangoplate_mock_aos_radi.src.main.news

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.isGetNewsReviewItem
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFrameFragment.Companion.reviewListKey
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import kotlin.properties.Delegates

class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::bind, R.layout.fragment_news), NewsFragmentView{
    val reveiwArrayList = ArrayList<TotalReviewResultData>()

    var reviewId by Delegates.notNull<Int>()
    var userId by Delegates.notNull<Int>()
    lateinit var userName: String
    var isHolic by Delegates.notNull<Int>()
    lateinit var userProfileImgUrl: String
    var userReviewCount by Delegates.notNull<Int>()
    var userFollowerCount by Delegates.notNull<Int>()
    var reviewExpression by Delegates.notNull<Int>()
    lateinit var reviewContents: String
    lateinit var restaurantName: String
    lateinit var restaurantLocation: String
    var reviewLikeCount by Delegates.notNull<Int>()
    var reviewReplyCount by Delegates.notNull<Int>()
    lateinit var updatedAt: String
    lateinit var reviewImgList: ArrayList<String>
    var restaurantLikeStatus by Delegates.notNull<Int>()
    var reviewLikeStatus by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NewsService(this).tryGetRestaurants(page = 0, limit = 10)

        if (isGetNewsReviewItem) {


            isGetNewsReviewItem = !isGetNewsReviewItem
        }

    }

    fun setArgmentsToTotalFrame(reviewList: ArrayList<TotalReviewResultData>) {
        val fmbt = (activity as MainActivity).supportFragmentManager.beginTransaction()
        fmbt.replace(R.id.news_layout_frame, NewsFrameFragment().apply {
            arguments = Bundle().apply {
                putSerializable(reviewListKey, reviewList)
//                putInt(reviewIdKey, reviewId)
//                putInt(userIdKey, userId)
//                putString(userNameKey, userName)
//                putInt(isHolicKey, isHolic)
//                putString(userProfileImgUrlKey, userProfileImgUrl)
//                putInt(userReviewCountKey, userReviewCount)
//                putInt(userFollowerCountKey, userFollowerCount)
//                putInt(reviewExpressionKey, reviewExpression)
//                putString(reviewContentsKey, reviewContents)
//                putString(restaurantNameKey, restaurantName)
//                putString(news_restaurantLocationKey, restaurantLocation)
//                putInt(reviewLikeCountKey, reviewLikeCount)
//                putInt(reviewReplyCountKey, reviewReplyCount)
//                putString(updatedAtKey, updatedAt)
//                putInt(restaurantLikeStatusKey, restaurantLikeStatus)
//                putInt(reviewLikeStatusKey, reviewLikeStatus)
//                putSerializable(reviewImgListKey, reviewImgList)
            }
            Log.d(ApplicationClass.TAG, "arguments: $arguments")
        }).commit()
    }

    override fun onGetTotalReviewSuccess(response: NewsResponse, reviewList: ArrayList<TotalReviewResultData>) {
        Log.d(ApplicationClass.TAG, "onGetTotalReviewSuccess: $reviewList")

//        for (i in 0 until reviewList.size) {
//            reviewId = reviewList[i].reviewId
//            userId = reviewList[i].userId
//            userName = reviewList[i].userName
//            isHolic = reviewList[i].isHolic
//            userProfileImgUrl = reviewList[i].userProfileImgUrl
//            userReviewCount = reviewList[i].userReviewCount
//            userFollowerCount = reviewList[i].userFollowerCount
//            reviewExpression = reviewList[i].reviewExpression
//            reviewContents = reviewList[i].reviewContents
//            restaurantName = reviewList[i].restaurantName
//            restaurantLocation = reviewList[i].restaurantLocation
//            reviewLikeCount = reviewList[i].reviewLikeCount
//            reviewReplyCount = reviewList[i].reviewReplyCount
//            updatedAt = reviewList[i].updatedAt
//            reviewImgList = reviewList[i].reviewImgList
//            restaurantLikeStatus = reviewList[i].restaurantLikeStatus
//            reviewLikeStatus = reviewList[i].reviewLikeStatus
//        }
        setArgmentsToTotalFrame(reviewList)

    }

    override fun onGetTotalReviewFailure(message: String) {

    }

}