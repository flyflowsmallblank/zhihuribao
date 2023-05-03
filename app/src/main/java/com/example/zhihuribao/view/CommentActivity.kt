package com.example.zhihuribao.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zhihuribao.adapter.RvCommentAdapter
import com.example.zhihuribao.data.AllData
import com.example.zhihuribao.data.Comment
import com.example.zhihuribao.databinding.ActivityCommentBinding
import com.example.zhihuribao.util.network.NetWork
import com.example.zhihuribao.util.network.NetWorkFactory

class CommentActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityCommentBinding.inflate(layoutInflater) }
    private var comment : Comment? = null
    private lateinit var adapter: RvCommentAdapter
    private val mViewModel by lazy {
        ViewModelProvider(
            this,
            NetWorkFactory("https://news-at.zhihu.com/api/4/")
        )[NetWork::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initCommentObserver()
        AllData.commentId?.let { getComment(it) }
        initStatusBar()
        comment?.let { initAdapter(it) }
    }

    override fun onResume() {
        super.onResume()
        initRefresh()
       initBack()
    }

    private fun initBack() {
        mBinding.commentBack.setOnClickListener {
            finish()
        }
    }

    private fun initRefresh() {
        mBinding.mainRefresh.setOnRefreshListener {
            mBinding.mainRefresh.isRefreshing = false
        }
    }

    private fun initAdapter(comment: Comment) {
        Log.d(TAG, "initAdapter: 这个时候的值 comment = $comment")
        adapter = RvCommentAdapter(comment,this)
        val manager = LinearLayoutManager(this)
        mBinding.commentRecycler.adapter = adapter
        mBinding.commentRecycler.layoutManager = manager
    }

    private fun getComment(id : Int?) {
        Log.d(TAG, "CommentActivity,id: $id ")
        id?.let {
            mViewModel.getShortComment(this,it)
            mViewModel.getLongComment(this,it)
        }
    }

    private fun initCommentObserver() {
        mViewModel.commentLiveData.observe(this){
            Log.d(TAG, "initCommentObserver: 我请求到了$it")
            if(comment == null){
                comment = Comment(it.comments)
                initAdapter(comment!!)
                val text = it.comments.size.toString() + "条评论"
                mBinding.commentNumber.text = text
            }else{
                adapter.addData(it)
                val text = comment!!.comments.size.toString() + "条评论"
                mBinding.commentNumber.text = text
            }
        }
    }

    /**
     * 初始化toolbar状态栏
     */
    private fun initStatusBar() {
        setSupportActionBar(mBinding.toolbar)  //设置转太烂为我们自定义的toolbar
        mBinding.toolbar.setPadding(0, getStatusBatHeight(), 0, 0)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    /**
     * 获得状态栏高度，方便留空
     * @return 返回状态栏的高度
     */
    @SuppressLint("DiscouragedApi")
    private fun getStatusBatHeight(): Int {
        //得到状态栏的高度
        var result = 0
        //获取状态栏高度的资源id
        @SuppressLint("InternalInsetResource") val resourceId =
            resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        Log.d(TAG, "getStatusBatHeight:$result ")
        return result
    }

    companion object{
        fun startAction(context: Context){
            val intent = Intent(context,CommentActivity::class.java)
            context.startActivity(intent)
        }
    }
}