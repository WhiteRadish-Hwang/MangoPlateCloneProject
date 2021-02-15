package com.example.mangoplate_mock_aos_radi.src.main.detail.model

data class DetailsReviewRecyclerItems (var reviewImgList: ArrayList<String>,
                                       var userProfileImgUrl: String,
                                       var userName: String,
                                       var isHolic: Int,
                                       var userReviewCount: Int,
                                       var userFollowerCount: Int,
                                       var reviewExpression: String,
                                       var reviewReplyCount: String,
                                       var reviewLikeCount: String,
                                       var updatedAt: String,
                                       var reviewContents: String,
                                       var reviewLikeStatus: Int,
                                       var expression_delicious: String,
                                       var expression_good: String,
                                       var expression_bad: String)