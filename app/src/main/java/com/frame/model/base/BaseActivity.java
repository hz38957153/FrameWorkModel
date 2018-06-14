package com.frame.model.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.frame.model.R;
import com.frame.model.widget.NetworkStateView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc：
 * Author：MrZ
 * CreateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */
public abstract class BaseActivity extends AppCompatActivity implements NetworkStateView.OnRefreshListener{

    public static Context mContext;
    private NetworkStateView networkStateView;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        mContext = getApplicationContext();
        afterCreate(savedInstanceState);
    }

    @SuppressLint("InflateParams")
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(R.layout.activity_base, null);
        //设置填充activity_base布局
        super.setContentView(view);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            view.setFitsSystemWindows(true);
        }

        //加载子类Activity的布局
        initDefaultView(layoutResID);
    }

    /**
     * 初始化默认布局的View
     * @param layoutResId 子View的布局id
     */
    private void initDefaultView(int layoutResId) {
        networkStateView = (NetworkStateView) findViewById(R.id.nsv_state_view);
        FrameLayout container = (FrameLayout) findViewById(R.id.fl_activity_child_container);
        View childView = LayoutInflater.from(this).inflate(layoutResId, null);
        container.addView(childView, 0);
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 显示加载中的布局
     */
    public void showLoadingView() {
        networkStateView.showLoading();
    }

    /**
     * 显示加载完成后的布局(即子类Activity的布局)
     */
    public void showContentView() {
        networkStateView.showSuccess();
    }

    /**
     * 显示没有网络的布局
     */
    public void showNoNetworkView() {
        networkStateView.showNoNetwork();
        networkStateView.setOnRefreshListener(this);
    }

    /**
     * 显示没有数据的布局
     */
    public void showEmptyView() {
        networkStateView.showEmpty();
        networkStateView.setOnRefreshListener(this);
    }

    /**
     * 显示数据错误，网络错误等布局
     */
    public void showErrorView() {
        networkStateView.showError();
        networkStateView.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        onNetworkViewRefresh();
    }

    /**
     * 重新请求网络
     */
    public void onNetworkViewRefresh() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
