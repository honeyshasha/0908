
package com.bwie.xulits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bwie.xulits.bean.NewsBean;
import com.bwie.xulits.fragment.FragmentCaiji;
import com.bwie.xulits.fragment.FragmentJunshi;
import com.bwie.xulits.fragment.FragmentKeJi;
import com.bwie.xulits.fragment.FragmentLeft;
import com.bwie.xulits.fragment.FragmentRigth;
import com.bwie.xulits.fragment.FragmentSheHui;
import com.bwie.xulits.fragment.FragmentTiYu;
import com.bwie.xulits.fragment.FragmentYiLiao;
import com.bwie.xulits.fragment.FragmentYuLe;
import com.bwie.xulits.fragment.MyFragment;
import com.bwie.xulits.sql.MyDao;
import com.bwie.xulits.view.HorizontalTabHost;
import com.kson.slidingmenu.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @ViewInject(R.id.tabhost) HorizontalTabHost tabhost;
    @ViewInject(R.id.main_line_leftImage)ImageView left_image;
    @ViewInject(R.id.main_line_rightImage)ImageView right_image;
    private List<Fragment> fragments;
    private List<NewsBean> listbean;
    private NewsBean bean;
    private SlidingMenu sliding;
    private ImageView dotImage;
    private List<ChannelBean> channelActivityList;
    private List<ChannelBean> channelist;
    private MyDao dao;

    private String[] false_pd={"热点","北京","视频",
            "订阅","图片","汽车", "国际","段子",
            "趣图","健康","正能量","特卖",
            "中国好声音","历史","时尚","辟谣","探索","美国",
            "搞笑","故事","奇葩","情感"};
    private NewsBean newsBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        dotImage= (ImageView) findViewById(R.id.dotImage);
        //初始化侧滑
        initMenu();
        initData();
        initView();
    }
    /**
     * 点击
     */
    private void initView() {
        left_image.setOnClickListener(this);
        right_image.setOnClickListener(this);
        dotImage.setOnClickListener(this);
    }
    /**
     * 文字
     */
    private void initData() {
        dao=new MyDao(MainActivity.this);
        fragments=new ArrayList<>();
        listbean=new ArrayList<>();
        newsBean = new NewsBean();
        listbean.add(new NewsBean("1","推荐",true));
        listbean.add(new NewsBean("2","娱乐",true));
        listbean.add(new NewsBean("3","社会",true));
        listbean.add(new NewsBean("4","体育",true));
        listbean.add(new NewsBean("3","财经",true));
        listbean.add(new NewsBean("6","科技",true));
        listbean.add(new NewsBean("7","军事",true));
        listbean.add(new NewsBean("8","医疗",true));

        fragments.add(new MyFragment());
        fragments.add(new FragmentYuLe());
        fragments.add(new FragmentSheHui());
        fragments.add(new FragmentTiYu());
        fragments.add(new FragmentCaiji());
        fragments.add(new FragmentKeJi());
        fragments.add(new FragmentJunshi());
        fragments.add(new FragmentYiLiao());
        tabhost.display(listbean,fragments);
    }
    /**
     * 侧滑菜单
     */
    private void initMenu() {
        sliding = new SlidingMenu(this);
        //左滑动
        sliding.setMenu(R.layout.fragmentleft);
        getSupportFragmentManager().beginTransaction().replace(R.id.left_replace,new FragmentLeft()).commit();
        //左右滑
        sliding.setMode(SlidingMenu.LEFT_RIGHT);
        //触摸
        sliding.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //主页面剩余的宽度
        sliding.setBehindOffsetRes(R.dimen.Basoff);

        //右滑动
        sliding.setSecondaryMenu(R.layout.fragmentrigth);
        getSupportFragmentManager().beginTransaction().replace(R.id.rigth_replace,new FragmentRigth()).commit();
        sliding.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
    }
    /**
     * 点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_line_leftImage:
                sliding.showMenu();
                break;
            case R.id.main_line_rightImage:
                sliding.showSecondaryMenu();
                break;
            case R.id.dotImage:
                dotImage_pindao();
                break;
        }
    }
    /**
     * 点击频道====>存到数据库
     */
    private void dotImage_pindao() {
        channelist=new ArrayList<>();
        String result=dao.select("pindao");
        System.out.println("====结果"+result);
        if(result==null){
            //第一次进入频道
            for (int i = 0; i <listbean.size(); i++) {
                NewsBean n=listbean.get(i);
                ChannelBean channel=new ChannelBean(n.name,n.state);
                channelist.add(channel);
            }
        }else{
            //第二次进入
            try {
                JSONArray array=new JSONArray(result);
                for (int i = 0; i <array.length(); i++) {
                    JSONObject object= (JSONObject) array.get(i);
                    String name=object.getString("name");
                    boolean state=object.getBoolean("isSelect");
                    ChannelBean beans=new ChannelBean(name,state);
                    channelist.add(beans);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ChannelActivity.startChannelActivity(MainActivity.this, channelist);
    }
    //回调的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断回传码是否相同
        if(resultCode==101){
            Log.i("xxx","回传!");
            String reqult_key=data.getStringExtra("json");
            dao.delect("pindao");//清楚数据
            dao.add("pindao",reqult_key);//添加到数据
            System.out.println("====保存"+reqult_key);
            listbean.clear();
            ArrayList<Fragment> list=new ArrayList<>();
            try {
                JSONArray arr=new JSONArray(reqult_key);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj= (JSONObject) arr.get(i);
                    String name=obj.getString("name");
                    boolean state=obj.getBoolean("isSelect");
                    if(state){
                        list.add(fragments.get(i));
                        NewsBean newsBean=new NewsBean(name,state);
                        listbean.add(newsBean);
                    }
                }
                tabhost.remove();
                tabhost.display(listbean,list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
