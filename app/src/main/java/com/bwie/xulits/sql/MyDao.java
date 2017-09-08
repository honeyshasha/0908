
package com.bwie.xulits.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 小傻瓜 on 2017/9/7.
 */

public class MyDao {
    private Context context;
    private MySQLiteOpenHelper helper=null;
    public MyDao(Context context){
        super();
        this.context=context;
        helper=new MySQLiteOpenHelper(context);
    }

    /**
     * 添加数据
     * @param type
     * @param json
     */
    public void add(String type,String json){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type",type);
        values.put("json",json);
        db.insert("news",null,values);
        db.close();
        System.out.println("保存成功");
    }
    /**
     * 查询数据
     */
    public String select(String type){
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor=db.query("news",null,"type=?",new String[]{type},null,null,null);
        while (cursor.moveToNext()){
            String json=cursor.getString(cursor.getColumnIndex("json"));
            return json;
        }
        return null;
    }
    /**
     * 删除数据
     */
    public void delect(String type){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.delete("news","type=?",new String[]{type});
        String select=select(type);
        if(select==null){
            System.out.print("==删除查询为空！");
        }else{
            System.out.print("==删除后的查询未成功！");
        }
    }
}
