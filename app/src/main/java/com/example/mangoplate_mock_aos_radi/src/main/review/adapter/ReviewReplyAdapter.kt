package com.example.mangoplate_mock_aos_radi.src.main.review.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.review.model.ReviewReplyListResultData

class ReviewReplyAdapter(val context: Context?, val itemList: ArrayList<ReviewReplyListResultData>): RecyclerView.Adapter<ReviewReplyAdapter.ReplyItemViewHolder>() {
    interface MyReplyItemClickListener {
        fun onItemClick(position: Int)
    }

    private lateinit var mItemClickListener: MyReplyItemClickListener

    fun setMyReplyItemClickListener(itemClickListener: MyReplyItemClickListener) {
        mItemClickListener = itemClickListener
    }

    inner class ReplyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val replyImgUserProfile: ImageView = itemView.findViewById(R.id.reply_img_user_profile)
        val replyTextUserName: TextView = itemView.findViewById(R.id.reply_text_user_name)
        val replyImgIsHolic: ImageView = itemView.findViewById(R.id.reply_img_user_holic)
        val replyTextReplyContent: TextView = itemView.findViewById(R.id.reply_text_reply_content)
        val replyTextUpdateAt: TextView = itemView.findViewById(R.id.reply_text_update_at)
        val replyTextReplyAdd: TextView = itemView.findViewById(R.id.reply_text_reply_add)
        val replyTextReplyTag: TextView? = itemView.findViewWithTag(R.id.reply_text_reply_tag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recycler_reply_item, parent, false)
        return ReplyItemViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ReplyItemViewHolder, position: Int) {
        val items = itemList[position]
        var recommentedUsers = ""

        Log.d(TAG, "onBindViewHolder: ${items.commentUserList}")
        if (!items.commentUserList.isNullOrEmpty()) {
            items.commentUserList.forEach {commnetedUser ->
                 recommentedUsers += "@$commnetedUser "
            }
            holder.replyTextReplyTag?.visibility = View.VISIBLE
            holder.replyTextReplyTag?.text = recommentedUsers
        } else {
            holder.replyTextReplyTag?.visibility = View.GONE
        }
        items.commentUserList


        Glide.with(holder.replyImgUserProfile).load(items.replyUserProfileImgUrl).placeholder(null).circleCrop().into(holder.replyImgUserProfile)
        holder.replyTextUserName.text = items.replyUserName
        holder.replyTextReplyContent.text = items.replyContents

        when (items.isHolic) {
            1 -> holder.replyImgIsHolic.visibility = View.VISIBLE
            0 -> holder.replyImgIsHolic.visibility = View.GONE
        }


        holder.replyTextUpdateAt.text = items.updatedAt

//        holder.replyTextReplyAdd.setOnClickListener {
//            commentUserList.add(items.replyUserId)
//        }

    }

    override fun getItemCount(): Int = itemList.size
}
