package com.example.mangoplate_mock_aos_radi.src.main.news.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeService
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsFragmentView
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsService
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsStatusFragmentView
import com.example.mangoplate_mock_aos_radi.src.main.news.NewsStatusService
import com.example.mangoplate_mock_aos_radi.src.main.news.model.NewsResponse
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalReviewResultData
import java.lang.Exception

class TotalRecyclerAdapter(val context: Context?, var itemList: ArrayList<TotalRecyclerItems>): RecyclerView.Adapter<TotalRecyclerAdapter.TotalViewHolder>(), NewsStatusFragmentView {
    interface MyReviewItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyReviewItemClickListener

    fun setMyReviewClickListener(itemClickListener: MyReviewItemClickListener){
        mItemClickListener = itemClickListener
    }

    var isTotalItemLike: Boolean = false
    var isTotalLikeServiceDone: Boolean = false
    var isTotalItemWannaGo: Boolean = false
    var isTotalWannaGoServiceDone: Boolean = false



    inner class TotalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val totalImgProfile: ImageView = itemView.findViewById(R.id.total_img_profile)
        val totalTextUserName: TextView = itemView.findViewById(R.id.total_text_user_name)
        val totalTextUserReviewCount: TextView = itemView.findViewById(R.id.total_text_user_review_count)
        val totalTextUserFollowerCount: TextView = itemView.findViewById(R.id.total_text_user_follower_count)
        val totalTextRetaurantName: TextView = itemView.findViewById(R.id.total_text_restaurant_name)
        val totalTextRetaurantLoc: TextView = itemView.findViewById(R.id.total_text_restaurant_loc)
        val totalTextMainContent: TextView = itemView.findViewById(R.id.total_text_main)
        val totalTextLikeCount: TextView = itemView.findViewById(R.id.total_text_like_count)
        val totalTextCommnetCount: TextView = itemView.findViewById(R.id.total_text_comment)
        val totalTextUpdateAt: TextView = itemView.findViewById(R.id.total_text_updateAt)
        val totalInnerRecycler: RecyclerView = itemView.findViewById(R.id.total_recycler_img)
        val totalImgExpression: ImageView = itemView.findViewById(R.id.total_img_expression)
        val totalTextExpression: TextView = itemView.findViewById(R.id.total_text_expression)
        val totalImgHolic: ImageView = itemView.findViewById(R.id.total_img_user_holic)
        val totalImgBottomWannaGo: ImageView = itemView.findViewById(R.id.total_img_bottom_wanna_go)
        val totalImgBottomLike: ImageView = itemView.findViewById(R.id.total_img_bottom_like)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_total_recycler_item, parent, false)
        return TotalViewHolder(view)
    }

    override fun onBindViewHolder(holder: TotalViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.totalImgProfile).load(items.userProfileImgUrl).circleCrop().placeholder(R.drawable.profile).into(holder.totalImgProfile)
        holder.totalTextUserName.text = items.userName
        holder.totalTextUserReviewCount.text = items.userReviewCount.toString()
        holder.totalTextUserFollowerCount.text = items.userFollowerCount.toString()

        holder.totalTextRetaurantName.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        holder.totalTextRetaurantLoc.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        holder.totalTextRetaurantName.text = items.restaurantName
        holder.totalTextRetaurantLoc.text = items.restaurantLocation
        holder.totalTextMainContent.text = items.reviewContents

        // 버튼 클릭 시 1씩 증가하게 변화를 줘야함
        holder.totalTextLikeCount.text = items.reviewLikeCount.toString()
        holder.totalTextCommnetCount.text = items.reviewReplyCount.toString()

        holder.totalTextLikeCount.text = items.reviewLikeCount
        holder.totalTextCommnetCount.text = items.reviewReplyCount
        holder.totalTextUpdateAt.text = items.updatedAt

        when (items.reviewExpression) {
            -1 -> {
                holder.totalImgExpression.setImageResource(R.drawable.bad_remove)
                holder.totalTextExpression.text = items.expression_bad
            }
            1 -> {
                holder.totalImgExpression.setImageResource(R.drawable.good_remove)
                holder.totalTextExpression.text = items.expression_good
            }
            2 -> {
                holder.totalImgExpression.setImageResource(R.drawable.great_remove)
                holder.totalTextExpression.text = items.expression_delicious
            }
        }

        setItemsRecycler(holder.totalInnerRecycler, items.reviewImgList)



        //메인스레드 이용해서 클릭시 이미지변하게 할 것, 홈리사이클러뷰 Like부분 참고
        if (items.isHolic == 1) holder.totalImgHolic.visibility = View.VISIBLE
        else holder.totalImgHolic.visibility = View.GONE

        if (items.restaurantLikeStatus == 1) holder.totalImgBottomWannaGo.setImageResource(R.drawable.details_wanna_go_clicked)
        else holder.totalImgBottomWannaGo.setImageResource(R.drawable.mp_wanna_go)

        if (items.reviewLikeStatus == 1) holder.totalImgBottomLike.setImageResource(R.drawable.review_like_click)
        else holder.totalImgBottomLike.setImageResource(R.drawable.magno_like)


        holder.totalImgBottomWannaGo.setOnClickListener {
            // 가고싶다 추가, 해제 서비스 호출
            NewsStatusService(this).tryPatchReviewWannago(items.restaurantId)

            Thread {
                Thread.sleep(300)
                try {
                    if (isTotalWannaGoServiceDone) {
                        checkWannaGoStatus(items)
                        Log.d(TAG, "onBindViewHolder: isLike = ${items.restaurantLikeStatus}")
                        when (items.restaurantLikeStatus) {
                            1 -> {
                                Handler(Looper.getMainLooper()).post {
                                    holder.totalImgBottomWannaGo.setImageResource(R.drawable.details_wanna_go_clicked)
                                    notifyDataSetChanged()

                                }
                            }
                            0 -> {
                                Handler(Looper.getMainLooper()).post {
                                    holder.totalImgBottomWannaGo.setImageResource(R.drawable.mp_wanna_go)
                                    notifyDataSetChanged()
                                }
                            }
                        }

                    }
                    Thread.interrupted()
                } catch (e: Exception) {
                    Log.d(TAG, "Thread: Done")
                    isTotalWannaGoServiceDone = false
                }
            }.start()
        }

        holder.totalImgBottomLike.setOnClickListener {
            NewsStatusService(this).tryPatchReviewLike(items.reviewId)
            Thread {
                Thread.sleep(300)
                try {
                    if (isTotalLikeServiceDone) {
                        checkLikeStatus(items)
                        Log.d(TAG, "onBindViewHolder: isLike = ${items.restaurantLikeStatus}")
                        when (items.reviewLikeStatus) {
                            1 -> {
                                Handler(Looper.getMainLooper()).post {
                                    holder.totalImgBottomLike.setImageResource(R.drawable.review_like_click)
//                                    holder.totalTextLikeCount.text = items.reviewLikeCount
                                    notifyDataSetChanged()

                                }
                            }
                            0 -> {
                                Handler(Looper.getMainLooper()).post {
                                    holder.totalImgBottomLike.setImageResource(R.drawable.magno_like)
//                                    holder.totalTextLikeCount.text = items.reviewLikeCount
                                    notifyDataSetChanged()
                                }
                            }
                        }

                    }
                    Thread.interrupted()
                } catch (e: Exception) {
                    Log.d(TAG, "Thread: Done")
                    isTotalLikeServiceDone = false
                }
            }.start()
        }
    }

    fun clearItemList (paramList: ArrayList<TotalRecyclerItems>) {
        itemList.clear()
        itemList = paramList
        notifyDataSetChanged()
    }

    fun checkWannaGoStatus(items: TotalRecyclerItems) {
        when {
            isTotalItemWannaGo -> { // 서비스가 끝나고 좋아요 성공 시
                items.restaurantLikeStatus = 1
            }
            else -> { // 서비스가 끝나고 좋아요 해제 시
                items.restaurantLikeStatus = 0
            }
        }
    }

    fun checkLikeStatus(items: TotalRecyclerItems) {
        when {
            isTotalItemLike -> { // 서비스가 끝나고 좋아요 성공 시
                items.reviewLikeStatus = 1
//                items.reviewLikeCount = (items.reviewLikeCount.toInt() + 1).toString()
            }
            else -> { // 서비스가 끝나고 좋아요 해제 시
                items.reviewLikeStatus = 0
//                items.reviewLikeCount = (items.reviewLikeCount.toInt() - 1).toString()
            }
        }
    }

    //inner 리사이클러뷰 어답터 장착
    fun setItemsRecycler(recyclerView: RecyclerView, item: ArrayList<String>){
        val itemsAdapter = TotalRecyclerInnerImageAdapter(context, item)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            // 이너 이미지 아이템 클릭 리스너
            itemsAdapter.let {
                it.setMyInnerImgItemClickListener(object : TotalRecyclerInnerImageAdapter.MyInnerImgItemClickListener {
                    override fun onItemClick(position: Int) {


                    }
                })
            } // end listener
            recyclerView.adapter = itemsAdapter
        }

    }

    override fun getItemCount(): Int = itemList.size



    override fun onPatchTotalReviewWannaGoSuccess(response: BaseResponse) {
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.code}")
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.message}")

        when (response.code) {
            1000 -> isTotalItemWannaGo = true
            1001 -> isTotalItemWannaGo = false
        }
        isTotalWannaGoServiceDone = true
    }

    override fun onPatchTotalReviewWannaGoFailure(message: String) {
        Log.d(TAG, "오류: $message")
    }

    override fun onPatchTotalReviewLikeSuccess(response: BaseResponse) {
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.code}")
        Log.d(TAG, "onPatchReviewWannaGoSuccess: ${response.message}")

        when (response.code) {
            1000 -> isTotalItemLike = true
            1001 -> isTotalItemLike = false
        }
        isTotalLikeServiceDone = true
    }

    override fun onPatchTotalReviewLikeFailure(message: String) {
        Log.d(TAG, "오류: $message")
    }

}