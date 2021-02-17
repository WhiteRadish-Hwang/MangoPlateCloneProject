package com.example.mangoplate_mock_aos_radi.src.main.discount.eatDeal.model

import java.io.Serializable

data class EatDealDetailsImagesItems (
        val imgId: Int,
        val eatDealImgUrl: String
): Serializable

data class EatDealDetailsInfoItems (
        val message: String,
        val eatDealName: String,
        val restaurantId: Int,
        val eatDealOneLine: String,
        val eatDealTerm: String,
        val eatDealBeforePrice: Int,
        val eatDealAfterPrice: Int,
        val eatDealDiscount: Int,
        val eatDealPickUpPossible: Int,
        val restaurantInfo: String,
        val menuInfo: String,
        val noticeInfo: String,
        val howToUseInfo: String,
        val refundPolicyInfo: String
): Serializable