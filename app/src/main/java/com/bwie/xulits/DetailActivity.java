
package com.bwie.xulits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class DetailActivity extends BaseActivity {
    private WebView web_view;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent in=getIntent();
        url = in.getStringExtra("url");
        //初始化控件
        initView();
    }
    /**
     * 获取控件
     */
    private void initView() {
        web_view= (WebView) findViewById(R.id.web_view);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.loadUrl(url);
        web_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToFinishActivity();
            }
        });
    }
}
