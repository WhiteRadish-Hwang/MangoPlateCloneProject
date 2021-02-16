package com.example.mangoplate_mock_aos_radi.src.main.discount.model

data class EatDealResultData (val eatDealId: Int,
                              val firstImageUrl: String,
                              val eatDealDiscount: Int,
                              val eatDealBeforePrice: Int,
                              val eatDealAfterPrice: Int, 
                              val eatDealName: String,
                              val eatDealOneLine: String,
                              val eatDealPickUpPossible: Int)