package com.powerge.wise.powerge.otherPages

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.powerge.wise.basestone.heart.network.FlatMapResponse
import com.powerge.wise.basestone.heart.network.FlatMapTopResList
import com.powerge.wise.basestone.heart.network.ResultModelData
import com.powerge.wise.basestone.heart.ui.XAdapter
import com.powerge.wise.basestone.heart.ui.view.PagingRecyclerView
import com.powerge.wise.powerge.BR
import com.powerge.wise.powerge.R
import com.powerge.wise.powerge.bean.User
import com.powerge.wise.powerge.bean.ZhiZhangLogBean
import com.powerge.wise.powerge.config.soap.ApiService
import com.powerge.wise.powerge.config.soap.request.BaseUrl
import com.powerge.wise.powerge.config.soap.request.RequestBody
import com.powerge.wise.powerge.config.soap.request.RequestEnvelope
import com.powerge.wise.powerge.databinding.ItemZhiZhangLogesBinding
import com.powerge.wise.powerge.helper.EEMsgToastHelper
import kotlinx.android.synthetic.main.activity_staff_traning.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class StaffTraningActivity : AppCompatActivity() {
    var currentPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_traning)
        tv_title.text = resources.getStringArray(R.array.item_name_array)[10]
        initView()
    }

    @SuppressLint("ResourceAsColor")
    private fun initView() {
        refresh_layout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        refresh_layout.setOnRefreshListener(refreshListener)
        content_log.setOnLoadMoreListener(onLoadMoreListener)
        val layoutManager = LinearLayoutManager(this)
        content_log.layoutManager = layoutManager
        content_log.adapter = adapter
    }

    internal var adapter: XAdapter<ZhiZhangLogBean, ItemZhiZhangLogesBinding> = XAdapter.SimpleAdapter(BR.textItem, R.layout.item_zhi_zhang_loges)

    var refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener { content_log.state = PagingRecyclerView.State.Refresh }

    var onLoadMoreListener: PagingRecyclerView.OnLoadMoreListener = PagingRecyclerView.OnLoadMoreListener { getData(currentPage + 1) }

    private fun getData(page: Int) {
        val zhiZhangLogBean = ZhiZhangLogBean.newInstance()
        zhiZhangLogBean.nameSpace = BaseUrl.NAMESPACE_P
        zhiZhangLogBean.setArg1(page.toString())
        zhiZhangLogBean.setUserName(User.getCurrentUser().getName())
        RequestEnvelope.getRequestEnvelope().setBody(RequestBody(zhiZhangLogBean))

        ApiService.Creator.get().queryMonitorLogs(RequestEnvelope.getRequestEnvelope())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(FlatMapResponse())
                .flatMap(FlatMapTopResList())
                .subscribe(object : Subscriber<ResultModelData.ReturnValueBean<ZhiZhangLogBean>>() {
                    override fun onCompleted() {
                        refresh_layout.isRefreshing = false
                        content_log.state = PagingRecyclerView.State.LoadSuccess
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        EEMsgToastHelper.newInstance().selectWitch(e.message)
                        refresh_layout.isRefreshing = false
                        content_log.state = PagingRecyclerView.State.LoadFail
                    }

                    override fun onNext(returnValueBean: ResultModelData.ReturnValueBean<ZhiZhangLogBean>) {
                        if (returnValueBean.currentPage == "1") {
                            adapter.setList(returnValueBean.resultList)
                        } else {
                            adapter.addItems(returnValueBean.resultList)
                        }
                        refresh_layout.isRefreshing = false
                        content_log.state = if (returnValueBean.resultList == null || returnValueBean.resultList.size < 10) PagingRecyclerView.State.NoMore else PagingRecyclerView.State.LoadSuccess
                        currentPage = Integer.parseInt(returnValueBean.currentPage)
                    }
                })
    }

    fun onClick(view: View) {
        finish()
    }

}