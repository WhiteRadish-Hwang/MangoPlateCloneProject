package com.example.mangoplate_mock_aos_radi.src.main.news.adapter

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
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.TopListRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.HollicRecyclerItems
import com.example.mangoplate_mock_aos_radi.src.main.news.model.TotalRecyclerInnerImageItems

class HollicRecyclerAdapter(val context: Context?, val itemList: ArrayList<HollicRecyclerItems>): RecyclerView.Adapter<HollicRecyclerAdapter.HollicViewHolder>() {
    companion object{
        var isExpandable: Boolean = false
    }

    inner class HollicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val HolicImgProfile: ImageView = itemView.findViewById(R.id.holic_img_profile)
        val HolicTextUserName: TextView = itemView.findViewById(R.id.holic_text_user_name)
        val HolicTextReviewCount: TextView = itemView.findViewById(R.id.holic_text_review_count)
        val HolicTextFollowerCount: TextView = itemView.findViewById(R.id.holic_text_follower_count)
        val HolicTextRetaurantInfo: TextView = itemView.findViewById(R.id.holic_text_restaurant_name_loc)
        val HolicTextMainContent: TextView = itemView.findViewById(R.id.holic_text_main)
        val HolicTextLikeCount: TextView = itemView.findViewById(R.id.holic_text_like_count)
        val HolicTextCommnetCount: TextView = itemView.findViewById(R.id.holic_text_comment)
        val HolicTextDateAgo: TextView = itemView.findViewById(R.id.holic_text_date_ago)
        val HolicInnerRecycler: RecyclerView = itemView.findViewById(R.id.holic_recycler_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HollicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_discount_top_list_recycler_items, parent, false)
        return HollicViewHolder(view)
    }


    override fun onBindViewHolder(holder: HollicViewHolder, position: Int) {
        val items = itemList[position]

        //추후 게시글의 유저 이미지로 서버에서 받아서 변경
        Glide.with(holder.HolicImgProfile).load(ApplicationClass.profileImageUrl).circleCrop().placeholder(R.drawable.profile).into(holder.HolicImgProfile)
        //추후 게시글의 유저 아이디로 서버에서 받아서 변경
        holder.HolicTextUserName.text = ApplicationClass.user_name
        //추후 서버에서 받아 유저 리뷰, 팔로워 개수 받아서 변경
        holder.HolicTextReviewCount.text = items.reviewCount.toString()
        holder.HolicTextFollowerCount.text = items.followerCount.toString()

        holder.HolicTextRetaurantInfo.text = items.restaurantInfo
        holder.HolicTextMainContent.text = items.mainContentText

        // 버튼 클릭 시 1씩 증가하게 변화를 줘야함
        if (items.likeCount == null || items.commentCount == null){
            holder.HolicTextLikeCount.text = ""
            holder.HolicTextCommnetCount.text = ""
        }
        holder.HolicTextLikeCount.text = "좋아요 ${items.likeCount}개"
        holder.HolicTextCommnetCount.text = "댓글 ${items.commentCount}개"

        holder.HolicTextDateAgo.text = items.dateTimeAgo

        setItemsRecycler(holder.HolicInnerRecycler, items.innerImageItems)


    }
    //inner 리사이클러뷰 어답터 장착
    fun setItemsRecycler(recyclerView: RecyclerView, item: ArrayList<String>){
        val itemsAdapter = TotalRecyclerInnerImageAdapter(context, item)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = itemsAdapter
    }

    override fun getItemCount(): Int = itemList.size
}