package com.frame.model.base.mvp;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MrZ on 2017/10/11. 14:54
 * project delin
 * Explain 抽象的公用Presenter
 */

public abstract class BasePresenter<T extends Iview> implements Ipresenter<T> {

    protected T mMvpView;//所有View
    protected DisposableList mSubscriptions;//rx注册中心
    protected DataRepository mDataCenter;//数据中心
    //protected abstract DisposableList createDisposableList();//引入darger后取缔

    /**
     * @description 获取V
     * @author ydc
     * @createDate
     * @version 1.0
     */
    public T getMvpView() {
        return mMvpView;
    }

    /**
     * @description view绑定P的时候初始化
     * @author ydc
     * @createDate
     * @version 1.0
     */
    @Override
    public void attachView(T view) {
        this.mMvpView = view;
        this.mSubscriptions = new DisposableList();
        this.mDataCenter = DataRepository.getInstance();
    }

    /**
     * @description view失去绑定清除
     * @author ydc
     * @createDate
     * @version 1.0
     */
    @Override
    public void detachView() {
        unsubscribe();
        this.mMvpView = null;
        this.mSubscriptions = null;
        this.mDataCenter = null;
    }

    @Override
    public void unsubscribe(){
        if(mSubscriptions!=null){
            mSubscriptions.clear();
        }
    }

    /**
     * @description 当前的view（fragemnt&activity是否存在）
     * @author ydc
     * @createDate
     * @version 1.0
     */
    public boolean isViewAttached() {
        return mMvpView != null;
    }

    /**
     * @description 是否viewb绑定过P
     * @author ydc
     * @createDate
     * @version 1.0
     */
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    /**
     * @description p&v没有绑定的异常
     * @author ydc
     * @createDate
     * @version 1.0
     */
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter");
        }
    }

    /**
     * @description 统一添加订阅关联被观察者和观察者
     * @author ydc
     * @createDate
     * @version 1.0
     */
    public void addSubscription(Flowable flowable, Consumer consumer,Consumer c) {
        if( flowable!=null && consumer!=null ){
            if (mSubscriptions == null) {
                mSubscriptions = new DisposableList();
            }
            mSubscriptions.clear();
            mSubscriptions.add(flowable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer,c));
        }
    }

    public void addSubscription(Observable flowable, Consumer consumer, Consumer c,Action action) {
        if( flowable!=null && consumer!=null ){
            if (mSubscriptions == null) {
                mSubscriptions = new DisposableList();
            }
            mSubscriptions.clear();
            mSubscriptions.add(flowable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer,c,action));
        }
    }
}
