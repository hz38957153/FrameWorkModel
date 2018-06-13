package com.frame.model.base.mvp;

/**
 * desc：MVP的P层
 * Author：MrZ
 * CrateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */
public interface Ipresenter<T extends Iview> {

    /**
     * @description 关联P与V（绑定，VIEW销毁适合解绑）
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void attachView(T view);

    /**
     * @description 取消关联P与V（防止内存泄漏）
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void detachView();

    /**
     * @description RX订阅
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void subscribe();

    /**
     * @description RX取消订阅
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void unsubscribe();

}
