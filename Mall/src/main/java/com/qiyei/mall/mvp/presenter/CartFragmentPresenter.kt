package com.qiyei.mall.mvp.presenter

import com.qiyei.framework.mvp.presenter.BasePresenter
import com.qiyei.mall.mvp.view.ICartFragmentView
import com.qiyei.mall.mvp.view.IHomeFragmentView
import com.qiyei.sdk.log.LogManager
import javax.inject.Inject

/**
 * @author Created by qiyei2015 on 2018/10/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
class CartFragmentPresenter @Inject constructor():BasePresenter<ICartFragmentView>() {

    override fun getTAG(): String {
        return CartFragmentPresenter::class.java.simpleName
    }

    fun getString(){
        LogManager.i(getTAG(),"hello world")
    }

}