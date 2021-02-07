package com.example.mangoplate_mock_aos_radi.src.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeSortSelectBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@SuppressLint("ResourceAsColor")
class HomeSortSelectFragment(val itemClick: (Int) -> Unit): BottomSheetDialogFragment() {
    var _binding: FragmentHomeSortSelectBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeSortSelectBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeSortSelectImgArrow.setOnClickListener {
            dialog?.dismiss()
        }

        binding.homeSortSelectTextPoint.setOnClickListener {
            itemClick(0)
            setTextDesign(binding.homeSortSelectTextPoint)
//            dialog?.dismiss()
        }
        binding.homeSortSelectTextRecommend.setOnClickListener {
            itemClick(1)
            setTextDesign(binding.homeSortSelectTextRecommend)
//            dialog?.dismiss()
        }
        binding.homeSortSelectTextReview.setOnClickListener {
            itemClick(2)
            setTextDesign(binding.homeSortSelectTextReview)
//            dialog?.dismiss()
        }
        binding.homeSortSelectTextDistance.setOnClickListener {
            itemClick(3)
            setTextDesign(binding.homeSortSelectTextDistance)
//            dialog?.dismiss()
        }


    }

    fun setTextDesign(view: TextView){
        val homeSortSelectTextPoint = binding.homeSortSelectTextPoint
        val homeSortSelectTextRecommend = binding.homeSortSelectTextRecommend
        val homeSortSelectTextReview = binding.homeSortSelectTextReview
        val homeSortSelectTextDistance = binding.homeSortSelectTextDistance
        val sortTextViewArray = arrayListOf<TextView>(
            homeSortSelectTextPoint,
            homeSortSelectTextRecommend,
            homeSortSelectTextReview,
            homeSortSelectTextDistance
        )

        for (v in sortTextViewArray) {
            if (view == v) {
                Log.d(TAG, "setTextDesign: view $view v $v")
                view.setTextColor(R.color.cliked_color)
                view.setBackgroundResource(R.drawable.home_sort_select_text_border)
            } else {
                v.setTextColor(R.color.uncliked_color)
                v.setBackgroundResource(R.drawable.home_sort_select_text_border_unclicked)
            }
        }
    }

}