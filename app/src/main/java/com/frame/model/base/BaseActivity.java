package com.frame.model.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * desc：
 * Author：MrZ
 * CreateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */
public class BaseActivity extends AppCompatActivity {

    public static Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
    }
}
