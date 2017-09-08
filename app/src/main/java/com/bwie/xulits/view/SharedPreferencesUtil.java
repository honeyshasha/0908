
package com.bwie.xulits.view;

import android.content.Context;
import android.content.SharedPreferences;

import com.bwie.xulits.MyApp;


/**
 * Created by 小傻瓜 on 2017/9/1.
 */

public class SharedPreferencesUtil {
    private final static String KEY="common_data";
    /**
     * 得到SharedPreferences对象
     * @return
     */
    public static SharedPreferences getPreferences(){
        return MyApp.mcontext.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }
    /**
     * 存在一行数据uid
     * @param key
     * @param value
     */
    public static void putPreferences(String key,String value){
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.putString(key,value);
        editor.commit();
    }
    /**
     * 获取uid的数据
     * @param key
     * @return
     */
    public static String getPerferencesValue(String key){
        return getPreferences().getString(key,"");
    }

    /**
     * 清除指定数据
     * @param key
     */
    public static void clear(String key){
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清空所有数据
     */
    public static void clear(){
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.clear();
        editor.commit();
    }
}
