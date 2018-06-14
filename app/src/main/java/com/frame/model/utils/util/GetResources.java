package com.frame.model.utils.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by MrZ on 2017/11/15. 10:12
 * project delin
 * Explain 获取资源工具类
 */

public class GetResources {

    /**
     * @author MrZ
     * @time 2017/11/15  10:14
     * @notes 获取颜色
     */
    public static int getColor(int color){
        return ContextCompat.getColor(Utils.getApp(),color);
    }

    /**
     * @author MrZ
     * @time 2017/11/15  10:21
     * @notes 获取drawable资源
     */
    public static Drawable getDrawable(int drawable){
        return ContextCompat.getDrawable(Utils.getApp(),drawable);
    }
}
