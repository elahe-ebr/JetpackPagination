package com.elahe.jetpackpagination.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elahe.jetpackpagination.R
import com.elahe.jetpackpagination.data.Data

class MainListAdapter : PagingDataAdapter<Data, MainListAdapter.ViewHolder>(DiffUtilCallBack) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Data) {
            itemView.apply {
                itemView.findViewById<TextView>(R.id.tvName).text = data.last_name
                itemView.findViewById<TextView>(R.id.tvEmail).text = data.email

                val ivAvatar = itemView.findViewById<ImageView>(R.id.ivAvatar)

                Glide.with(ivAvatar)
                    .load(data.avatar)
                    .centerCrop()
                    .into(ivAvatar)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
    )

    object DiffUtilCallBack : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }
}