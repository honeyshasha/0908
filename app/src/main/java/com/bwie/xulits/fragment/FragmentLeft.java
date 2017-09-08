
package com.bwie.xulits.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.xulits.MainActivity;
import com.bwie.xulits.R;
import com.bwie.xulits.view.LoginActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Created by 小傻瓜 on 2017/8/31.
 */
public class FragmentLeft extends Fragment implements View.OnClickListener {
    private View view;
    private Button login;
    private TextView yejian_size;
    private ImageView yejian;
    private ImageView qq_dot;
    private Tencent mtencent;
    private static final String TAG="MainActivity";
    private static final String App_ID="1105602574";//官方获取的APPID
    private BaseUiListener baseUiListener;
    private UserInfo mUserInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.itemfragmentone,container,false);
        }
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //QQ第三登录 //传入参数APPID和全局Context上下文
        mtencent=Tencent.createInstance(App_ID,getActivity().getApplicationContext());

        login = getActivity().findViewById(R.id.login);
        yejian=getActivity().findViewById(R.id.yejian);
        qq_dot=getActivity().findViewById(R.id.qq_dot);
        yejian_size=getActivity().findViewById(R.id.yejian_size);
        yejian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mode=getResources().getConfiguration().uiMode& Configuration.UI_MODE_NIGHT_MASK;
           if(yejian_size.getText().equals("夜间")){
                    yejian_size.setText("日间");
                    ((MainActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else if(yejian_size.getText().equals("日间")){
                    yejian_size.setText("夜间");
                    ((MainActivity)getActivity()).getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        /**
         * qq第三方登录
         */
        qq_dot.setOnClickListener(this);
    }

    /**
     * 点击qq第三方登录
     * @param view
     */
    @Override
    public void onClick(View view) {
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        baseUiListener=new BaseUiListener();
        //all表示获取所有权限
        mtencent.login(getActivity(),"all",baseUiListener);
    }


    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    public class BaseUiListener implements IUiListener{

        @Override
        public void onComplete(Object o) {
            Toast.makeText(getActivity(),"授权成功！",Toast.LENGTH_SHORT).show();;
            Log.e(TAG,"response:"+o);
            JSONObject object= (JSONObject) o;
            try {
                String openID = object.getString("openid");
                String accessToken = object.getString("access_token");
                String expires = object.getString("expires_in");
                mtencent.setOpenId(openID);
                mtencent.setAccessToken(accessToken,expires);

                QQToken qqToken = mtencent.getQQToken();
                mUserInfo = new UserInfo(getActivity(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,baseUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
