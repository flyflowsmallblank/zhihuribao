package com.example.zhihuribao.interfacee

import android.view.View
import com.example.zhihuribao.data.LatestMessage

interface OnItemClickListener {
    fun onItemClick(view: View, position:Int,storyList: MutableList<LatestMessage.Story>)
}