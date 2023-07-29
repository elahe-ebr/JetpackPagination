package com.elahe.jetpackpagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elahe.jetpackpagination.adapter.LoaderStateAdapter
import com.elahe.jetpackpagination.adapter.MainListAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var mViewModel: MainViewModel
    lateinit var mainListAdapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initList()
        initViewModel()
    }

    private fun initList() {
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            mainListAdapter = MainListAdapter()
            val loaderStateAdapter = LoaderStateAdapter{
                mainListAdapter.retry()
            }
           adapter = mainListAdapter.withLoadStateFooter(loaderStateAdapter)
        }
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this,MainViewModelFactory())[MainViewModel::class.java]
        lifecycleScope.launch {
            mViewModel.getListData().collect {
                mainListAdapter.submitData(it)
            }
        }
    }
}