package com.example.zhihuribao

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.zhihuribao.`interface`.BackInterface
import com.example.zhihuribao.adapter.ViewPagerAdapter
import com.example.zhihuribao.databinding.ActivityMainBinding


const val TAG = "lx"
class MainActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initStatusBar()
        initBanner()
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

    private fun initBanner(){
        var fragments = ArrayList<BackInterface>()
        fragments.add(object : BackInterface{
            override fun back(): Fragment {
                return BannerFragment.newInstance(R.drawable.banner1)
            }
        })
        fragments.add(object : BackInterface{
            override fun back(): Fragment {
                return BannerFragment.newInstance(R.drawable.banner2)
            }
        })
        fragments.add(object : BackInterface{
            override fun back(): Fragment {
                return BannerFragment.newInstance(R.drawable.banner3)
            }
        })
        fragments.add(object : BackInterface{
            override fun back(): Fragment {
                return BannerFragment.newInstance(R.drawable.banner4)
            }
        })
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