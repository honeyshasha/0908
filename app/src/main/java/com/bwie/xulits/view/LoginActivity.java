
package com.bwie.xulits.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xulits.R;
import com.bwie.xulits.bean.User;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    @ViewInject(R.id.imageview_X) ImageView imageview_X;
    private int TIME=10;
    private final int SECOND=1000;
    private EventHandler eventHandler;

    Handler handler=new Handler();
    Runnable timeRunable=new Runnable() {
        @Override
        public void run() {
            TIME--;
            if(TIME==0){
                handler.removeCallbacks(this);
                TIME=10;
                sendtime.setEnabled(true);
                sendtime.setText("再次获取");
            }else{
                sendtime.setEnabled(false);
                sendtime.setTextColor(getResources().getColor(R.color.colorPrimary));
                sendtime.setText(TIME+"s");
                handler.postDelayed(this,SECOND);
            }
        }
    };
    private EditText ed_phone;
    private EditText ed_input;
    private TextView sendtime;
    private Button but_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initData();
        //处理view方法
        initView();
        registerSMS();
    }
    //初始化控件
    private void initData() {
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_input = (EditText) findViewById(R.id.ed_input);
        sendtime = (TextView) findViewById(R.id.sendtTime);
        but_send = (Button) findViewById(R.id.but_send);
        sendtime.setOnClickListener(this);
        but_send.setOnClickListener(this);
    }
    private void registerSMS() {
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (result == SMSSDK.RESULT_COMPLETE) {//只有回服务器验证成功，才能允许用户登录
                        //回调完成,提交验证码成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "服务器验证成功", Toast.LENGTH_SHORT).show();
                                    User user = new User();
                                    user.uid = ed_phone.getText().toString();
                                    user.phone = ed_phone.getText().toString();
                                    SharedPreferencesUtil.putPreferences("uid", user.uid);
                                    SharedPreferencesUtil.putPreferences("phone", user.phone);
                                }
                            });
                        }
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        };
        // 注册监听器
       // SMSSDK.registerEventHandler(eventHandler);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.but_send:
                vetif();
                SMSSDK.submitVerificationCode("86",ed_phone.getText().toString(),ed_input.getText().toString());
                break;
            case R.id.sendtTime:
                if(TextUtils.isEmpty(ed_phone.getText().toString())){
                    Toast.makeText(LoginActivity.this,"请输入手机号！",Toast.LENGTH_SHORT).show();
                    return;
                }
                handler.postDelayed(timeRunable,SECOND);
                SMSSDK.getVerificationCode("86",ed_phone.getText().toString());
                break;

        }
    }
    private void vetif() {
        if(TextUtils.isEmpty(ed_phone.getText().toString())){
            Toast.makeText(LoginActivity.this,"请输入手机号！",Toast.LENGTH_SHORT).show();
            return;
        }if(TextUtils.isEmpty(ed_input.getText().toString())){
            Toast.makeText(LoginActivity.this,"请输入验证码！",Toast.LENGTH_SHORT).show();
            return;
        }
    }
    /**
     * 处理view方法
     */
    private void initView() {
        imageview_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//关闭此页面
            }
        });
    }
}
