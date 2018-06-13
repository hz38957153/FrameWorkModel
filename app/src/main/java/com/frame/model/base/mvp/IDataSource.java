package com.frame.model.base.mvp;

import com.frame.model.base.Feed;

import java.util.List;

import io.reactivex.Flowable;

/**
 * desc：数据中心
 * Author：MrZ
 * CrateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */
public interface IDataSource {

    /**
     * @description 获取一组数据
     * @author ydc
     * @createDate
     * @version 1.0
     */
    Flowable<List<Feed>> getFeeds();
    /**
     * @description RX获取被观察者
     * @author ydc
     * @createDate
     * @version 1.0
     */
    Flowable<Feed> getFeed(Feed feed);

    /**
     * @description 数据无效时候，需要回调处理
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void onInvalidData();

    /**
     * @description 保存数据
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void saveData(String id);

    /**
     * @description 删除数据
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void delData(String id);

    /**
     * @description 删除所有数据
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void delAllData();

    /**
     * @description 刷新数据
     * @author ydc
     * @createDate
     * @version 1.0
     */
    void refreshData();
}
