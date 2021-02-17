package com.example.mangoplate_mock_aos_radi.src.main.review

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.TAG
import com.example.mangoplate_mock_aos_radi.databinding.FragmentHomeSortSelectBinding
import com.example.mangoplate_mock_aos_radi.databinding.FragmentNewsReviewReplyBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@SuppressLint("ResourceAsColor")
class ReviewReplyBottomFragment(val itemClick: (Int) -> Unit): BottomSheetDialogFragment() {
    var _binding: FragmentNewsReviewReplyBottomBinding? = null
    val binding get() = _binding!!

    companion object {
        var replyPosition: Int = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsReviewReplyBottomBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.replyBtnModify.setOnClickListener {
            replyPosition = 0
            itemClick(replyPosition)
            dialog?.dismiss()
        }

        binding.replyBtnDelete.setOnClickListener {
            replyPosition = 1
            itemClick(replyPosition)
            dialog?.dismiss()
        }
        binding.replyBtnCancel.setOnClickListener {

            dialog?.dismiss()
        }


    }

}