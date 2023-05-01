package com.example.zhihuribao.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zhihuribao.interfacee.BackInterface

class ViewPagerAdapter(private val fragments:ArrayList<BackInterface>, activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        Log.d("TAG", "createFragment success ")
        return fragments[position].back()
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}