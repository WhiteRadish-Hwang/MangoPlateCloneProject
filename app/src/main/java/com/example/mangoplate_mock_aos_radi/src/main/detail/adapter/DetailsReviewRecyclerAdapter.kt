package com.example.mangoplate_mock_aos_radi.src.main.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.detail.model.DetailsReviewRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.adapter.TotalRecyclerInnerImageAdapter

class DetailsReviewRecyclerAdapter(val context: Context?, val itemList: ArrayList<DetailsReviewRecyclerItems>): RecyclerView.Adapter<DetailsReviewRecyclerAdapter.ReviewViewHolder>() {

    interface MyReviewItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyReviewItemClickListener

    fun setMyReviewItemClickListener(itemClickListener: MyReviewItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ReviewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val reviewImgProfile: ImageView = itemView.findViewById(R.id.review_img_profile)
        val reviewTextUserName: TextView = itemView.findViewById(R.id.review_text_user_name)
        val reviewTextUserReviewCount: TextView = itemView.findViewById(R.id.review_text_user_review_count)
        val reviewTextUserFollowerCount: TextView = itemView.findViewById(R.id.review_text_user_follower_count)
        val reviewTextMainContent: TextView = itemView.findViewById(R.id.review_text_main)
        val reviewTextLikeCount: TextView = itemView.findViewById(R.id.review_text_like_count)
        val reviewTextCommnetCount: TextView = itemView.findViewById(R.id.review_text_comment)
        val reviewTextUpdateAt: TextView = itemView.findViewById(R.id.review_text_updateAt)
        val reviewInnerRecycler: RecyclerView = itemView.findViewById(R.id.review_recycler_img)
        val reviewImgExpression: ImageView = itemView.findViewById(R.id.review_img_expression)
        val reviewTextExpression: TextView = itemView.findViewById(R.id.review_text_expression)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_details_review_recycler_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.reviewImgProfile).load(items.userProfileImgUrl).circleCrop().placeholder(R.drawable.profile).into(holder.reviewImgProfile)
        holder.reviewTextUserName.text = items.userName
        holder.reviewTextUserReviewCount.text = items.userReviewCount.toString()
        holder.reviewTextUserFollowerCount.text = items.userFollowerCount.toString()

        holder.reviewTextMainContent.text = items.reviewContents

        // 버튼 클릭 시 1씩 증가하게 변화를 줘야함
        holder.reviewTextLikeCount.text = items.reviewLikeCount.toString()
        holder.reviewTextCommnetCount.text = items.reviewReplyCount.toString()

        holder.reviewTextLikeCount.text = items.reviewLikeCount
        holder.reviewTextCommnetCount.text = items.reviewReplyCount
        holder.reviewTextUpdateAt.text = items.updatedAt

        when (items.reviewExpression){
            "맛있다" -> {
                holder.reviewImgExpression.setImageResource(R.drawable.great_remove)
                holder.reviewTextExpression.text = items.expression_delicious
            }
            "괜찮다" -> {
                holder.reviewImgExpression.setImageResource(R.drawable.good_remove)
                holder.reviewTextExpression.text = items.expression_good
            }
            "별로" -> {
                holder.reviewImgExpression.setImageResource(R.drawable.bad_remove)
                holder.reviewTextExpression.text = items.expression_bad
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

        setItemsRecycler(holder.reviewInnerRecycler, items.reviewImgList)
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
}