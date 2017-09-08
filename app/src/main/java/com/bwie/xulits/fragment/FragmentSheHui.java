package com.bwie.xulits.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bwie.xulits.DetailActivity;
import com.bwie.xulits.R;
import com.bwie.xulits.adapter.MyAdapter;
import com.bwie.xulits.api.HttpApi;
import com.bwie.xulits.bean.News;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import view.xlistview.XListView;

/**
 * Created by 小傻瓜 on 2017/9/6.
 */
@ContentView(R.layout.fragment_item_three)
public class FragmentSheHui extends Fragment implements XListView.IXListViewListener {
    private View view;
    @ViewInject(R.id.xlist)
    private XListView xlist;
    private List<News.ResultBean.DataBean> list;
    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view= x.view().inject(this,inflater,container);
        }
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //适配器
        adapter();
    }

    private void adapter() {
        RequestParams params=new RequestParams(HttpApi.GET_URL);
        params.addQueryStringParameter("key",HttpApi.KEY);
        params.addBodyParameter("type",HttpApi.TYPE3);
        params.setCacheMaxAge(1000*60);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析
                Gson gson=new Gson();
                News news = gson.fromJson(result.toString(), News.class);
                list = news.getResult().getData();
                for (News.ResultBean.DataBean n:list){
                    Log.i("头条",n.getTitle()+" "+n.getAuthor_name()+" "+n.getThumbnail_pic_s()+" "+n.getDate());
                }
                adapter = new MyAdapter(getContext(), list);
                xlist.setAdapter(adapter);
                //刷新
                xlist.setPullRefreshEnable(true);
                xlist.setPullLoadEnable(true);
                xlist.setXListViewListener(FragmentSheHui.this);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getContext(),"请求错误",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
        xlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("url",list.get(i-1).getUrl());
                startActivity(intent);
            }
        });
    }
    private void addData(){
        RequestParams params=new RequestParams(HttpApi.GET_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                News news = gson.fromJson(result.toString(), News.class);
                List<News.ResultBean.DataBean> newsBeen = news.getResult().getData();
                //将newsbean放入集合中
                list.addAll(newsBeen);
                //适配器刷新
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }
    private Handler handler=new Handler();
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //获取数据并且添加到适配器的方法
                adapter();
                Log.i("xxx","刷新成功！");
                //停止刷新
                xlist.stopRefresh();
                //停止加载
                xlist.stopLoadMore();
            }
        },2000);
    }
    //上拉加载
    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //添加数据的方法
                addData();
                Log.i("xxx","添加成功！");
                //停止刷新
                xlist.stopRefresh();
                //停止加载
                xlist.stopLoadMore();
            }
        },3000);
    }
}
