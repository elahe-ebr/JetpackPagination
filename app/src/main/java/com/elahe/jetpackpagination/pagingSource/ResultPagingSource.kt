package com.elahe.jetpackpagination.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elahe.jetpackpagination.data.Data
import com.elahe.jetpackpagination.network.APIService

class ResultPagingSource(private val apiService: APIService) : PagingSource<Int, Data>() {

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = apiService.getListData(currentLoadingPageKey)
            val responseData = mutableListOf<Data>()
            val data = response.body()?.data ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            var nextKey: Int? = null
            if (response.body()?.total_pages!! > response.body()?.page!!)
                nextKey = currentLoadingPageKey.plus(1)

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}