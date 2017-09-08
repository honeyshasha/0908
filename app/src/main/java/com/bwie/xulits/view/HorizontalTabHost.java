
package com.bwie.xulits.view;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.xulits.R;
import com.bwie.xulits.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小傻瓜 on 2017/8/31.
 */

public class HorizontalTabHost extends LinearLayout implements ViewPager.OnPageChangeListener{
    private Context context;
    private int myColor;
    private HorizontalScrollView horizontal;
    private ViewPager viewpager;
    private LinearLayout linelayout;
    private List<NewsBean> listbean;
    private List<Fragment> listfragment;
    private List<TextView> listview;
    private int count;
    private Myadapter adapter;

    public HorizontalTabHost(Context context) {
       this(context,null);
    }

    public HorizontalTabHost(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalTabHost(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        //初始化控件和view
        init(context,attrs);
    }
    private void init(Context context,AttributeSet attrs) {
        TypedArray typed=context.obtainStyledAttributes(attrs,R.styleable.HorizontalTabHost);
        //自定义颜色
        myColor=typed.getColor(R.styleable.HorizontalTabHost_top ,0x9999);
        typed.recycle();//回收
        //初始化view
        initView();
    }
    private void initView() {
        View view= LayoutInflater.from(context).inflate(R.layout.horizontaltabhost,this,true);
        horizontal = view.findViewById(R.id.horizontal);
        viewpager = view.findViewById(R.id.viewpager);
        linelayout = view.findViewById(R.id.linelayout);
        //viewpager
        viewpager.addOnPageChangeListener(this);
    }
    public void display(List<NewsBean> listbean, List<Fragment> fragments){
        this.listbean=listbean;
        this.count =listbean.size();
        this.listfragment=fragments;
        listview=new ArrayList<>(count);
        //绘制页面
        disUI();
    }
    /*
    //绘制页面
     */
    private void disUI() {
        dishorizontal();
        disviewpager();
    }

    /**
     * viewpager绘制
     */
    private void disviewpager() {
        adapter = new Myadapter(((FragmentActivity)context).getSupportFragmentManager());
        viewpager.setAdapter(adapter);
    }
    /**
     * 水平绘制
     */
    private void dishorizontal() {
        linelayout.setBackgroundColor(myColor);
        for(int i=0;i<count;i++){
            NewsBean bean=listbean.get(i);
            final TextView tv= (TextView) View.inflate(context,R.layout.new_top_item,null);
            tv.setText(bean.name);
            final int fianlI=i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewpager.setCurrentItem(fianlI);
                    //点击文字让文字居中
                    clicknum(tv);
                }
            });
            linelayout.addView(tv);
            listview.add(tv);
        }
        //默认设置第一项为选中
        listview.get(0).setSelected(true);
    }
    /**
     * 点击文字让文字居中
     * 移动view对象到中间
     */
    private void clicknum(TextView tv) {
        DisplayMetrics dispaly=getResources().getDisplayMetrics();
        int width=dispaly.widthPixels;
        int[] location=new int[2];
        tv.getLocationInWindow(location);
        int rwidth=tv.getWidth();
        horizontal.smoothScrollBy(location[0]+rwidth/2-width/2,0);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if(linelayout!=null&&linelayout.getChildCount()>0){
            for (int i=0;i<linelayout.getChildCount();i++){
                if(i==position){
                    linelayout.getChildAt(i).setSelected(true);
                }else{
                    linelayout.getChildAt(i).setSelected(false);
                }
            }
        }
        //移动居中
        clicknum(listview.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    class Myadapter extends FragmentPagerAdapter{

        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listfragment.get(position);
        }

        @Override
        public int getCount() {
            return listfragment.size();
        }
    }
    public void remove(){
        linelayout.removeAllViews();
    }
}
