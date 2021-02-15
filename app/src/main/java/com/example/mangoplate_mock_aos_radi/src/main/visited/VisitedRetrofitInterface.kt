package com.example.mangoplate_mock_aos_radi.src.main.visited

import com.example.mangoplate_mock_aos_radi.src.main.visited.model.GetVisitedResponse
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.PostVisitedResponse
import com.example.mangoplate_mock_aos_radi.src.main.visited.model.PostVisitedResultData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VisitedRetrofitInterface {

    @GET("restaurants/{restaurantId}/visited")
    fun getVisited(@Path("restaurantId") restaurantId: Int) : Call<GetVisitedResponse>

    @POST("restaurants/{restaurantId}/visited")
    fun posetVisitedApply(@Path("restaurantId") restaurantId: Int,
                          @Query("status") status: Int): Call<PostVisitedResponse>
}