package com.frame.model.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frame.model.R;

/**
 * desc：
 * Author：MrZ
 * CreateDate：2018/6/21
 * UpdateDate：2018/6/21
 * github：https://github.com/hz38957153
 */
public class ChatAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public ChatAdapter(int layoutResId) {
        super(R.layout.list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item,item);
    }
}
