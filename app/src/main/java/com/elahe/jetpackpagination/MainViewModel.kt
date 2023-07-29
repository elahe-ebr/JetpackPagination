package com.elahe.jetpackpagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.elahe.jetpackpagination.data.Data
import com.elahe.jetpackpagination.network.APIService
import com.elahe.jetpackpagination.network.RetrofitInstance
import com.elahe.jetpackpagination.pagingSource.ResultPagingSource
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException

class MainViewModel : ViewModel() {

    private val apiService: APIService =
        RetrofitInstance.getRetrofitInstance().create(APIService::class.java)

    fun getListData(): Flow<PagingData<Data>> {
        return Pager(config = PagingConfig(pageSize = 6), pagingSourceFactory = {
            ResultPagingSource(apiService)
        }).flow.cachedIn(viewModelScope)
    }
}

class MainViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}