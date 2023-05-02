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
import androidx.lifecycle.ViewModelProvider
import com.example.zhihuribao.data.AllData
import com.example.zhihuribao.data.Comment
import com.example.zhihuribao.data.LatestMessage
import com.example.zhihuribao.databinding.FragmentContentBinding
import com.example.zhihuribao.util.network.NetWork
import com.example.zhihuribao.util.network.NetWorkFactory
import java.util.ArrayList


class ContentFragment : Fragment() {
    private val mBinding by lazy { FragmentContentBinding.inflate(layoutInflater) }
    private val mViewModel by lazy {
        ViewModelProvider(
            this,
            NetWorkFactory("https://news-at.zhihu.com/api/4/")
        )[NetWork::class.java]
    }
    private var commentList = ArrayList<Comment>()

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
        val id = latestMessageStory?.id
        initCommentObserver()
        if(id != null){
           getExtra(id)
        }
        Log.d(TAG, "id: $id")
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        initClick()
    }

    private fun initClick() {
        mBinding.fragContentBack.setOnClickListener {
            requireActivity().finish()
        }
    }


    private fun initCommentObserver() {
        mViewModel.extraLiveData.observe(viewLifecycleOwner){
            mBinding.fragContentCommentSize.text = it.comments.toString()
            mBinding.fragContentGoodSize.text = it.popularity.toString()
        }
    }

    /**
     * 网络请求到额外的信息，点赞评论数之类的
     */
    private fun getExtra(id : Int){
        mViewModel.getExtraMessage(requireActivity(),id)
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