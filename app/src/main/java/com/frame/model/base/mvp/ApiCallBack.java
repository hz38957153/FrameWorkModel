package com.frame.model.base.mvp;

import com.frame.model.base.BaseFeed;
import com.frame.model.utils.util.LogUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * desc：处理网络数据处理完成后的回调响应（观察者）
 * Author：MrZ
 * CrateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */

public abstract class ApiCallBack<M> implements Consumer<M>
{

    private static final String TAG = "ApiCallBack";
    /**
     * @description 成功接口回调，提供给View处理页面问题
     * @author ydc
     * @createDate
     * @version 1.0
     */
    public abstract void onSuccess(M modelBean);

    /**
     * @description 失败接口回调，提供给View处理页面问题
     * @author ydc
     * @createDate
     * @version 1.0
     */
    public abstract void onFailure(String errorMsg);

    /**
     * @description 请求结束，提供给View处理页面问题
     * @author ydc
     * @createDate
     * @version 1.0
     */
    public abstract void onFinished();


    /**
     * @author MrZ
     * @time 2017/10/24  9:50
     * @notes 成功接口回调 根据状态分别实现onSuccess onFailure onFinished 方法
     */
    @Override
    public void accept(final M modelBean) throws Exception {
        if(modelBean!=null){
            try {
                BaseFeed feed = (BaseFeed) modelBean;
                if(feed.getStatus().getCode() == 200){
                    Observable<M> observable = new Observable() {
                        @Override
                        protected void subscribeActual(Observer observer) {
                            observer.onNext(modelBean);
                            observer.onComplete();
                        }
                    };

                    Observer<M> observer = new Observer<M>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {
                        }

                        @Override
                        public void onNext(@NonNull M m) {
                            onSuccess(modelBean);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            LogUtils.a("onError");
                            onFailure(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            onFinished();
                        }
                    };
                    observable.subscribe(observer);
                }
                else if (feed.getStatus().getCode() == 204){
                    onFailure(feed.getStatus().getMessage());
                }
                else{
                    onFailure(feed.getStatus().getMessage());
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
