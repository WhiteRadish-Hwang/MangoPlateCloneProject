package com.example.mangoplate_mock_aos_radi.src.main.add

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.example.mangoplate_mock_aos_radi.R
import com.example.mangoplate_mock_aos_radi.config.BaseFragment
import com.example.mangoplate_mock_aos_radi.databinding.FragmentAddBinding

class AddFragment: BaseFragment<FragmentAddBinding>(FragmentAddBinding::bind, R.layout.fragment_add){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animation_eat_deal = AnimationUtils.loadAnimation(context, R.anim.alpha_transition_add_eat_deal)
        val animation_visited = AnimationUtils.loadAnimation(context, R.anim.alpha_transition_add_visited)
        val animation_review = AnimationUtils.loadAnimation(context, R.anim.alpha_transition_add_review)
        val animation_register = AnimationUtils.loadAnimation(context, R.anim.alpha_transition_add_register)

        binding.addLayoutEatDeal.startAnimation(animation_eat_deal)
        binding.addLayoutVisited.startAnimation(animation_visited)
        binding.addLayoutReview.startAnimation(animation_review)
        binding.addLayoutRegister.startAnimation(animation_register)

    }
}