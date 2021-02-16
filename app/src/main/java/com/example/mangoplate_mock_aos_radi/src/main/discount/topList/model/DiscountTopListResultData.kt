package com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model

data class DiscountTopListResultData (val topListId: Int,
                                      val topListImgUrl: String,
                                      val topListView: Int,
                                      val updatedAt: String,
                                      var userBookMark: Int,
                                      val topListName: String,
                                      val topListOneLine: String,
                                      val topListSortOnTop: Int)