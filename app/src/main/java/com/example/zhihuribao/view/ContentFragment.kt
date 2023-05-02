package com.example.zhihuribao.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.zhihuribao.data.AllData
import com.example.zhihuribao.data.LatestMessage
import com.example.zhihuribao.databinding.FragmentContentBinding


class ContentFragment : Fragment() {
    private val mBinding by lazy { FragmentContentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val latestMessageStory = AllData.latestMessageStory
        val contentUrl = latestMessageStory?.url
        Log.d(TAG, "imgUrl = $contentUrl")
        mBinding.fragWebView.settings.javaScriptEnabled = true
        mBinding.fragWebView.webViewClient = WebViewClient()
        if (contentUrl != null) {
            mBinding.fragWebView.loadUrl(contentUrl)
            mBinding.fragWebView.isVisible = true
            Log.d(TAG, "加载webview成功了: ")
        }
        return mBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(message : LatestMessage.Story) =
            ContentFragment().apply {
                arguments = Bundle().apply {
                    AllData.latestMessageStory = message
                }
            }
    }
}