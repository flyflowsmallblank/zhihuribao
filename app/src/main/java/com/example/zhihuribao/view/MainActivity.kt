package com.example.zhihuribao.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zhihuribao.*
import com.example.zhihuribao.data.LatestMessage
import com.example.zhihuribao.interfacee.BackInterface
import com.example.zhihuribao.util.network.NetWork
import com.example.zhihuribao.util.network.NetWorkFactory
import com.example.zhihuribao.adapter.RvContentAdapter
import com.example.zhihuribao.adapter.ViewPagerAdapter
import com.example.zhihuribao.databinding.ActivityMainBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


const val TAG = "lx"

class MainActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mViewModel by lazy {
        ViewModelProvider(
            this,
            NetWorkFactory("https://news-at.zhihu.com/api/4/")
        )[NetWork::class.java]
    }

    private var count : Long = 1
    private var limitCount : Int = 6
    private lateinit var adapter: RvContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initStatusBar()
        initRvObserver()
        getLatestMessageNet()
        initTime()
    }

    override fun onResume() {
        super.onResume()
        initRefresh()
        initScrollLast()
        initOldMessageObserver()
    }

    /**
     * 初始化老信息的观察者，观察完之后更新适配器
     */
    private fun initOldMessageObserver() {
        mViewModel.oldLiveData.observe(this) {
            Log.d(TAG, "initOldMessageObserver: 观察到了")
            adapter.addData(it.stories)
            Log.d(TAG, "initOldMessageObserver: 观察到的数据是 ${it.stories}")
        }
    }

    /**
     * 初始化滑到底端加载更多
     */
    private fun initScrollLast() {
        mBinding.mainRvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
//                layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                Log.d(TAG, "onScrolled: lastVisibleItemPosition = $lastVisibleItemPosition totalItemCount = $totalItemCount")
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    Log.d(TAG, "onScrolled: 我滑动到了最后 count=$count")
                    if(count<limitCount){
                        val beforeDay = beforeDay(count)
                        getOldMessage(beforeDay)
                    }
                }
            }
        })
    }

    /**
     * 获得之前的老的信息
     */
    private fun getOldMessage(beforeDay: String) {
        mViewModel.getOldMessage(this, beforeDay.toInt())
        Log.d(TAG, "getOldMessage: 请求成功")
        count++
    }

    /**
     * 获取之前日期的一个方法，根据传入参数的不同，减去不同的天数
     * @param reduceDay 传入减去天数
     * @return 返回减去之后的日期
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun beforeDay(reduceDay: Long): String {
        // 获取当前日期
        val today = LocalDate.now()
        // 使用 Kotlin 的日期和时间扩展函数来减去一天
        val yesterday = today.minusDays(reduceDay)
        // 格式化日期为所需格式
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val yesterdayDate = yesterday.format(formatter)
        Log.d(TAG, "beforeDate: $yesterdayDate")
        return yesterdayDate
    }

    /**
     * 初始化刷新过程
     */
    private fun initRefresh() {
        mBinding.mainRefresh.setOnRefreshListener {
            val flag = getLatestMessageNet()
            mBinding.mainRefresh.isRefreshing = false
        }
    }

    /**
     * 初始化观察者对象,这个是观察最新的,同时也是轮播图的banner的
     */
    private fun initRvObserver() {
        mViewModel.homeLiveData.observe(this) {
            val latestMessage = it as LatestMessage
            adapter = RvContentAdapter(latestMessage.stories, this)
            mBinding.mainRvContent.adapter = adapter
            mBinding.mainRvContent.layoutManager = LinearLayoutManager(this)
            mBinding.mainRvContent.isNestedScrollingEnabled = false
            initBanner(it)
        }
    }

    /**
     * 从网络请求数据，然后Rv适配器填充
     */
    private fun getLatestMessageNet(): Boolean {
        val flag = mViewModel.getLatestMessage(this)
        return flag
    }

    /**
     * 左上角日期的更改
     */
    private fun initTime() {
        val calender = Calendar.getInstance()
        val month = calender.get(Calendar.MONTH) + 1
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val monthMap = mapOf<Int, String>(
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
        mBinding.mainFrame.setPadding(0, getStatusBatHeight(), 0, 0)
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

    /**
     * banner轮播
     */

    private fun initBanner(latestMessage: LatestMessage) {
        var fragments = ArrayList<BackInterface>()
        for (top_story in latestMessage.top_stories) {
            var order = latestMessage.top_stories.indexOf(top_story)
            fragments.add(object : BackInterface {
                override fun back(): Fragment {
                    return BannerFragment.newInstance(
                        top_story.id,
                        top_story.image,
                        top_story.title,
                        top_story.hint,
                        order
                    )
                }
            })
        }
        val adapter = ViewPagerAdapter(fragments, this)
        mBinding.mainVp2Banner.adapter = adapter
        Log.d(TAG, "适配器设置成功: ")
        transBanner(latestMessage)
    }

    /**
     * banner的轮播效果的实现
     */
    private fun transBanner(latestMessage: LatestMessage) {
        //自动轮播banner
        var currentIndex = 0
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (currentIndex > latestMessage.top_stories.size) {
                    currentIndex = 0
                }
                mBinding.mainVp2Banner.currentItem = currentIndex++
                handler.postDelayed(this, 3000)
            }
        }, 5000)
    }
}