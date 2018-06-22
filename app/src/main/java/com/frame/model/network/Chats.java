package com.frame.model.network;

import android.content.Context;

import com.dhh.rxlifecycle2.RxLifecycle;
import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketInfo;
import com.dhh.websocket.WebSocketSubscriber2;
import com.frame.model.base.BaseData;
import com.frame.model.base.URLRoot;
import com.frame.model.bean.MessageBean;
import com.frame.model.bean.MessageResponseBean;
import com.frame.model.utils.util.LogUtils;
import com.frame.model.utils.util.Utils;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * desc：
 * Author：MrZ
 * CreateDate：2018/6/20
 * UpdateDate：2018/6/20
 * github：https://github.com/hz38957153
 */
public abstract class Chats extends WebSocketListener {

    private static String SEND_MESSAGE = "send_message";
    private static String GET_LIST = "get_list";
    private static String PING = "ping";

    private static ChatListener chatListener;
    private static String roomId;
    private static WebSocket webSocket;

    public static void joinChat(String id, Context mContext){
        roomId = id;
        Config config = new Config.Builder()
                .setShowLog(true)           //show  log
                .setClient(new OkHttpClient.Builder()
                        .readTimeout(3,  TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())   //if you want to set your okhttpClient
//                .setShowLog(true, "your logTag")
                .setReconnectInterval(2, TimeUnit.SECONDS)  //set reconnect interval
//                .setSSLSocketFactory(yourSSlSocketFactory, yourX509TrustManager) // wss support
                .build();
        RxWebSocket.setConfig(config);
        /**
         *
         *如果你想将String类型的text解析成具体的实体类，比如,
         * 请使用 {@link  WebSocketSubscriber2}，仅需要将泛型传入即可
         */
        RxWebSocket.get(URLRoot.BASE_STOCK)
                .compose(RxLifecycle.with(mContext).<WebSocketInfo>bindToLifecycle())
                .subscribe(new WebSocketSubscriber2<MessageResponseBean>() {
                    @Override
                    protected void onOpen(WebSocket web) {
                        super.onOpen(web);
                        webSocket = web;
                        LogUtils.a("ChatActivity",true);
                        MessageBean messageBean = new MessageBean();
                        MessageBean.RequestBean requestBean = new MessageBean.RequestBean();
                        requestBean.setGroup("1");
                        requestBean.setUser_id(BaseData.getInstance().getUid()+"");
                        messageBean.setType("join");
                        messageBean.setRequest(requestBean);
                        Gson gson =new Gson();
                        String message = gson.toJson(messageBean);
                        RxWebSocket.send(URLRoot.BASE_STOCK,message);
                        if (chatListener != null){
                            chatListener.onOpen(webSocket);
                        }

                    }

                    @Override
                    protected void onMessage(MessageResponseBean responseBean) {
                        LogUtils.a("ChatActivity","onMessage");
                        if (responseBean != null){
                            if (responseBean.getType().equals(SEND_MESSAGE)){
                                if (responseBean.getResponse() != null){
                                    if (chatListener != null)
                                        chatListener.onNewMessage(responseBean);
                                }
                            }
                            else if(responseBean.getType().contains(PING)){
                                //RxWebSocket.send(URLRoot.BASE_STOCK, JSONObject.fromObject("{'type':'pong'}").toString());
                            }
                        }

                    }

                    @Override
                    protected void onReconnect() {
                        super.onReconnect();
                        if (chatListener != null)
                            chatListener.onReconnect();
                    }

                    @Override
                    protected void onClose() {
                        super.onClose();
                        if (chatListener != null)
                            chatListener.onClose();
                    }
                });
    }

    public interface ChatListener {
        void onOpen(WebSocket webSocket);
        void onNewMessage(MessageResponseBean responseBean);
        void onReconnect();
        void onClose();
    }

    public static void msgListener(ChatListener Listener){
        chatListener = Listener;
    }

    public static void logout(){
        chatListener = null;
        Disposable disposable = RxWebSocket.get("ws://sdfs").subscribe();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void send(String msg){
        MessageBean messageBean = new MessageBean();
        MessageBean.RequestBean requestBean = new MessageBean.RequestBean();
        requestBean.setGroup("1");
        requestBean.setUser_id("2022");
        requestBean.setMessage(msg);
        messageBean.setType(SEND_MESSAGE);
        messageBean.setRequest(requestBean);
        if (webSocket != null)
        webSocket.send(new Gson().toJson(messageBean));
        LogUtils.a("send");

    }

}
