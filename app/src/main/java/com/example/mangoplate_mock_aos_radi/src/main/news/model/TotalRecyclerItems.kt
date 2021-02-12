package com.example.mangoplate_mock_aos_radi.src.main.news.model

data class TotalRecyclerItems (var reviewImgList: ArrayList<TotalRecyclerInnerImageItems>?,
                               var userProfileImgUrl: String,
                               var userName: String,
                               var isHolic: Int,
                               var userReviewCount: Int,
                               var userFollowerCount: Int,
                               var reviewExpression: Int,
                               var reviewReplyCount: String,
                               var reviewLikeCount: String,
                               var restaurantName: String,
                               var restaurantLocation: String,
                               var updatedAt: String,
                               var reviewContents: String,
                               var restaurantLikeStatus: Int,
                               var reviewLikeStatus: Int,
                               var expression_delicious: String,
                               var expression_good: String,
                               var expression_bad: String)