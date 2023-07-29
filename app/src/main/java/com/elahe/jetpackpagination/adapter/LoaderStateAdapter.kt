package com.elahe.jetpackpagination.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elahe.jetpackpagination.R


class LoaderStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bind(loadState: LoadState) {
            val progressBar = itemView.findViewById<ProgressBar>(R.id.pbLoader)
            val btnRetry = itemView.findViewById<Button>(R.id.btnRetry)

            when (loadState) {
                is LoadState.Loading -> progressBar.visibility = View.VISIBLE
                is LoadState.Error -> btnRetry.visibility = View.VISIBLE
                is LoadState.NotLoading -> {
                    btnRetry.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
            }
        }

        init {
            itemView.findViewById<Button>(R.id.btnRetry).setOnClickListener {
                retry.invoke()
            }
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false),retry)
    }

}