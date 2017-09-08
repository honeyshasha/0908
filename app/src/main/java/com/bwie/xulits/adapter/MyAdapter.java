
package com.bwie.xulits.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.xulits.R;
import com.bwie.xulits.bean.News;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/8/29.
 */

public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<News.ResultBean.DataBean> list;
    public MyAdapter(Context context, List<News.ResultBean.DataBean> list){
        this.context=context;
        this.list=list;
    }
    public static final int a=0;
    public static final int b=1;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return a;
        }else{
            return b;
        }
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderOne holderOne=null;
        ViewHolderTwo holderTwo=null;
        int type=getItemViewType(i);
        if(view==null){
            switch (type){
                case a:
                    holderOne=new ViewHolderOne();
                    view=View.inflate(context, R.layout.itemone,null);
                    holderOne.title=view.findViewById(R.id.title);
                    holderOne.author_name=view.findViewById(R.id.author_name);
                    holderOne.date=view.findViewById(R.id.date);
                    holderOne.imagview=view.findViewById(R.id.imagview);
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holderOne.imagview);
                    holderOne.title.setText(list.get(i).getTitle());
                    holderOne.date.setText(list.get(i).getDate());
                    holderOne.author_name.setText(list.get(i).getAuthor_name());
                    view.setTag(holderOne);
                    break;
                case b:
                    holderTwo=new ViewHolderTwo();
                    view=View.inflate(context, R.layout.itemtwo,null);
                    holderTwo.title_two=view.findViewById(R.id.title_two);
                    holderTwo.author_name_two=view.findViewById(R.id.author_name_two);
                    holderTwo.date_two=view.findViewById(R.id.date_two);
                    holderTwo.img_two=view.findViewById(R.id.img_two);

                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holderTwo.img_two);
                    holderTwo.title_two.setText(list.get(i).getTitle());
                    holderTwo.date_two.setText(list.get(i).getDate());
                    holderTwo.author_name_two.setText(list.get(i).getAuthor_name());
                    view.setTag(holderTwo);
                    break;
            }
        }else{
            switch (type){
                case a:
                    holderOne= (ViewHolderOne) view.getTag();
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holderOne.imagview);
                    holderOne.title.setText(list.get(i).getTitle());
                    holderOne.date.setText(list.get(i).getDate());
                    holderOne.author_name.setText(list.get(i).getAuthor_name());
                    break;
                case b:
                    holderTwo= (ViewHolderTwo) view.getTag();
                    ImageLoader.getInstance().displayImage(list.get(i).getThumbnail_pic_s(),holderTwo.img_two);
                    holderTwo.title_two.setText(list.get(i).getTitle());
                    holderTwo.date_two.setText(list.get(i).getDate());
                    holderTwo.author_name_two.setText(list.get(i).getAuthor_name());
                    break;
            }
        }
        return view;
    }
    class ViewHolderOne{
        public TextView title,author_name,date;
        public ImageView imagview;
    }
    class ViewHolderTwo{
        public TextView title_two,author_name_two,date_two;
        public ImageView img_two;
    }
}
