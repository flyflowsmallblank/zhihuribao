package com.example.zhihuribao

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.zhihuribao.databinding.FragmentBannerBinding

class BannerFragment : Fragment() {
    private val mBinding by lazy { FragmentBannerBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance(imageRes: Int) = BannerFragment().apply {
            arguments = Bundle().apply { putInt("image_res", imageRes) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val imageView = mBinding.imageView as ImageView
        imageView.setImageResource(arguments?.getInt("image_res") ?: 0)
        Log.d(TAG, "取出的图片资源是 ${arguments?.getInt("image_res") ?: 0}")
        return mBinding.root
    }
}