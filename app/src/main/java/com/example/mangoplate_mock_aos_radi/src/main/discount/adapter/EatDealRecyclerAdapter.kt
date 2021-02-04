package com.example.mangoplate_mock_aos_radi.src.main.discount.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.src.main.discount.model.EatDealRecyclerItems

class EatDealRecyclerAdapter(val context: Context?, val itemList: ArrayList<EatDealRecyclerItems>): RecyclerView.Adapter<EatDealRecyclerAdapter.EatDealViewHolder>() {
    inner class EatDealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val eat_deal_img: ImageView = itemView.findViewById(R.id.eat_deal_img)
        val eat_deal_text_title: TextView = itemView.findViewById(R.id.eat_deal_text_title)
        val eat_deal_text_sub_title: TextView = itemView.findViewById(R.id.eat_deal_text_sub_title)
        val eat_deal_text_can_take_out: TextView = itemView.findViewById(R.id.eat_deal_text_can_take_out)
        val eat_deal_text_new_and_hot: TextView = itemView.findViewById(R.id.eat_deal_text_new_and_hot)
        val eat_deal_text_discount_rate: TextView = itemView.findViewById(R.id.eat_deal_text_discount_rate)
        val eat_deal_text_discount_price: TextView = itemView.findViewById(R.id.eat_deal_text_discounted_price)
        val eat_deal_text_original_price: TextView = itemView.findViewById(R.id.eat_deal_text_original_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EatDealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_discount_eat_deal_recycler_items, parent, false)
        return EatDealViewHolder(view)
    }


    override fun onBindViewHolder(holder: EatDealViewHolder, position: Int) {
        val items = itemList[position]

        Glide.with(holder.eat_deal_img).load(items.image).placeholder(R.drawable.home_vp_img1).into(holder.eat_deal_img)
        holder.eat_deal_text_title.text = items.title
        holder.eat_deal_text_sub_title.text = items.subTitle
        holder.eat_deal_text_can_take_out.text = items.canTakeout
        holder.eat_deal_text_new_and_hot.text = items.newAndHot
        holder.eat_deal_text_discount_rate.text = "${items.discountRate}%"
        holder.eat_deal_text_original_price.text = items.price
        val discountPrice = items.price.toInt() * (1-(items.discountRate.toInt() / 100))
        holder.eat_deal_text_discount_price.text = "$discountPrice"
    }

    override fun getItemCount(): Int = itemList.size
}