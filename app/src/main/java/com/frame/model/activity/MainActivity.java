package com.frame.model.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dhh.rxlifecycle2.RxLifecycle;
import com.dhh.websocket.Config;
import com.dhh.websocket.RxWebSocket;
import com.dhh.websocket.WebSocketInfo;
import com.dhh.websocket.WebSocketSubscriber;
import com.dhh.websocket.WebSocketSubscriber2;
import com.frame.model.BuildConfig;
import com.frame.model.R;
import com.frame.model.base.BaseActivity;
import com.frame.model.base.URLRoot;
import com.frame.model.utils.util.LogUtils;
import com.frame.model.utils.util.ToastUtils;
import com.frame.model.widget.NetworkStateView;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import okhttp3.WebSocket;
import okio.ByteString;

public class MainActivity extends BaseActivity {

    TextView textView;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.imssssss)
    PhotoView imssssss;


    private void initConfig(){
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
                    protected void onMessage(@NonNull String text) {
                        LogUtils.a("MainActivity", text);
                    }
                });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {


        initView();
        initConfig();

        imssssss.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtils.showShort("onLongClick");
                return true;
            }
        });
        textView = (TextView) findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(BuildConfig.HOST);
            }
        });
    }
    int ii = 0;
    @Override
    protected void initView() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*showLoadingView();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showContentView();
                    }
                }, 1000);*/

                startActivity(new Intent(MainActivity.this,CardViewActivity.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmptyView();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showContentView();
                    }
                }, 1000);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoNetworkView();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showContentView();
                    }
                }, 1000);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Glide.with(mContext).load("http://pic40.nipic.com/20140412/11857649_170524977000_2.jpg").into(imssssss);
                //RxWebSocket.send(URLRoot.BASE_STOCK,"sendMessage" + ++ii);
                startActivity(new Intent(MainActivity.this,ChatActivity.class));
            }
        });


    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ToastUtils.showShort(NetworkStateView.mCurrentState + "");
    }

    @Override
    protected void initData() {

    }

}
