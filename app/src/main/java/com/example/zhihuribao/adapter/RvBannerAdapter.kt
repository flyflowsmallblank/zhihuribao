package com.example.zhihuribao.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zhihuribao.databinding.FragmentBannerBinding

//class RvBannerAdapter : ListAdapter<String, RvBannerAdapter.Holder>(
//    object : DiffUtil.ItemCallback<String>() {
//        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
//            return oldItem == newItem
//        }
//
//        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
//            return oldItem == newItem
//        }
//    }
//){
//    class Holder(val binding: FragmentBannerBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//
//    }
//
//    override fun onBindViewHolder(holder: Holder, position: Int) {
//
//    }
//}