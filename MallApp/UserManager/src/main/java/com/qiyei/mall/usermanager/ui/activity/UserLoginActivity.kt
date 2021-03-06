package com.qiyei.mall.usermanager.ui.activity


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.qiyei.framework.constant.MallConstant
import com.qiyei.framework.extend.enable
import com.qiyei.framework.titlebar.CommonTitleBar
import com.qiyei.framework.ui.activity.BaseMVPActivity
import com.qiyei.mall.usermanager.R
import com.qiyei.mall.usermanager.data.bean.UserInfo
import com.qiyei.mall.usermanager.injection.component.DaggerUserManagerComponent
import com.qiyei.mall.usermanager.injection.module.UserManagerModule
import com.qiyei.mall.usermanager.mvp.presenter.UserLoginPresenter
import com.qiyei.mall.usermanager.mvp.view.IUserLoginView
import com.qiyei.provider.router.RouteMall
import com.qiyei.provider.service.mall.IPushManagerService
import com.qiyei.provider.service.mall.MallServiceConstant
import com.qiyei.sdk.dc.DataManager
import kotlinx.android.synthetic.main.activity_user_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

@Route(path = RouteMall.UserManager.LOGIN)
class UserLoginActivity : BaseMVPActivity<UserLoginPresenter>(),IUserLoginView {

    @Autowired(name = MallServiceConstant.PUSH_MANAGER_SERVICE_PATH)
    lateinit var mPushManagerService:IPushManagerService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        initView()
    }

    override fun getTAG(): String {
        return UserLoginActivity::class.java.simpleName
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.mLoginButton -> {
                mPresenter.login(mMobileEditText.text.toString(),mPasswordEditText.text.toString(),mPushManagerService.getPushId())
            }
            R.id.mForgetPasswordEditText -> {
                startActivity<UserForgetPasswordActivity>()
            }
        }
    }

    override fun initComponentInject() {
        //注入点
        DaggerUserManagerComponent.builder()
                .activityComponent(mActivityComponent)
                .userManagerModule(UserManagerModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    /**
     * View层回调
     */
    override fun onLoginResult(userInfo: UserInfo) {
        toast(userInfo.toString())
        DataManager.getInstance().setString(MallConstant::class.java,MallConstant.KEY_USER_NAME,userInfo.userName)
        finish()
    }

    private fun initView(){
        mTitleBar = CommonTitleBar.Builder(this)
                .setTitle("登录")
                .setRightText("注册")
                .setRightClickListener {
                    startActivity<UserRegisterActivity>()
                }
                .build()
        mLoginButton.enable(mMobileEditText){isLoginEnable()}
        mLoginButton.enable(mPasswordEditText){isLoginEnable()}
        mLoginButton.setOnClickListener(this)
        mForgetPasswordEditText.setOnClickListener(this)
        mLoginButton.setOnClickListener(this)
    }

    /**
     * 注册按钮是否可用
     */
    private fun isLoginEnable():Boolean{
        return mMobileEditText.text.isNotEmpty()
                && mPasswordEditText.text.isNotEmpty()
    }
}
