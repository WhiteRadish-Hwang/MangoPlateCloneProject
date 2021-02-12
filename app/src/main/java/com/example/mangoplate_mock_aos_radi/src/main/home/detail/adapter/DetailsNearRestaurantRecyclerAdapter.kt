package com.example.mangoplate_mock_aos_radi.src.main.home.detail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.home.detail.model.NearRestaurantResultData

class DetailsNearRestaurantRecyclerAdapter(val context: Context?, var itemList: ArrayList<NearRestaurantResultData>, var areaName: String): RecyclerView.Adapter<DetailsNearRestaurantRecyclerAdapter.ItemViewHolder>() {
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


        val firstImageUrl: ImageView = itemView.findViewById(R.id.details_near_res_recycler_main_img)
        val restaurantName: TextView = itemView.findViewById(R.id.details_near_res__recycler_text_title)
        val locName: TextView = itemView.findViewById(R.id.details_near_res__recycler_text_loc)
        val star: TextView = itemView.findViewById(R.id.details_near_res__recycler_text_grade)
        val restaurantView: TextView = itemView.findViewById(R.id.details_near_res__recycler_text_view_point)
        val reviewCount: TextView = itemView.findViewById(R.id.details_near_res__recycler_text_review_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_restaurant_details_near_restaurant_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items: NearRestaurantResultData = itemList[position]

        Glide.with(holder.firstImageUrl).load(items.firstImageUrl).placeholder(R.drawable.home_vp_img1).into(holder.firstImageUrl)
//        holder.foodImg.setImageResource(R.drawable.home_vp_img3)

        holder.restaurantName.text = items.restaurantName
        holder.locName.text = areaName
        holder.star.text = items.star
        holder.restaurantView.text = items.restaurantView.toString()
        holder.reviewCount.text = items.reviewCount.toString()
    }

    fun clearItemList() {
        itemList.clear()
        notifyDataSetChanged()
    }

}