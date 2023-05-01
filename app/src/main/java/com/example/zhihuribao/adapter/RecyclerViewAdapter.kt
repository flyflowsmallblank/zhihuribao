package com.example.zhihuribao.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zhihuribao.data.LatestMessage
import com.example.zhihuribao.databinding.ItemRecyclerBinding


class RecyclerViewAdapter(val storyList: List<LatestMessage.Story>, val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.Holder>(){
    class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root)

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
}