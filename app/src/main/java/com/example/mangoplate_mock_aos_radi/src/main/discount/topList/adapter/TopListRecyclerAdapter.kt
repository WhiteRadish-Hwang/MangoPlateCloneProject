package com.example.mangoplate_mock_aos_radi.src.main.discount.topList.adapter

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.config.BaseResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.DiscountTopListFragmentView
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.DiscountTopListService
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResponse
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.DiscountTopListResultData
import com.example.mangoplate_mock_aos_radi.src.main.discount.topList.model.TopListRecyclerItems
import java.lang.Exception

class TopListRecyclerAdapter(val context: Context?, val itemList: ArrayList<DiscountTopListResultData>): RecyclerView.Adapter<TopListRecyclerAdapter.TopListViewHolder>(), DiscountTopListFragmentView {
    interface MyTopListItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyTopListItemClickListener

    fun setMyTopListItemClickListener(itemClickListener: MyTopListItemClickListener){
        mItemClickListener = itemClickListener
    }

    var adapterIsBookmark: Boolean = false

    inner class TopListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val topListImg: ImageView = itemView.findViewById(R.id.top_list_img)
        val topListTextName: TextView = itemView.findViewById(R.id.top_list_text_name)
        val topListTextOneLine: TextView = itemView.findViewById(R.id.top_list_text_one_line)
        val topListTextViewCount: TextView = itemView.findViewById(R.id.top_list_text_view_count)
        val topListTextUploadDateAt: TextView = itemView.findViewById(R.id.top_list_text_upload_date_at)
        val topListBookMark: ImageView = itemView.findViewById(R.id.top_list_img_book_mark)
        val topListViewCountTop: ImageView = itemView.findViewById(R.id.top_list_img_view_count_top)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_discount_top_list_recycler_items, parent, false)

        return TopListViewHolder(view)
    }


    override fun onBindViewHolder(holder: TopListViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.topListImg).load(items.topListImgUrl).placeholder(R.drawable.home_vp_img1).into(holder.topListImg)
        holder.topListTextName.text = items.topListName
        holder.topListTextOneLine.text = items.topListOneLine
        holder.topListTextViewCount.text = items.updatedAt
        holder.topListTextUploadDateAt.text = items.topListView.toString()

        if (items.userBookMark == 1) holder.topListBookMark.setImageResource(R.drawable.bookmark_click)
        else holder.topListBookMark.setImageResource(R.drawable.bookmark)

        if (items.topListSortOnTop == 1) holder.topListViewCountTop.visibility = View.VISIBLE
        else holder.topListViewCountTop.visibility = View.GONE



        holder.topListBookMark.setOnClickListener {
            DiscountTopListService(this).tryPatchDiscountTopList(items.topListId)
            // 클릭 이벤트
            Thread {
                Thread.sleep(300)
                try {
                    when {
                        adapterIsBookmark -> { // 서비스가 끝나고 북마크 시
                            items.userBookMark = 1
                        }
                        else -> { // 서비스가 끝나고 북마크 해제 시
                            items.userBookMark = 0
                        }
                    }
                        Log.d(TAG, "onBindViewHolder: userBookMark = ${items.userBookMark}")
                        when (items.userBookMark) {
                            1 -> {
                                Handler(Looper.getMainLooper()).post {
                                    holder.topListBookMark.setImageResource(R.drawable.bookmark_click)
                                    notifyDataSetChanged()
                                }
                            }
                            0 -> {
                                Handler(Looper.getMainLooper()).post {
                                    holder.topListBookMark.setImageResource(R.drawable.bookmark)
                                    notifyDataSetChanged()
                                }
                            }
                        }


                    Thread.interrupted()

                } catch (e: Exception) {
                    Log.d(TAG, "Thread: Done")
                }

            }.start()
        }

    }

    override fun getItemCount(): Int = itemList.size


    override fun onGetTopListSuccess(response: DiscountTopListResponse, topListList: ArrayList<DiscountTopListResultData>) {
    }

    override fun onGetTopListFailure(message: String) {
    }

    override fun onPatchTopListSuccess(response: BaseResponse) {
        Log.d(TAG, "onPatchTopListSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchTopListSuccess: ${response.code}")
        Log.d(TAG, "onPatchTopListSuccess: ${response.message}")
        if (response.code == 1000) adapterIsBookmark = true
        else if (response.code == 1001) adapterIsBookmark = false
    }

    override fun onPatchTopListFailure(message: String) {
        Toast.makeText(context, "오류 : $message", Toast.LENGTH_SHORT).show()
    }
}