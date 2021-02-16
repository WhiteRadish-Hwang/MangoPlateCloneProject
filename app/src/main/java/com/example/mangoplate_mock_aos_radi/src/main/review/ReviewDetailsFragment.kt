package com.example.mangoplate_mock_aos_radi.src.main.review

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.profileImageUrl
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.databinding.FragmentReviewDetailsBinding
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerInnerImageAdapter
import com.example.mangoplate_mock_aos_radi.src.main.review.adapter.ReviewReplyAdapter
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewImgListResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewReplyListResultData
import kotlin.properties.Delegates

class ReviewDetailsFragment: BaseFragment<FragmentReviewDetailsBinding>(FragmentReviewDetailsBinding::bind, R.layout.fragment_review_details), ReviewDetailsFragmentView {
    companion object {
        const val reviewIdKey = "reviewIdKey"
        const val reviewrestaurantIdKey = "reviewrestaurantIdKey"
    }

    //리뷰상세
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
    var restaurantLikeStatus by Delegates.notNull<Int>()
    var reviewLikeStatus by Delegates.notNull<Int>()

    var reviewIdArg by Delegates.notNull<Int>()

    //리뷰 이미지
    val reviewImgIdList = ArrayList<Int>()
    val reviewImgUrlList = ArrayList<String>()

    //리뷰 댓글
    var replyId by Delegates.notNull<Int>()
    var replyUserId by Delegates.notNull<Int>()
    lateinit var replyUserName: String
    var replyIsHolic by Delegates.notNull<Int>()
    lateinit var replyUserProfileImgUrl: String
    lateinit var replyContents: String
    lateinit var replyUpdatedAt: String
    var commentUserList = ArrayList<String>()

    var reviewReplyArrayList = ArrayList<ReviewReplyListResultData>()

    //상태 변수
    var isBottomLike: Boolean = false
    var isBottomWannaGo: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: $arguments")
        reviewIdArg = arguments?.getInt(reviewIdKey) as Int
        executeReviewDetailsService(reviewIdArg)

        binding.reviewLayoutBottomWannaGo.setOnClickListener {
//            showLoadingDialog(context!!)
//            ReviewService(this).tryPatchReviewWannago()
        }

