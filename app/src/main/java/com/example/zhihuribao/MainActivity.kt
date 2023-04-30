package com.example.zhihuribao

import android.R.attr.data
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zhihuribao.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*


const val TAG = "lx"
class MainActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mViewModel by lazy { ViewModelProvider(this,NetWorkFactory("https://news-at.zhihu.com/api/4/"))[NetWork::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initStatusBar()
        initRvObserver()
        startConnection()
//        initTabLayout()
        initTime()
    }

    private fun initTabLayout() {
        TabLayoutMediator(mBinding.mainTab, mBinding.mainVp2Banner) { tab, position ->

        }.attach() //这个没有

    }

    /**
     * 初始化观察者对象,这个是观察最新的,同时也是轮播图的banner的
     */
    private fun initRvObserver() {
        mViewModel.homeLiveData.observe(this){
            val latestMessage = it as LatestMessage
            mBinding.mainRvContent.adapter = RecyclerViewAdapter(latestMessage.stories,this)
            mBinding.mainRvContent.layoutManager = LinearLayoutManager(this)
            initBanner(it)
        }
    }

    /**
     * 从网络请求数据，然后Rv适配器填充
     */
    private fun startConnection() {
        mViewModel.getLatestMessage(this)
    }

    /**
     * 左上角日期的更改
     */
    private fun initTime() {
        val calender = Calendar.getInstance()
        val month = calender.get(Calendar.MONTH)+1
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val monthMap = mapOf<Int,String>(
            1 to "一月", 2 to "二月", 3 to "三月", 4 to "四月",
            5 to "五月", 6 to "六月", 7 to "七月", 8 to "八月",
            9 to "九月", 10 to "十月", 11 to "十一月", 12 to "十二月"
        )
        mBinding.mainFrameDay.text = day.toString()
        mBinding.mainFrameMonth.text = monthMap[month]
    }

    /**
     * 初始化toolbar状态栏
     */
    private fun initStatusBar() {
        setSupportActionBar(mBinding.mainToolbar)  //设置转太烂为我们自定义的toolbar
        mBinding.mainFrame.setPadding(0,getStatusBatHeight(),0,0)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    /**
     * 获得状态栏高度，方便留空
     * @return 返回状态栏的高度
     */
    @SuppressLint("DiscouragedApi")
    private fun getStatusBatHeight() : Int{
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

    /**
     * banner轮播
     */

    private fun initBanner(latestMessage: LatestMessage){
        var fragments = ArrayList<BackInterface>()
        for(top_story in latestMessage.top_stories){
            fragments.add(object : BackInterface {
                override fun back(): Fragment {
                    return BannerFragment.newInstance(top_story.image)
                }
            })
        }
        val adapter = ViewPagerAdapter(fragments , this)
        mBinding.mainVp2Banner.adapter = adapter
        Log.d(TAG, "适配器设置成功: ")
        //自动轮播banner
        var currentIndex = 0
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(object : Runnable {
            override fun run() {
                if(currentIndex>3){
                    currentIndex = 0
                }
                mBinding.mainVp2Banner.currentItem = currentIndex++
                handler.postDelayed(this, 3000)
            }
        }, 5000)
    }
}