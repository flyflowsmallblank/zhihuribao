package com.example.zhihuribao

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fragments:ArrayList<BackInterface>, activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        Log.d("TAG", "createFragment success ")
        return fragments[position].back()
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}