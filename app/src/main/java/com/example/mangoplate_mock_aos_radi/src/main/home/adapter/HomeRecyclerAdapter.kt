package com.example.mangoplate_mock_aos_radi.src.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.home.model.HomeRecyclerItems

class HomeRecyclerAdapter(val context: Context?, var itemList: ArrayList<HomeRecyclerItems>): RecyclerView.Adapter<HomeRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val foodImg: ImageView = itemView.findViewById(R.id.home_recycler_main_img)
        val title: TextView = itemView.findViewById(R.id.home_recycler_text_title)
        val location: TextView = itemView.findViewById(R.id.home_recycler_text_loc)
        val grade: TextView = itemView.findViewById(R.id.home_recycler_text_grade)
        val viewPoint: TextView = itemView.findViewById(R.id.home_recycler_text_view_point)
        val reviewCount: TextView = itemView.findViewById(R.id.home_recycler_text_review_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_recycler_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items: HomeRecyclerItems = itemList[position]
        Glide.with(holder.foodImg).load(items.image).placeholder(R.drawable.home_vp_img1).into(holder.foodImg)
//        holder.foodImg.setImageResource(R.drawable.home_vp_img3)
        holder.title.text = "${items.idx}. ${items.title}"
        holder.location.text = items.location
        holder.grade.text = items.grade
        holder.viewPoint.text = items.viewPoint.toString()
        holder.reviewCount.text = items.reviewCount.toString()
    }

}