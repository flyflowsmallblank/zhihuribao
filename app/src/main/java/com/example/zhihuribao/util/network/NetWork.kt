package com.example.zhihuribao.util.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zhihuribao.data.*
import com.example.zhihuribao.interfacee.ApiService
import com.example.zhihuribao.view.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//使用retrofit，rxjava，viewModel，livedata的工具类
class NetWork(val baseUrl: String) : ViewModel(){
    //创建livedata
    val homeLiveData : LiveData<LatestMessage>
        get() = _mutableHomeLiveData
    private val _mutableHomeLiveData = MutableLiveData<LatestMessage>()

    //创建okhttp实例，用来传入retrofit的client方法
    private val okHttpClient = OkHttpClient.Builder().build()
    //创建Retrofit实例
    val retrofit  = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
        .build()

        //接下来定义APiService接口，自己新建类自己实现一下，记得返回值是observable<你Gson解析完之后的数据类名称>
        //数据类没有也要生成一下
        //接下来传递一个rxjava对象,后面放的是你的请求接口，动态的
    val dataService = retrofit.create(ApiService::class.java)

    /**
     * 获得最新的消息的网络请求
     */
    fun getLatestMessage(context: Context) : Boolean{
        var isSuccess = false
        dataService.getLatestMessage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LatestMessage> {
                override fun onSubscribe(d: Disposable) {
                    // 可以在这里执行一些初始化操作，比如显示 loading 状态等
                }

                override fun onNext(response: LatestMessage) {
                    // 在这里处理获取到的网络数据，比如刷新 UI 显示等
                    _mutableHomeLiveData.value = response
                    Log.d(TAG, "onNext: 我成功发送了response，期待接收")
                }

                override fun onError(e: Throwable) {
                    // 在这里处理网络请求失败的情况，比如显示错误提示等
                    e.printStackTrace()
                    isSuccess = false
                    Toast.makeText(context,"连接失败，请重试",Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    // 在这里执行请求完成后的一些操作，比如隐藏 loading 状态等
                    isSuccess = true
                }
            })
        return isSuccess
    }

    /**
     * 获得之前的消息的网络请求
     * @param data 想要查看的日期
     */
    fun getOldMessage(context: Context,data : Int){
        dataService.getOldMessage(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<OldMessage> {
                override fun onSubscribe(d: Disposable) {
                    // 可以在这里执行一些初始化操作，比如显示 loading 状态等
                }

                override fun onNext(response: OldMessage) {
                    // 在这里处理获取到的网络数据，比如刷新 UI 显示等
//                    _mutableHomeLiveData.value = response
                }

                override fun onError(e: Throwable) {
                    // 在这里处理网络请求失败的情况，比如显示错误提示等
                    Toast.makeText(context,"连接失败，请重试",Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    // 在这里执行请求完成后的一些操作，比如隐藏 loading 状态等
                }
            })
    }

    fun getExtraMessage(context: Context,id : Int){
        dataService.getExtraMessage(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ExtraMessage> {
                override fun onSubscribe(d: Disposable) {
                    // 可以在这里执行一些初始化操作，比如显示 loading 状态等
                }

                override fun onNext(response: ExtraMessage) {
                    // 在这里处理获取到的网络数据，比如刷新 UI 显示等
//                    _mutableHomeLiveData.value = response
                }

                override fun onError(e: Throwable) {
                    // 在这里处理网络请求失败的情况，比如显示错误提示等
                    Toast.makeText(context,"连接失败，请重试",Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    // 在这里执行请求完成后的一些操作，比如隐藏 loading 状态等
                }
            })
    }

    fun getLongComment(context: Context,id : Int){
        dataService.getLongComment(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<LongComment> {
                override fun onSubscribe(d: Disposable) {
                    // 可以在这里执行一些初始化操作，比如显示 loading 状态等
                }

                override fun onNext(response: LongComment) {
                    // 在这里处理获取到的网络数据，比如刷新 UI 显示等
//                    _mutableHomeLiveData.value = response
                }

                override fun onError(e: Throwable) {
                    // 在这里处理网络请求失败的情况，比如显示错误提示等
                    Toast.makeText(context,"连接失败，请重试",Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    // 在这里执行请求完成后的一些操作，比如隐藏 loading 状态等
                }
            })
    }

    fun getShortComment(context: Context,id : Int){
        dataService.getShortComment(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ShortComment> {
                override fun onSubscribe(d: Disposable) {
                    // 可以在这里执行一些初始化操作，比如显示 loading 状态等
                }

                override fun onNext(response: ShortComment) {
                    // 在这里处理获取到的网络数据，比如刷新 UI 显示等
//                    _mutableHomeLiveData.value = response
                }

                override fun onError(e: Throwable) {
                    // 在这里处理网络请求失败的情况，比如显示错误提示等
                    Toast.makeText(context,"连接失败，请重试",Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    // 在这里执行请求完成后的一些操作，比如隐藏 loading 状态等
                }
            })
    }


        /**
         * 使用方法
         * 一创建
         * private val mViewModel by lazy { ViewModelProvider(this)[NetWorkPlus::class.java] }
         * 二调用方法startConnection
         * 三观察
         * mViewModel.homeLiveData.observe(viewLifecycleOwner){
        Log.d("lx", "onCreate: 观察到了")
        接下来的操作
        }
         */


}