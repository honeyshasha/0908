
package com.bwie.xulits;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bwie.xulits.adapter.RecyclerViewAdapter;
import com.bwie.xulits.bean.Catogray;

import java.util.ArrayList;
import java.util.List;

public class XiaZaiActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recy_view;
    private Button but_xia;
    private List<Catogray> list;
    private CheckBox checkbox;
    private ImageView image_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xia_zai);
        //初始化
        initView();
        initData();
    }
        /**
         * 初始化数据
         */
        private void initData() {
            list = new ArrayList<>();
            Catogray c = new Catogray();
            c.type = "top";
            c.name = "头条";
            list.add(c);
            c = new Catogray();
            c.type = "yule";
            c.name = "娱乐";
            list.add(c);
            c = new Catogray();
            c.type = "xinwen";
            c.name = "新闻";
            list.add(c);
            c = new Catogray();
            c.type = "redian";
            c.name = "热点";
            list.add(c);
            c = new Catogray();
            c.type = "shipin";
            c.name = "视频";
            list.add(c);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, list);
            recy_view.setLayoutManager(new LinearLayoutManager(this));
            recy_view.setAdapter(adapter);
            adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int pos, View view) {
                    checkbox = view.findViewById(R.id.off_checkBox);
                    Catogray c = list.get(pos);
                    if (checkbox.isChecked()) {
                        checkbox.setChecked(false);
                        c.state = false;
                    } else {
                        checkbox.setChecked(true);
                        c.state = true;
                    }
                    //修改原有list的数据，根据pos，设置新的对象，然后更新list
                    list.set(pos, c);
                }
            });
        }
    /**
     * 获取控件
     */
    private void initView() {
        recy_view = (RecyclerView) findViewById(R.id.recy_view);
        but_xia = (Button) findViewById(R.id.but_xia);
        image_view= (ImageView) findViewById(R.id.image_view);
        //点击下载
        but_xia.setOnClickListener(this);
        image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(list!=null&&list.size()>0){
            for (Catogray catogray:list){
                if (catogray.state){
                    loadData(catogray.type);
                }
            }
        }
        for (Catogray c:list){
            System.out.println("state====" + c.state);
        }
    }
    /**
     * 只要有wifi，就下载离线数据，下载完成后保存到数据库
     *
     * @param type
     */
    private void loadData(final String type) {
    }
}
