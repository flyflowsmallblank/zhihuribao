package com.example.zhihuribao.view

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.zhihuribao.adapter.RvContentAdapter
import com.example.zhihuribao.adapter.ViewPagerAdapter
import com.example.zhihuribao.data.AllData
import com.example.zhihuribao.data.LatestMessage
import com.example.zhihuribao.databinding.ActivityContentBinding
import com.example.zhihuribao.interfacee.BackInterface
import java.util.ArrayList

class ContentActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityContentBinding.inflate(layoutInflater) }
    private lateinit var storyList : MutableList<LatestMessage.Story>
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        storyList = AllData.latestMessage!!
        val fragments = ArrayList<BackInterface>()
        for(message in storyList){
                fragments.add(object : BackInterface {
                    override fun back(): Fragment {
                        return ContentFragment.newInstance(message)
                    }
                })
            }
        //吐了。没设置adapter
        mBinding.mainVp2Content.adapter = ViewPagerAdapter(fragments,this)
        mBinding.mainVp2Content.currentItem = AllData.position
    }

    companion object{
        fun startAction(context: Context){
            val intent = Intent(context,ContentActivity::class.java)
            context.startActivity(intent)
        }
    }
}