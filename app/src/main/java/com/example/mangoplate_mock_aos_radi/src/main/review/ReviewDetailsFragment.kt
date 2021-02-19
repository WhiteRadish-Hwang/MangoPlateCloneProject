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
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.user_id
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.databinding.FragmentReviewDetailsBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFragment
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerInnerImageAdapter
import com.example.mangoplate_mock_aos_radi.src.main.review.adapter.ReviewReplyAdapter
import com.example.mangoplate_mock_aos_radi.src.main.review.model.*
import kotlin.properties.Delegates

class ReviewDetailsFragment: BaseFragment<FragmentReviewDetailsBinding>(FragmentReviewDetailsBinding::bind, R.layout.fragment_review_details), ReviewDetailsFragmentView {
    companion object {
        const val reviewIdKey = "reviewIdKey"
        const val reviewrestaurantIdKey = "reviewrestaurantIdKey"

        var commentUserList = ArrayList<Int>()
    }

    //리뷰상세
    var reviewDetailsRestaurantId by Delegates.notNull<Int>()
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

    var reviewReplyArrayList = ArrayList<ReviewReplyListResultData>()
    lateinit var reviewReplyAdapter: ReviewReplyAdapter
    lateinit var replyItemObject: ReviewReplyResultData

    //상태 변수
    var isBottomLike: Boolean = false
    var isBottomWannaGo: Boolean = false
    var isReplyModifying: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: $arguments")
        reviewIdArg = arguments?.getInt(reviewIdKey) as Int
        executeReviewDetailsService(reviewIdArg)

        binding.reviewImgArrow.setOnClickListener {
            (activity as MainActivity).onBackPressedAndReplace(NewsFragment())
        }

        binding.reviewLayoutBottomWannaGo.setOnClickListener {
            showLoadingDialog(context!!)
            ReviewService(this).tryPatchReviewWannago(reviewDetailsRestaurantId)
        }

        binding.reviewLayoutBottomLike.setOnClickListener {
            showLoadingDialog(context!!)
            ReviewService(this).tryPatchReviewLike(reviewIdArg)
        }

        binding.reviewImgReplyApply.setOnClickListener {
            val editTextString = binding.reviewEtReply.text.toString()
            if (editTextString.isNullOrBlank()) {
                showCustomToast("메세지를 입력하세요")
            }
            else if (!isReplyModifying) {
                Log.d(TAG, "onViewCreated: ${commentUserList.size}")
                replyItemObject = ReviewReplyResultData(commentUserList = commentUserList, replyContents = editTextString)
                showLoadingDialog(context!!)
                ReviewService(this).tryPostReviewReply(reviewIdArg, replyItemObject)
                binding.reviewEtReply.setText("")
            }
        }





    }

    private fun executeReplyModifyService(reviewId: Int, replyId: Int, body: ReviewReplyResultData) {
        showLoadingDialog(context!!)
        ReviewService(this).tryPatchReplyModify(reviewId, replyId, body)
    }

    private fun executeReplyDeleteService(reviewId: Int, replyId: Int) {
        showLoadingDialog(context!!)
        ReviewService(this).tryPatchReplyDelete(reviewId, replyId)
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
                val likeCountText = String.format(getString(R.string.review_like_count, reviewLikeCount))
                binding.reviewTextLikeCount.text = likeCountText
            }
            false -> {
                binding.reviewImgBottomLike.setImageResource(R.drawable.magno_like)
                val likeCountText = String.format(getString(R.string.review_like_count, reviewLikeCount))
                binding.reviewTextLikeCount.text = likeCountText
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
        reviewReplyAdapter = ReviewReplyAdapter(context, itemList)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            // 이너 m이미지 아이템 클릭 리스너
            reviewReplyAdapter.let {
                it.setMyReplyItemClickListener(object : ReviewReplyAdapter.MyReplyItemClickListener {
                    override fun onItemClick(position: Int) {
                        if (reviewReplyArrayList[position].replyUserId == user_id?.toInt()) {
                            val replySettingFragment = ReviewReplyBottomFragment {
                                when (it) {
                                    0 -> {
                                        isReplyModifying = true
                                        // 선택 댓글 수정
                                        if (isReplyModifying) {
                                            binding.reviewEtReply.setText(reviewReplyArrayList[position].replyContents)

                                            binding.reviewImgReplyApply.setOnClickListener {
                                                if (binding.reviewEtReply.text.isNullOrBlank()) {
                                                    showCustomToast("메세지를 입력하세요")
                                                } else {
                                                    val editString = binding.reviewEtReply.text.toString()
                                                    Log.d(TAG, "onViewCreated: ${commentUserList.size}")
                                                    replyItemObject = ReviewReplyResultData(commentUserList = commentUserList, replyContents = editString)
                                                    binding.reviewEtReply.setText("")

                                                    executeReplyModifyService(reviewIdArg, reviewReplyArrayList[position].replyId, replyItemObject)
                                                }
                                            }
                                        }
                                    }
                                    1 -> {
                                        // 선택 댓글 삭제
                                        executeReplyDeleteService(reviewIdArg, reviewReplyArrayList[position].replyId)
                                    }
                                }
                            }
                            replySettingFragment.show((activity as MainActivity).supportFragmentManager, "replyOption")
                        } else {
                            val replySettingFragment = ReviewReplyBottomFragment {
                                when (it) {
                                    0 -> {
                                        Log.d(TAG, "onItemClick: 신고됨")
                                        showCustomToast("신고되었습니다")
                                    }
                                }
                            }
                            replySettingFragment.show((activity as MainActivity).supportFragmentManager, "notMyReplyOption")

                        }// end else

                    }
                })
            } // end listener
            recyclerView.adapter = reviewReplyAdapter
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
            reviewDetailsRestaurantId = reviewItem.restaurantId
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
            1000 -> {
                reviewLikeCount += 1
                isBottomLike = true
            }
            1001 -> {
                reviewLikeCount -= 1
                isBottomLike = false
            }
        }

        reviewLayoutBottomLikeViewBind()
    }

    override fun onPatchReviewLikeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPostReviewReplySuccess(response: BaseResponse, replyId: Int) {
        dismissLoadingDialog()
        Log.d(TAG, "onPostReviewReplySuccess: ${response.isSuccess}")
        Log.d(TAG, "onPostReviewReplySuccess: ${response.code}")
        Log.d(TAG, "onPostReviewReplySuccess: ${response.message}")
        Log.d(TAG, "onPostReviewReplySuccess: $replyId")

        if (response.isSuccess) {
            executeReviewDetailsService(reviewId)
            reviewReplyAdapter.notifyDataSetChanged()
        }


    }

    override fun onPostReviewReplyFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPatchReplyDeleteSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPatchReplyDeleteSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchReplyDeleteSuccess: ${response.code}")
        Log.d(TAG, "onPatchReplyDeleteSuccess: ${response.message}")

        if (response.isSuccess) {
            executeReviewDetailsService(reviewId)
            reviewReplyAdapter.notifyDataSetChanged()
        }
    }

    override fun onPatchReplyDeleteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onPatchReplyModifySuccess(response: BaseResponse) {
        dismissLoadingDialog()
        Log.d(TAG, "onPatchReplyModifySuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchReplyModifySuccess: ${response.code}")
        Log.d(TAG, "onPatchReplyModifySuccess: ${response.message}")

        if (response.isSuccess) {
            executeReviewDetailsService(reviewId)
            reviewReplyAdapter.notifyDataSetChanged()
        }
        isReplyModifying = false
    }

    override fun onPatchReplyModifyFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }


}