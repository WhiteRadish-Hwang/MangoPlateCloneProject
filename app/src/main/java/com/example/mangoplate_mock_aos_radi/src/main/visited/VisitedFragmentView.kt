package com.example.mangoplate_mock_aos_radi.src.main.visited

import com.example.mangoplate_mock_aos_radi.src.main.visited.model.GetVisitedResponse
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.GetVisitedResultData
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.PostVisitedResponse

interface VisitedFragmentView {

    fun onGetVisitedSuccess(response: GetVisitedResponse, result: ArrayList<GetVisitedResultData>)

    fun onGetVisitedFailure(message: String)

    fun onPostVisitedApplySuccess(response: PostVisitedResponse)

    fun onPostVisitedApplyFailure(message: String)

}