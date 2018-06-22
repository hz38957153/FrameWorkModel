package com.frame.model.base;

/**
 * desc：
 * Author：MrZ
 * CreateDate：2018/6/22
 * UpdateDate：2018/6/22
 * github：https://github.com/hz38957153
 */
public class BaseData {

    private static BaseData instance = null;
    /**
     * 私有默认构造子
     */
    private BaseData(){}
    /**
     * 静态工厂方法
     */
    public static synchronized BaseData getInstance(){
        if(instance == null){
            instance = new BaseData();
        }
        return instance;
    }

    private int Uid;
    private String UName;
    private String UIcon;

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = 2022;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUIcon() {
        return UIcon;
    }

    public void setUIcon(String UIcon) {
        this.UIcon = UIcon;
    }
}
