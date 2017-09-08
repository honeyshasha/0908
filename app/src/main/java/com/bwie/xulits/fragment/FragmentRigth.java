
package com.bwie.xulits.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bwie.xulits.R;
import com.bwie.xulits.XiaZaiActivity;

/**
 * Created by 小傻瓜 on 2017/8/31.
 */
public class FragmentRigth extends Fragment implements View.OnClickListener{
    private View view;
    private RelativeLayout tupian;
    private RelativeLayout lixian;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.itemfragmenttwo,container,false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        tupian = view.findViewById(R.id.tupian);
        tupian.setOnClickListener(FragmentRigth.this);
        lixian=view.findViewById(R.id.lixian);
        lixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), XiaZaiActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 点击
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tupian:
                new AlertDialog.Builder(getActivity()).setSingleChoiceItems(new String[]{"大图", "无图"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            //需要存储本地状态，选择的什么网络流量节省方式，"wifi"="hasnet"，加载大图
                        } else if (i == 1) {
                            //需要存储本地状态，选择的什么网络流量节省方式，"wifi"="nonet"，不加载图
                        }
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
        }
    }
}
