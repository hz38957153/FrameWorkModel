package com.frame.model.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frame.model.BuildConfig;
import com.frame.model.R;
import com.frame.model.base.BaseActivity;
import com.frame.model.utils.util.ToastUtils;
import com.frame.model.widget.NetworkStateView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;

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
    /**
     * 主 变量
     */
    // 主线程Handler
    // 用于将从服务器获取的消息显示出来
    private Handler mMainHandler;
    // Socket变量
    private Socket socket;
    // 线程池
    // 为了方便展示,此处直接采用线程池进行线程管理,而没有一个个开线程
    private ExecutorService mThreadPool;
    /**
     * 接收服务器消息 变量
     */
    // 输入流对象
    InputStream is;
    // 输入流读取器对象
    InputStreamReader isr;
    BufferedReader br;
    // 接收服务器发送过来的消息
    String response;

    /**
     * 发送消息到服务器 变量
     */
    // 输出流对象
    OutputStream outputStream;

    private String NET_STATE;

    /**
     * @author MrZ
     * @time 2018/6/14  14:03
     * @uptime 2018/6/14  14:03
     * @describe 建立连接
     */
    private void initViewSocket() {
        mThreadPool = Executors.newCachedThreadPool();
        // 利用线程池直接开启一个线程 & 执行该线程
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 创建Socket对象 & 指定服务端的IP 及 端口号
                    socket = new Socket("192.168.1.172", 8989);
                    // 判断客户端和服务器是否连接成功
                    System.out.println(socket.isConnected());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * @author MrZ
     * @time 2018/6/14  14:03
     * @uptime 2018/6/14  14:03
     * @describe 读取消息
     */
    private void readMsg() {
        // 利用线程池直接开启一个线程 & 执行该线程
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 步骤1：创建输入流对象InputStream
                    is = socket.getInputStream();
                    // 步骤2：创建输入流读取器对象 并传入输入流对象
                    // 该对象作用：获取服务器返回的数据
                    isr = new InputStreamReader(is);
                    br = new BufferedReader(isr);
                    // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
                    response = br.readLine();
                    // 步骤4:通知主线程,将接收的消息显示到界面
                    Message msg = Message.obtain();
                    msg.what = 0;
                    mMainHandler.sendMessage(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * @author MrZ
     * @time 2018/6/14  14:03
     * @uptime 2018/6/14  14:03
     * @describe 发送消息
     */
    private void sendMsg(final String msg) {
        // 利用线程池直接开启一个线程 & 执行该线程
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 步骤1：从Socket 获得输出流对象OutputStream
                    // 该对象作用：发送数据
                    outputStream = socket.getOutputStream();
                    // 步骤2：写入需要发送的数据到输出流对象中
                    outputStream.write((msg + "\n").getBytes("utf-8"));
                    // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞
                    // 步骤3：发送数据到服务端
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * @author MrZ
     * @time 2018/6/14  14:04
     * @uptime 2018/6/14  14:04
     * @describe 关闭连接
     */
    private boolean clostConn() {
        try {
            // 断开 客户端发送到服务器 的连接，即关闭输出流对象OutputStream
            outputStream.close();

            // 断开 服务器发送到客户端 的连接，即关闭输入流读取器对象BufferedReader
            br.close();

            // 最终关闭整个Socket连接
            socket.close();

            // 判断客户端和服务器是否已经断开连接
            System.out.println(socket.isConnected());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        textView = (TextView) findViewById(R.id.tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(BuildConfig.HOST);
            }
        });

        // 实例化主线程,用于更新接收过来的消息
        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:

                        break;
                }
            }
        };
        initView();
    }

    @Override
    protected void initView() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showContentView();
                    }
                },1000);
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
                },1000);
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
                },1000);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContentView();
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        ToastUtils.showShort(NetworkStateView.mCurrentState+"");
    }

    @Override
    protected void initData() {

    }

}
