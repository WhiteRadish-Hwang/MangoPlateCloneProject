package com.example.mangoplate_mock_aos_radi.src.main.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
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
        val totalTextReviewCount: TextView = itemView.findViewById(R.id.total_text_review_count)
        val totalTextFollowerCount: TextView = itemView.findViewById(R.id.total_text_follower_count)
        val totalTextRetaurantInfo: TextView = itemView.findViewById(R.id.total_text_restaurant_name_loc)
        val totalTextMainContent: TextView = itemView.findViewById(R.id.total_text_main)
        val totalTextLikeCount: TextView = itemView.findViewById(R.id.total_text_like_count)
        val totalTextCommnetCount: TextView = itemView.findViewById(R.id.total_text_comment)
        val totalTextDateAgo: TextView = itemView.findViewById(R.id.total_text_date_ago)
        val totalInnerRecycler: RecyclerView = itemView.findViewById(R.id.total_recycler_img)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_news_total_recycler_item, parent, false)
        return TotalViewHolder(view)
    }


    override fun onBindViewHolder(holder: TotalViewHolder, position: Int) {
        val items = itemList[position]

        //추후 게시글의 유저 이미지로 서버에서 받아서 변경
        Glide.with(holder.totalImgProfile).load(ApplicationClass.profileImageUrl).circleCrop().placeholder(R.drawable.profile).into(holder.totalImgProfile)
        //추후 게시글의 유저 아이디로 서버에서 받아서 변경
        holder.totalTextUserName.text = ApplicationClass.user_id
        //추후 서버에서 받아 유저 리뷰, 팔로워 개수 받아서 변경
        holder.totalTextReviewCount.text = items.reviewCount.toString()
        holder.totalTextFollowerCount.text = items.followerCount.toString()

        holder.totalTextRetaurantInfo.text = items.restaurantInfo
        holder.totalTextMainContent.text = items.mainContentText

        // 버튼 클릭 시 1씩 증가하게 변화를 줘야함
        if (items.likeCount == null || items.commentCount == null){
            holder.totalTextLikeCount.text = ""
            holder.totalTextCommnetCount.text = ""
        }
        holder.totalTextLikeCount.text = "좋아요 ${items.likeCount}개"
        holder.totalTextCommnetCount.text = "댓글 ${items.commentCount}개"

        holder.totalTextDateAgo.text = items.dateTimeAgo

//        // 홀릭 탭에서 사용할 예정
//       val isExpendable: Boolean = items.expendable
//        holder.totalLayoutExpandable.visibility = if (isExpendable) View.VISIBLE else View.GONE
//        holder.totalLayoutProfile.visibility = if (!isExpendable) View.VISIBLE else View.GONE
//        holder.totalLayoutMainContent.visibility = if (!isExpendable) View.VISIBLE else View.GONE
//        holder.totalLayoutFooter.visibility = if (!isExpendable) View.VISIBLE else View.GONE
//
//        holder.itemView.setOnClickListener {
//            if (position == 0) {
//                items.expendable = true
//            }
//        }

        setItemsRecycler(holder.totalInnerRecycler, items.innerImageItems)
    }

    //inner 리사이클러뷰 어답터 장착
    fun setItemsRecycler(recyclerView: RecyclerView, item: ArrayList<TotalRecyclerInnerImageItems>?){
        val itemsAdapter = TotalRecyclerInnerImageAdapter(context, item)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = itemsAdapter
    }

    override fun getItemCount(): Int = itemList.size
}