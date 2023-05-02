package com.example.zhihuribao.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zhihuribao.data.LatestMessage
import com.example.zhihuribao.databinding.ItemRecyclerBinding
import com.example.zhihuribao.view.MainActivity


class RvContentAdapter(var storyList: MutableList<LatestMessage.Story>, val context: Context) : ListAdapter<String, RvContentAdapter.Holder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
){
    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.rvItemTvAbstract.text = storyList[position].title.toString()
        holder.binding.rvItemTvAuthorTime.text = storyList[position].hint.toString()
        Glide.with(context)
            .load(storyList[position].images[0].toString())
            .override(225,225)
            .into(holder.binding.rvItemImg)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newList: MutableList<LatestMessage.Story>){
        storyList.addAll(newList)
        notifyDataSetChanged()
    }
}