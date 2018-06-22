package com.frame.model.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dhh.rxlifecycle2.RxLifecycle;
import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketInfo;
import com.dhh.websocket.WebSocketSubscriber;
import com.frame.model.R;
import com.frame.model.adapter.ChatAdapter;
import com.frame.model.base.BaseActivity;
import com.frame.model.base.URLRoot;
import com.frame.model.bean.MessageBean;
import com.frame.model.bean.MessageResponseBean;
import com.frame.model.network.Chats;
import com.frame.model.utils.util.LogUtils;
import com.frame.model.utils.util.StringUtils;
import com.frame.model.utils.util.ToastUtils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import okhttp3.WebSocket;

/**
 * desc： 聊天页面
 * Author：MrZ
 * CreateDate：2018/6/21
 * UpdateDate：2018/6/21
 * github：https://github.com/hz38957153
 */
public class ChatActivity extends BaseActivity {
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.recycler_list)
    RecyclerView recyclerList;

    ChatAdapter adapter;
    int i = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        //initView();
        initData();
    }

    @Override
    protected void initView() {
        //init config
        Config config = new Config.Builder()
                .setShowLog(true)           //show  log
//                .setClient(yourClient)   //if you want to set your okhttpClient
//                .setShowLog(true, "your logTag")
//                .setReconnectInterval(2, TimeUnit.SECONDS)  //set reconnect interval
//                .setSSLSocketFactory(yourSSlSocketFactory, yourX509TrustManager) // wss support
                .build();
        RxWebSocket.setConfig(config);

        /**
         *
         *如果你想将String类型的text解析成具体的实体类，比如,
         * 请使用 {@link  WebSocketSubscriber}，仅需要将泛型传入即可
         */
        RxWebSocket.get(URLRoot.BASE_STOCK)
                .compose(RxLifecycle.with(this).<WebSocketInfo>bindToLifecycle())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onOpen(WebSocket webSocket) {
                        super.onOpen(webSocket);
                        LogUtils.a("ChatActivity",true);
                        MessageBean messageBean = new MessageBean();
                        MessageBean.RequestBean requestBean = new MessageBean.RequestBean();
                        requestBean.setGroup("1");
                        requestBean.setUser_id("2022");
                        messageBean.setType("join");
                        messageBean.setRequest(requestBean);
                        Gson gson =new Gson();
                        String message = gson.toJson(messageBean);
                        RxWebSocket.send(URLRoot.BASE_STOCK,message);
                    }

                    /*@Override
                    protected void onMessage(MessageResponseBean responseBean) {
                        LogUtils.a("ChatActivity", responseBean.toString());
                        if (responseBean.getType().contains("ping")){
                            RxWebSocket.send(URLRoot.BASE_STOCK,JSONObject.fromObject("{'type':'pong'}").toString());
                            LogUtils.a("pong");
                        }
                        if (!responseBean.getType().contains("4097")){
                            if (responseBean != null){
                                if (responseBean.getResponse() != null){
                                    adapter.addData(StringUtils.isEmptyResuleString(responseBean.getResponse().getMessage()));
                                    LogUtils.a("收到消息");
                                }
                            }
                            recyclerList.scrollToPosition(adapter.getData().size()-1);
                        }
                    }*/

                    @Override
                    protected void onMessage(@NonNull String text) {
                        LogUtils.a("ChatActivity", text);
                        if (text.contains("ping")){
                            //RxWebSocket.send(URLRoot.BASE_STOCK,JSONObject.fromObject("{'type':'pong'}").toString());
                            LogUtils.a("pong");
                        }
                        if (!text.contains("4097")){
                            Gson gson = new Gson();
                            MessageResponseBean bean = gson.fromJson(text,MessageResponseBean.class);
                            if (bean != null){
                                if (bean.getResponse() != null){
                                    adapter.addData(StringUtils.isEmptyResuleString(bean.getResponse().getMessage()));
                                    LogUtils.a("收到消息");
                                }
                            }
                            recyclerList.scrollToPosition(adapter.getData().size()-1);
                        }
                    }

                    @Override
                    protected void onReconnect() {
                        super.onReconnect();
                        LogUtils.a("ChatActivity", "重连");
                    }
                });

        adapter = new ChatAdapter(R.layout.list_item);
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerList.setHasFixedSize(false);
        recyclerList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        Chats.joinChat("asd",this);
        Chats.msgListener(new Chats.ChatListener() {
            @Override
            public void onOpen(WebSocket webSocket) {
                LogUtils.a("ChatActivity",true);
            }

            @Override
            public void onNewMessage(MessageResponseBean responseBean) {
                adapter.addData(StringUtils.isEmptyResuleString(responseBean.getResponse().getMessage()));
                LogUtils.a("收到消息");
            }

            @Override
            public void onReconnect() {
                LogUtils.a("重连");
            }

            @Override
            public void onClose() {
                LogUtils.a("关闭");
            }
        });


        adapter = new ChatAdapter(R.layout.list_item);
        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerList.setHasFixedSize(false);
        recyclerList.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*//注销
        Disposable disposable = RxWebSocket.get("ws://sdfs").subscribe();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }*/

        //Chats.logout();
    }

    @OnClick({ R.id.btn_send, R.id.recycler_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                if (StringUtils.isEmpty(editText.getText().toString().trim())){
                    ToastUtils.showShort("请输入内容");
                    return;
                }

                Chats.send(editText.getText().toString().trim());
                break;
        }
    }
}
