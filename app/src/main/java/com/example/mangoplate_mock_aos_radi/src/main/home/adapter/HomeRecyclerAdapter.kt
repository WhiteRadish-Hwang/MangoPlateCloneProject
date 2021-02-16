package com.example.mangoplate_mock_aos_radi.src.main.home.adapter

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeFragmentView
import com.example.mangoplate_mock_aos_radi.src.main.home.HomeService
import com.example.mangoplate_mock_aos_radi.src.main.home.model.*
import java.lang.Exception

class HomeRecyclerAdapter(val context: Context?, var itemList: ArrayList<HomeRecyclerItems>): RecyclerView.Adapter<HomeRecyclerAdapter.ItemViewHolder>(), HomeFragmentView {
    var isLikeAndVisitedDone: Boolean = false
    var adapterIsLike: Boolean = false


    interface MyItemClickListener {
        fun onItemClick(position: Int)
    }
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mItemClickListener.onItemClick(adapterPosition)
            }
        }

        val foodImg: ImageView = itemView.findViewById(R.id.home_recycler_main_img)
        val title: TextView = itemView.findViewById(R.id.home_recycler_text_title)
        val location: TextView = itemView.findViewById(R.id.home_recycler_text_loc)
        val grade: TextView = itemView.findViewById(R.id.home_recycler_text_grade)
        val viewPoint: TextView = itemView.findViewById(R.id.home_recycler_text_view_point)
        val reviewCount: TextView = itemView.findViewById(R.id.home_recycler_text_review_count)
        val distanceForUser: TextView = itemView.findViewById(R.id.home_recycler_text_distance_for_user)
        val wannaGo: ImageView = itemView.findViewById(R.id.home_img_star)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var adapterIsVisited: Boolean = false
        val items: HomeRecyclerItems = itemList[position]

        Glide.with(holder.foodImg).load(items.image).placeholder(R.drawable.home_vp_img1).into(holder.foodImg)
//        holder.foodImg.setImageResource(R.drawable.home_vp_img3)

        if (items.isVisited == 1) { // 가봤어요 1일 때 체크표시
            holder.wannaGo.setImageResource(R.drawable.already_visited)
            adapterIsVisited = true
        }

        if (!adapterIsVisited) {
            when (items.isLike) {
                1 -> {
                    holder.wannaGo.setImageResource(R.drawable.wanngo_clicked)
                }
                0 -> {
                    holder.wannaGo.setImageResource(R.drawable.star_white)
                }
            }
        }

        if (!adapterIsVisited) {
            holder.wannaGo.setOnClickListener {
                // 가고싶다 추가, 해제 서비스 호출
                HomeService(this).tryPatchWannago(items.restaurantId)

                Thread {
                    Thread.sleep(300)
                    try {
                        if (isLikeAndVisitedDone) {
                            checkLikeImgStatus(items)
                            Log.d(TAG, "onBindViewHolder: isLike = ${items.isLike}")
                            when (items.isLike) {
                                1 -> {
                                    Handler(Looper.getMainLooper()).post {
                                        holder.wannaGo.setImageResource(R.drawable.wanngo_clicked)
                                        notifyDataSetChanged()
                                    }
                                }
                                0 -> {
                                    Handler(Looper.getMainLooper()).post {
                                        holder.wannaGo.setImageResource(R.drawable.star_white)
                                        notifyDataSetChanged()
                                    }
                                }
                            }
                        }
                        if (isLikeAndVisitedDone) Thread.interrupted()

                    } catch (e: Exception) {
                        Log.d(TAG, "Thread: Done > $isLikeAndVisitedDone")
                        isLikeAndVisitedDone = false
                    }

                }.start()

            }
        }

        holder.title.text = "${items.idx}. ${items.title}"
        holder.location.text = items.areaName
        holder.grade.text = items.star
        holder.viewPoint.text = items.viewPoint.toString()
        holder.reviewCount.text = items.reviewCount.toString()
        holder.distanceForUser.text = items.distanceFromUser.toString()
    }

    fun clearItemList() {
        itemList.clear()
        notifyDataSetChanged()
    }

    fun checkLikeImgStatus(items: HomeRecyclerItems) {
        Log.d(TAG, "items.isLike: $adapterIsLike")
        when {
            adapterIsLike -> { // 서비스가 끝나고 좋아요 성공 시
                Log.d(TAG, "onBindViewHolder: 2")
                if (isLikeAndVisitedDone){
                    items.isLike = 1
                }
            }
            else -> { // 서비스가 끝나고 좋아요 해제 시
                Log.d(TAG, "onBindViewHolder: 3")
                if (isLikeAndVisitedDone){
                    if (!adapterIsLike) items.isLike = 0
                }

            }
        }
    }

    fun updateList(items: ArrayList<HomeRecyclerItems>?) {
        items?.let {
            val diffCallback = DiffUtilCallback(this.itemList, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)

            this.itemList.run {
                clear()
                addAll(items)
                diffResult.dispatchUpdatesTo(HomeRecyclerAdapter(context, itemList))
            }
        }
    }







    override fun onGetRestaurantSuccess(response: RestaurantsResponse, topList: ArrayList<TopListResultData>, restaurantList: ArrayList<RestaurantResultData>) {

    }

    override fun onGetRestaurantFailure(message: String) {

    }

    override fun onPatchWannaGoSuccess(response: PatchWannagoResponse) {
        Log.d(TAG, "onPatchWannaGoSuccess: ${response.isSuccess}")
        Log.d(TAG, "onPatchWannaGoSuccess: ${response.code}")
        Log.d(TAG, "onPatchWannaGoSuccess: ${response.message}")
        isLikeAndVisitedDone = true

        if (response.code == 1000) adapterIsLike = true
        else if (response.code == 1001) adapterIsLike = false

    }

    override fun onPatchWannaGoFailure(message: String) {
        Toast.makeText(context, "오류 : $message", Toast.LENGTH_SHORT).show()
    }

}