        binding.reviewLayoutBottomLike.setOnClickListener {
            showLoadingDialog(context!!)
            ReviewService(this).tryPatchReviewLike(reviewId)
        }

    }

    private fun executeReviewDetailsService(reviewId: Int) {
        showLoadingDialog(context!!)
        ReviewService(this).tryGetReviewDtails(reviewId)
    }

    private fun reviewLayoutBottomWannaGoViewBind() {
        when (isBottomWannaGo) {
            true -> {
                binding.reviewImgBottomWannaGo.setImageResource(R.drawable.details_wanna_go_clicked)
            }
            false -> {
                binding.reviewImgBottomWannaGo.setImageResource(R.drawable.mp_wanna_go)
            }
        }
    }

    private fun reviewLayoutBottomLikeViewBind() {
        when (isBottomLike) {
            true -> {
                binding.reviewImgBottomLike.setImageResource(R.drawable.review_like_click)
            }
            false -> {
                binding.reviewImgBottomLike.setImageResource(R.drawable.magno_like)
            }
        }
    }

    private fun viewBindData() {
        Glide.with(binding.reviewImgProfile).load(userProfileImgUrl).circleCrop().placeholder(R.drawable.profile).into(binding.reviewImgProfile)
        binding.reviewTextUserName.text = userName
        binding.reviewTextUserReviewCount.text = userReviewCount.toString()
        binding.reviewTextUserFollowerCount.text = userFollowerCount.toString()

        when (reviewExpression) {
            -1 -> {
                binding.reviewImgExpression.setImageResource(R.drawable.bad_remove)
                binding.reviewTextExpression.text = getString(R.string.news_text_bad)
            }
            1 -> {
                binding.reviewImgExpression.setImageResource(R.drawable.good_remove)
                binding.reviewTextExpression.text = getString(R.string.news_text_good)
            }
            2 -> {
                binding.reviewImgExpression.setImageResource(R.drawable.great_remove)
                binding.reviewTextExpression.text = getString(R.string.news_text_great)
            }
        }

        binding.reviewTextRestaurantName.text = restaurantName
        binding.reviewTextRestaurantLoc.text = restaurantLocation
        binding.reviewTextMain.text = reviewContents
        val likeCountText = String.format(getString(R.string.review_like_count, reviewLikeCount))
        val replyCountText = String.format(getString(R.string.review_reply_count, reviewReplyCount))
        binding.reviewTextLikeCount.text = likeCountText
        binding.reviewTextComment.text = replyCountText
        binding.reviewTextUpdateAt.text = updatedAt

        when (isHolic) {
            1 -> binding.reviewImgUserHolic.visibility = View.VISIBLE
            0 -> binding.reviewImgUserHolic.visibility = View.GONE
        }

        when (restaurantLikeStatus) {
            1 -> binding.reviewImgBottomWannaGo.setImageResource(R.drawable.details_wanna_go_clicked)
            0 -> binding.reviewImgBottomWannaGo.setImageResource(R.drawable.mp_wanna_go)
        }

        when (reviewLikeStatus) {
            1 -> binding.reviewImgBottomLike.setImageResource(R.drawable.review_like_click)
            0 -> binding.reviewImgBottomLike.setImageResource(R.drawable.magno_like)
        }

        setImgRecycler(binding.reviewRecyclerImg, reviewImgUrlList)

        Glide.with(binding.reviewImgMyProfile).load(profileImageUrl).circleCrop().placeholder(R.drawable.profile).into(binding.reviewImgMyProfile)

    }

    private fun setReplyRecycler(recyclerView: RecyclerView, itemList: ArrayList<ReviewReplyListResultData>) {
        val itemsAdapter = ReviewReplyAdapter(context, itemList)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            // 이너 m이미지 아이템 클릭 리스너
            itemsAdapter.let {
                it.setMyReplyItemClickListener(object : ReviewReplyAdapter.MyReplyItemClickListener {
                    override fun onItemClick(position: Int) {

                    }
                })
            } // end listener
            recyclerView.adapter = itemsAdapter
        }

    }

    //inner 리사이클러뷰 어답터 장착
    fun setImgRecycler(recyclerView: RecyclerView, item: ArrayList<String>){
        val itemsAdapter = TotalRecyclerInnerImageAdapter(context, item)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            // 이너 m이미지 아이템 클릭 리스너
                        itemsAdapter.let {
                            it.setMyInnerImgItemClickListener(object : TotalRecyclerInnerImageAdapter.MyInnerImgItemClickListener {
                    override fun onItemClick(position: Int) {


                    }
                })
            } // end listener
            recyclerView.adapter = itemsAdapter
        }

    }

    override fun onGetReviewDetailsSuccess(response: ReviewDetailsResponse, reviewList: ArrayList<ReviewDetailsResultData>,
                                           reviewImgList: ArrayList<ReviewImgListResultData>, replyList: ArrayList<ReviewReplyListResultData>) {
        dismissLoadingDialog()

        Log.d(TAG, "onGetReviewDetailsSuccess: ${response.isSuccess}")
        Log.d(TAG, "onGetReviewDetailsSuccess: ${response.code}")
        Log.d(TAG, "onGetReviewDetailsSuccess: ${response.message}")
        Log.d(TAG, "onGetReviewDetailsSuccess: $reviewList")
        Log.d(TAG, "onGetReviewDetailsSuccess: $reviewImgList")
        Log.d(TAG, "onGetReviewDetailsSuccess: $replyList")

        reviewList.forEach { reviewItem ->
            reviewId = reviewItem.reviewId
            userId = reviewItem.userId
            userName = reviewItem.userName
            isHolic = reviewItem.isHolic
            userProfileImgUrl = reviewItem.userProfileImgUrl
            userReviewCount = reviewItem.userReviewCount
            userFollowerCount = reviewItem.userFollowerCount
            reviewExpression = reviewItem.reviewExpression
            reviewContents = reviewItem.reviewContents
            restaurantName = reviewItem.restaurantName
            restaurantLocation = reviewItem.restaurantLocation
            reviewLikeCount = reviewItem.reviewLikeCount
            reviewReplyCount = reviewItem.reviewReplyCount
            updatedAt = reviewItem.updatedAt
            restaurantLikeStatus = reviewItem.restaurantLikeStatus
            reviewLikeStatus = reviewItem.reviewLikeStatus

        }

        reviewImgList.forEach {reviewImgItem ->
            val imgId = reviewImgItem.imgId
            val imgUrl = reviewImgItem.reviewImgUrl

            reviewImgIdList.add(imgId)
            reviewImgUrlList.add(imgUrl)
        }

        reviewReplyArrayList = replyList

//        replyList.forEach {replyItem ->
//            val replyId = replyItem.replyId
//            val replyUserId = replyItem.replyUserId
//            val replyUserName = replyItem.replyUserName
//            val isHolic = replyItem.isHolic
//            val replyUserProfileImgUrl = replyItem.replyUserProfileImgUrl
//            val replyContents = replyItem.replyContents
//            val updatedAt = replyItem.updatedAt
//            commentUserList = replyItem.commentUserList
//        }


        setReplyRecycler(binding.reviewRecyclerReply, replyList)

        viewBindData()

    }

    override fun onGetReviewDetailsFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPatchReviewWannaGoSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.code}")
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.message}")

        when (response.code) {
            1000 -> isBottomWannaGo = true
            1001 -> isBottomWannaGo = false
        }

        reviewLayoutBottomWannaGoViewBind()
    }

    override fun onPatchReviewWannaGoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPatchReviewLikeSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.code}")
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.message}")

        when (response.code) {
            1000 -> isBottomLike = true
            1001 -> isBottomLike = false
        }

        reviewLayoutBottomLikeViewBind()
    }

    override fun onPatchReviewLikeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }


}