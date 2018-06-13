package com.frame.model.base;


import com.frame.model.base.mvp.IModel;
import com.frame.model.network.RxService;

/**
 * desc：数据模型基础类
 * Author：MrZ
 * CrateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */

public abstract class BaseModel implements IModel {

    /**
     *  @author MrZ
     *  @time 2018/6/12  18:31
     *  @uptime 2018/6/12  18:31
     *  @describe  返回服务接口对象实例
     */
    public <T> T createService(final Class<T> clazz) {
        validateServiceInterface(clazz);
        return (T) RxService.RETROFIT.createRetrofit().create(clazz);
    }

    /**
     *  @author MrZ
     *  @time 2018/6/12  18:31
     *  @uptime 2018/6/12  18:31
     *  @describe  返回服务接口对象实例
     */
    public <T> void validateServiceInterface(Class<T> service) {
        if (service == null) {
            //AppToast.ShowToast("服务接口不能为空！");
        }
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }
}
