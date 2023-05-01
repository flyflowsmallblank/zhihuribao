package com.example.zhihuribao.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.zhihuribao.databinding.FragmentBannerBinding

class BannerFragment : Fragment() {
    private val mBinding by lazy { FragmentBannerBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance(id : Int,imageRes: String,abstract : String,author : String,order : Int) = BannerFragment().apply {
            arguments = Bundle().apply {
                putString("image_res", imageRes)
                putString("abstract",abstract)
                putString("author",author)
                putInt("order",order)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val imgUrl = arguments?.getString("image_res") ?: ""
        val abstract = arguments?.getString("abstract") ?: ""
        val author = arguments?.getString("author") ?: ""
        val order = arguments?.getInt("order") ?: 0
        Glide.with(this@BannerFragment).load(imgUrl).into(mBinding.imageView)
        mBinding.fragTvAbstract.text = abstract
        mBinding.fragTvAuthorTime.text = author
        Log.d(TAG, "测试35: order=$order")
        var str = StringBuilder()
        for(i in 0 until 5){
            if(i == order){
                str.append("● ")
            }else{
                str.append("○ ")
            }
        }
        Log.d(TAG, "我设置的小圆点: $str")
        mBinding.fragBannerPoint.text = str.toString()
        return mBinding.root
    }
}