package com.example.mangoplate_mock_aos_radi.src.main.review

import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResponse
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewDetailsResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewImgListResultData
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewReplyListResultData

interface ReviewDetailsFragmentView {

    fun onGetReviewDetailsSuccess(response: ReviewDetailsResponse, reviewList: ArrayList<ReviewDetailsResultData>,
                                  reviewImgList: ArrayList<ReviewImgListResultData>, replyList: ArrayList<ReviewReplyListResultData>)

    fun onGetReviewDetailsFailure(message: String)

    fun onPatchReviewWannaGoSuccess(response: BaseResponse)

    fun onPatchReviewWannaGoFailure(message: String)

    fun onPatchReviewLikeSuccess(response: BaseResponse)

    fun onPatchReviewLikeFailure(message: String)

    fun onPostReviewReplySuccess(response: BaseResponse)

    fun onPostReviewReplyFailure(message: String)
}