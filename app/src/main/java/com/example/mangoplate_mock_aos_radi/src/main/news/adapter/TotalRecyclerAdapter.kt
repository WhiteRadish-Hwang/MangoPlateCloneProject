package com.example.mangoplate_mock_aos_radi.src.main.news.adapter

import android.content.Context
import android.graphics.Paint
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.text.toSpannable
import androidx.core.text.toSpanned
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.TopListRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerInnerImageItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerItems

class TotalRecyclerAdapter(val context: Context?, val itemList: ArrayList<TotalRecyclerItems>): RecyclerView.Adapter<TotalRecyclerAdapter.TotalViewHolder>() {

    inner class TotalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
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

//        if (items.isHolic == 1) {
//
//        }
//
//        if (items.restaurantLikeStatus == 1){
//
//        }
//        if (items.reviewLikeStatus == 1){
//
//        }

        setItemsRecycler(holder.totalInnerRecycler, items.reviewImgList)
    }

    //inner 리사이클러뷰 어답터 장착
    fun setItemsRecycler(recyclerView: RecyclerView, item: ArrayList<TotalRecyclerInnerImageItems>?){
        val itemsAdapter = TotalRecyclerInnerImageAdapter(context, item)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = itemsAdapter
    }

    override fun getItemCount(): Int = itemList.size
}