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
        fun newInstance(imageRes: String) = BannerFragment().apply {
            arguments = Bundle().apply { putString("image_res", imageRes) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val imgUrl = arguments?.getString("image_res") ?: ""
        Glide.with(this@BannerFragment).load(imgUrl).into(mBinding.imageView)
        return mBinding.root
    }
}