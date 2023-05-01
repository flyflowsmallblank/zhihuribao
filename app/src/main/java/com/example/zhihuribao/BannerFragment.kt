package com.example.zhihuribao

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.zhihuribao.databinding.FragmentBannerBinding

class BannerFragment : Fragment() {
    private val mBinding by lazy { FragmentBannerBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance(id : Int,imageRes: String,abstract : String,author : String) = BannerFragment().apply {
            arguments = Bundle().apply {
                putString("image_res", imageRes)
                putString("abstract",abstract)
                putString("author",author)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val imgUrl = arguments?.getString("image_res") ?: ""
        val abstract = arguments?.getString("abstract") ?: ""
        val author = arguments?.getString("author") ?: ""
        Glide.with(this@BannerFragment).load(imgUrl).into(mBinding.imageView)
        mBinding.fragTvAbstract.text = abstract
        mBinding.fragTvAuthorTime.text = author
        return mBinding.root
    }

    fun changePoint(index : Int){
    }
}