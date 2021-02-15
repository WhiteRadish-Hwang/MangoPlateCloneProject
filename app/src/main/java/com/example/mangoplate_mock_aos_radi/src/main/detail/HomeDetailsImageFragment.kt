package com.example.mangoplate_mock_aos_radi.src.main.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeDetailsInnerImgBinding
import com.example.mangoplate_mock_aos_radi.src.main.MainActivity
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.*
import com.example.mangoplate_mock_aos_radi.src.main.home.model.PatchWannagoResponse
import kotlin.properties.Delegates

class HomeDetailsImageFragment: BaseFragment<FragmentHomeDetailsInnerImgBinding>(FragmentHomeDetailsInnerImgBinding::bind, R.layout.fragment_home_details_inner_img), HomeDetailsFragmentView {

    lateinit var inimgUserProfileImgUrl: String
    lateinit var inimgRestaurantName: String
    lateinit var inimgUserName: String
    lateinit var inimgReviewImgUrl: String
    lateinit var inimgReviewContents: String
    lateinit var inimgUpdatedAt: String
    var inimgUserReviewCount by Delegates.notNull<Int>()
    var inimgUserFollower by Delegates.notNull<Int>()
    var inimgUserReviewLike by Delegates.notNull<Int>()
    var inimgIsHolic by Delegates.notNull<Int>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageId = arguments?.getInt("imageIdKey")
        val image = arguments?.getString("imageItemKey")

        DetailsService(this).tryGetDetailsImage(imageId!!)

        binding.inImgImgArrowLeft.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        // 서비스 추가예정
        binding.inimgImgLike.setOnClickListener {
            Log.d(TAG, "onViewCreated: $inimgUserReviewLike")
            when (inimgUserReviewLike) {
                1 -> {
                    binding.inimgImgLike.setColorFilter(R.color.cliked_color)
                }
                0 -> {
                    binding.inimgImgLike.colorFilter = null
                }
            }
        }


    }

    fun viewBindData() {
        Glide.with(binding.inimgImgUserProfileImg).load(inimgUserProfileImgUrl).circleCrop().placeholder(R.drawable.profile).into(binding.inimgImgUserProfileImg)
        inimgReviewImgUrl.let {imgUrl -> Glide.with(binding.inimgImgMainImage).load(imgUrl).into(binding.inimgImgMainImage) }

        when (inimgIsHolic) {
            1 -> {
                binding.inimgImgUserHolic.visibility = View.VISIBLE
            }
            0 -> {
                binding.inimgImgUserHolic.visibility = View.GONE
            }
        }

        binding.inimgTextResName.text = inimgRestaurantName
        binding.inimgTextUserName.text = inimgUserName
        binding.inimgTextUserFollowerCount.text = inimgUserFollower.toString()
        binding.inimgTextUserReviewCount.text = inimgUserReviewCount.toString()
        binding.inimgTextUpdateAt.text = inimgUpdatedAt

        var tenText: String = ""
        var restText: String = ""
        Log.d(TAG, "inimgReviewContents.length: ${inimgReviewContents.length}")
        if (inimgReviewContents.length > 20) {
            Log.d(TAG, "viewBindData: tr")
            binding.inimgTextMoreView.visibility = View.VISIBLE
            for (i in 0 until 10) tenText += inimgReviewContents[i]
            for (i in 10 until inimgReviewContents.length) restText += inimgReviewContents[i]
            binding.inimgTextReviewContent.text = tenText
            binding.inimgTextMoreText.text = restText
        } else {
            Log.d(TAG, "viewBindData: non")
            binding.inimgTextMoreText.visibility = View.GONE
            binding.inimgTextMoreView.visibility = View.GONE

            binding.inimgTextReviewContent.text = inimgReviewContents
        }

        var isMoreText: Boolean = false
        binding.inimgTextMoreView.setOnClickListener {
            isMoreText = !isMoreText
            if (!isMoreText) {
                binding.inimgTextMoreText.visibility = View.VISIBLE
                binding.inimgTextMoreView.setText(R.string.more_view_non)
            }
            else {
                binding.inimgTextMoreText.visibility = View.GONE
                binding.inimgTextMoreView.setText(R.string.more_view)
            }
        }



    }

    override fun onGetDetailsSuccess(response: DetailsResponse, imgsList: ArrayList<ImgsResultData>, detailedInfoList: ArrayList<DetailedInfoResultData>, menuImgList: ArrayList<MenuImgResultData>, keyWordList: ArrayList<KeyWordResultData>, reviewCountList: ArrayList<ReviewCountResultData>, reviewList: ArrayList<ReviewResultData>, nearRestaurantList: ArrayList<NearRestaurantResultData>) {

    }

    override fun onGetDetailsFailure(message: String) {

    }

    override fun onPatchWannaGoSuccess(response: PatchWannagoResponse) {

    }

    override fun onPatchWannaGoFailure(message: String) {

    }

    override fun onGetDetailsImageSuccess(response: DetailsImageResponse) {
        Log.d(TAG, "onGetDetailsImageSuccess: ${response.result}")

        val result = response.result

        result.forEach {imgItems ->
            val itemObject = imgItems.asJsonObject

            inimgUserProfileImgUrl = itemObject.get("userProfileImgUrl").asString
            inimgRestaurantName = itemObject.get("restaurantName").asString
            inimgUserName = itemObject.get("userName").asString
            inimgReviewImgUrl = itemObject.get("reviewImgUrl").asString
            inimgReviewContents = itemObject.get("reviewContents").asString
            inimgUpdatedAt = itemObject.get("updatedAt").asString
            inimgUserReviewCount = itemObject.get("userReviewCount").asInt
            inimgUserFollower = itemObject.get("userFollower").asInt
            inimgUserReviewLike = itemObject.get("userReviewLike").asInt
            inimgIsHolic = itemObject.get("isHolic").asInt
        }

        viewBindData()
    }

    override fun onGetDetailsImageFailure(message: String) {

    }
}