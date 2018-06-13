package com.frame.model.base.mvp;

/**
 * desc：MVP之V层 是所有VIEW的基类，其他类可以继承该类
 * Author：MrZ
 * CrateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */

public interface Iview<T> {

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:09
     *  @uptime 2018/6/13  9:09
     *  @describe 全局的显示加载框
     */
    void showLoading();

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:09
     *  @uptime 2018/6/13  9:09
     *  @describe 全局的显示加载框
     */
    void showLoading(String msg);

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:09
     *  @uptime 2018/6/13  9:09
     *  @describe 全局的显示加载框
     */
    void showLoading(String msg, int progress);

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:09
     *  @uptime 2018/6/13  9:09
     *  @describe 全局的隐藏加载框
     */
    void hideLoading();

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:09
     *  @uptime 2018/6/13  9:09
     *  @describe 全局消息展示
     */
    void showMsg(String msg);

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:09
     *  @uptime 2018/6/13  9:09
     *  @describe 全局错误消息展示
     */
    void showErrorMsg(String msg, String content);

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:09
     *  @uptime 2018/6/13  9:09
     *  @describe 关闭界面信息
     */
    void close();

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:09
     *  @uptime 2018/6/13  9:09
     *  @describe 当前fragment是否有效
     */
    boolean isActive();

    /**
     *  @author MrZ
     *  @time 2018/6/13  9:11
     *  @uptime 2018/6/13  9:11
     *  @describe 无网络处理
     */
    void noNetWork();

}