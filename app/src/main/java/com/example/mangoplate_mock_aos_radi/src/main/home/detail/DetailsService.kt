package com.example.mangoplate_mock_aos_radi.src.main.home.detail

import android.util.Log
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsService (val view: HomeDetailsFragmentView) {


    fun tryGetRestaurants(restaurantId: Int){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(DetailsRetrofitInterface::class.java)
        homeRetrofitInterface.getDetails(restaurantId = restaurantId).enqueue(object : Callback<DetailsResponse> {
            override fun onResponse(call: Call<DetailsResponse>, response: Response<DetailsResponse>) {
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            Log.d(TAG, "onResponse: ${response.body()}")

                            // 이너리스트 이미지
                            val imgsArrayList = ArrayList<ImgsResultData>()
                            val imgs = it.result.getAsJsonArray("imgs")

                            imgs.forEach {imgsItem ->
                                val imgsItemObject = imgsItem.asJsonObject
                                val  imgId = imgsItemObject.get("imgId").asInt
                                val  reviewImgUrl = imgsItemObject.get("reviewImgUrl").asString

                                val imgsListItem = ImgsResultData(imgId = imgId, reviewImgUrl = reviewImgUrl)

                                imgsArrayList.add(imgsListItem)
                            }

                            // 가게 상세정보
                            val detailedInfoArray = ArrayList<DetailedInfoResultData>()
                            val detailedInfo = it.result.getAsJsonArray("detailedInfo")

                            detailedInfo.forEach {detailedInfoItem ->
                                val detailedItemObject = detailedInfoItem.asJsonObject
                                val inspection = detailedItemObject.get("inspection").asInt
                                val restaurantId = detailedItemObject.get("restaurantId").asInt
                                val restaurantName = detailedItemObject.get("restaurantName").asString
                                val star = detailedItemObject.get("star").asString
                                val restaurantView = detailedItemObject.get("restaurantView").asInt
                                val likeCount = detailedItemObject.get("likeCount").asInt
                                val reviewCount = detailedItemObject.get("reviewCount").asInt
                                val userLike = detailedItemObject.get("userLike").asInt
                                val uservisited = detailedItemObject.get("uservisited").asInt
                                val restaurantInfo = detailedItemObject.get("restaurantInfo").asString
                                val restaurantLocation = detailedItemObject.get("restaurantLocation").asString
                                val restaurantLatitude = detailedItemObject.get("restaurantLatitude").asString
                                val restaurantLongitude = detailedItemObject.get("restaurantLongitude").asString
                                val restaurantPhoneNumber = detailedItemObject.get("restaurantPhoneNumber").asString
                                val restaurantTime = detailedItemObject.get("restaurantTime").asString
                                val restaurantHoliday = detailedItemObject.get("restaurantHoliday").asString
                                val restaurantRestTime = detailedItemObject.get("restaurantRestTime").asString
                                val restaurantPrice = detailedItemObject.get("restaurantPrice").asString
                                val restaurantMenu = detailedItemObject.get("restaurantMenu").asString

                                val detailedListItem = DetailedInfoResultData(inspection = inspection, restaurantId = restaurantId, restaurantName = restaurantName,
                                    star = star, restaurantView = restaurantView, likeCount = likeCount, reviewCount = reviewCount, userLike = userLike, uservisited = uservisited,
                                    restaurantInfo = restaurantInfo, restaurantLocation = restaurantLocation, restaurantLatitude = restaurantLatitude, restaurantLongitude = restaurantLongitude,
                                    restaurantPhoneNumber = restaurantPhoneNumber, restaurantTime = restaurantTime, restaurantHoliday = restaurantHoliday, restaurantRestTime = restaurantRestTime,
                                    restaurantPrice = restaurantPrice, restaurantMenu = restaurantMenu)

                                detailedInfoArray.add(detailedListItem)
                            }

                            // 메뉴판 이미지
                            val menuImgArray = ArrayList<MenuImgResultData>()
                            val menuImg = it.result.getAsJsonArray("menuImg")

                            menuImg.forEach { menuImgItem ->
                                val menuItemObject = menuImgItem.asJsonObject
                                val restaurantMenuImgUrl = menuItemObject.get("restaurantMenuImgUrl").asString

                                val menuListItem = MenuImgResultData(restaurantMenuImgUrl = restaurantMenuImgUrl)

                                menuImgArray.add(menuListItem)
                            }

                            // 키워드
                            val keywordArray = ArrayList<KeyWordResultData>()
                            val keyWord = it.result.getAsJsonArray("keyWord")

                            keyWord.forEach { keyWordItem ->
                                val keywordItemObject = keyWordItem.asJsonObject
                                val restaurantKeyWord = keywordItemObject.get("restaurantKeyWord").asString

                                val keyWordListItem = KeyWordResultData(restaurantKeyWord = restaurantKeyWord)

                                keywordArray.add(keyWordListItem)
                            }

                            // 리뷰카운트
                            val reviewCountArray = ArrayList<ReviewCountResultData>()
                            val reviewCount = it.result.getAsJsonArray("reviewCount")

                            reviewCount.forEach { reviewCountItem ->
                                val reviewCountItemObject = reviewCountItem.asJsonObject
                                val reviewCount = reviewCountItemObject.get("reviewCount").asInt
                                val deliciousCount = reviewCountItemObject.get("deliciousCount").asInt
                                val okayCount = reviewCountItemObject.get("okayCount").asInt
                                val badCount = reviewCountItemObject.get("badCount").asInt

                                val reviewCountListItem = ReviewCountResultData(reviewCount = reviewCount, deliciousCount = deliciousCount, okayCount = okayCount, badCount = badCount)

                                reviewCountArray.add(reviewCountListItem)
                            }

                            // 리뷰 (내용)
                            val reviewArray = ArrayList<ReviewResultData>()
                            val review = it.result.getAsJsonArray("review")

                            review.forEach { reviewItem ->
                                val reviewItemObject = reviewItem.asJsonObject
                                val reviewId = reviewItemObject.get("reviewId").asInt
                                val userProfileImgUrl = reviewItemObject.get("userProfileImgUrl").asString
                                val userName = reviewItemObject.get("userName").asString
                                val isHolic = reviewItemObject.get("isHolic").asInt
                                val reviewExpression = reviewItemObject.get("reviewExpression").asString
                                val userReviewCount = reviewItemObject.get("userReviewCount").asInt
                                val userFollower = reviewItemObject.get("userFollower").asInt
                                val reviewContents = reviewItemObject.get("reviewContents").asString
                                val reviewLikeCount = reviewItemObject.get("reviewLikeCount").asInt
                                val reviewReplyCount = reviewItemObject.get("reviewReplyCount").asInt
                                val userReviewLike = reviewItemObject.get("userReviewLike").asInt
                                val updatedAt = reviewItemObject.get("updatedAt").asString

                                val reviewListItem = ReviewResultData(reviewId = reviewId, userProfileImgUrl = userProfileImgUrl, userName = userName, isHolic = isHolic,
                                    reviewExpression = reviewExpression, userReviewCount = userReviewCount, userFollower = userFollower, reviewContents = reviewContents,
                                    reviewLikeCount = reviewLikeCount, reviewReplyCount = reviewReplyCount, userReviewLike = userReviewLike, updatedAt = updatedAt)

                                reviewArray.add(reviewListItem)
                            }

                            // 리뷰이미지
                            val reviewImgArray = ArrayList<ReviewImgResultData>()
                            val reviewImg = it.result.getAsJsonArray("reviewImg")

                            reviewImg.forEach { reviewImgItem ->
                                val reviewImgItemObject = reviewImgItem.asJsonObject
                                val reviewId = reviewImgItemObject.get("reviewId").asInt
                                val imgId = reviewImgItemObject.get("imgId").asInt
                                val reviewImgUrl = reviewImgItemObject.get("reviewImgUrl").asString

                                val reviewImgListItem = ReviewImgResultData(reviewId = reviewId, imgId = imgId, reviewImgUrl = reviewImgUrl)

                                reviewImgArray.add(reviewImgListItem)
                            }

                            // 근처 식당
                            val nearRestaurantArray = ArrayList<NearRestaurantResultData>()
                            val nearRestaurant = it.result.getAsJsonArray("nearRestaurant")

                            nearRestaurant.forEach { nearRestaurantItem ->
                                val nearRestaurantItemObject = nearRestaurantItem.asJsonObject
                                val restaurantId = nearRestaurantItemObject.get("restaurantId").asInt
                                val restaurantName = nearRestaurantItemObject.get("restaurantName").asString
                                val restaurantView = nearRestaurantItemObject.get("restaurantView").asInt
                                val reviewCount = nearRestaurantItemObject.get("reviewCount").asInt
                                val isLike = nearRestaurantItemObject.get("isLike").asInt
                                val visited = nearRestaurantItemObject.get("visited").asInt
                                val star = nearRestaurantItemObject.get("star").asString
                                val firstImageUrl = nearRestaurantItemObject.get("firstImageUrl").asString

                                val nearRestaurantListItem = NearRestaurantResultData(restaurantId = restaurantId, restaurantName = restaurantName, restaurantView = restaurantView,
                                    reviewCount = reviewCount, isLike = isLike, visited = visited, star = star, firstImageUrl = firstImageUrl)

                                nearRestaurantArray.add(nearRestaurantListItem)
                            }

                            // 지역
                            val areaArray = ArrayList<AreaResultData>()
                            val area = it.result.getAsJsonArray("area")

                            area.forEach { areaItem ->
                                val areaItemObject = areaItem.asJsonObject
                                val areaName = areaItemObject.get("areaName").asString

                                val areaListItem = AreaResultData(areaName = areaName)
                                areaArray.add(areaListItem)
                            }

                            view.onGetDetailsSuccess(response.body()!!, imgsList = imgsArrayList, detailedInfoList = detailedInfoArray, menuImgList = menuImgArray, keyWordList = keywordArray,
                                reviewCountList = reviewCountArray, reviewList = reviewArray, reviewImgList = reviewImgArray, nearRestaurantList = nearRestaurantArray, areaResultList = areaArray)

                        } // end body

                    } // end 200
                } // end when

            } // end onResponse

            override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
                view.onGetDetailsFailure(t.message ?: "통신 오류")
            }
        })
    }
}