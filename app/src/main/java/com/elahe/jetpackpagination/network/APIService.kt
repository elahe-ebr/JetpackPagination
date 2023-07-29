package com.elahe.jetpackpagination.network

import com.elahe.jetpackpagination.data.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("api/users")
    suspend fun getListData(@Query("page") pageNumber: Int): Response<ApiResponse>

}