package com.example.zhihuribao.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zhihuribao.data.Comment
import com.example.zhihuribao.databinding.ItemCommentBinding
import java.text.SimpleDateFormat
import java.util.*

class RvCommentAdapter(val comment: Comment,val context: Context) : ListAdapter<String, RvCommentAdapter.Holder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
) {
    inner class Holder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvCommentAdapter.Holder {
        return Holder(ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.itemCommentAuthor.text = comment.comments[position].author
        holder.binding.itemCommentContent.text = comment.comments[position].content
        holder.binding.itemCommentGoodSize.text = comment.comments[position].likes.toString()
        val millis = comment.comments[position].time.toLong() // 当前时间的毫秒数
        val date = Date(millis) // 将毫秒数转换为 Date 对象
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // 设置时间格式
        val time = formatter.format(date) // 将 Date 格式化为字符串
        holder.binding.itemCommentTime.text = time.toString()
        Glide.with(context)
            .load(comment.comments[position].avatar)
            .override(150,150)
            .into(holder.binding.itemCommentImg)
    }

    override fun getItemCount(): Int {
        return comment.comments.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(comment: Comment){
        comment.comments.addAll(comment.comments)
        notifyDataSetChanged()
    }
}