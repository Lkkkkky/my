package com.example.shop.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// 定义一个用于操作数据库的帮手类，继承自SQLiteOpenHelper
public class DBCart extends SQLiteOpenHelper {
    // 构造函数，用于在数据库还未创建时，初始化数据库名称和版本
    public DBCart(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private SQLiteDatabase dp;

    // 构造函数，用于在应用中调用，并创建或打开数据库
    public DBCart(Context context){
        // 调用父类的构造方法，设置数据库的名称和版本
        super(context,"DBCart.dp",null,1);
        // 获取数据库的写权限
        dp=this.getWritableDatabase();
    }

    // 添加一条数据到数据库
    public boolean add(String id,String username){
        ContentValues values=new ContentValues();
        // 将要插入的数据放入ContentValues中
        values.put("id",id);
        values.put("username",username);
        // 执行插入操作，并返回结果
        long i=dp.insert("cart",null,values);
        // 判断插入是否成功
        if(i>0){
            Log.d("","插入成功");
            return true;
        }
        return false;
    }

    // 从数据库中删除指定的数据
    public boolean del(String id,String username){
        // 执行删除操作，并返回结果
        long i=dp.delete("cart","id=? and username=?",new String[]{id,username});
        // 判断删除是否成功
        if(i>0){
            Log.d("","删除成功");
            return true;
        }
        return false;
    }

    // 根据用户名获取喜欢的条目的标题
    public ArrayList<String> getLikesTitle(String username){
        ArrayList<String> array=new ArrayList();
        // 查询数据库
        Cursor cursor=dp.query("cart",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            // 获取每条记录的id和username
            @SuppressLint("Range") String id=cursor.getString( cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name=cursor.getString( cursor.getColumnIndex("username"));
            // 如果用户名匹配，则将id添加到array列表中
            if(name.equals(username)){
                array.add(id );
            }
        }
        return array;
    }

    // 数据库首次创建时调用，用于创建数据库表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 定义创建数据库表的SQL语句
        String sql="create table cart(id text not null,username text not null,PRIMARY KEY(id,username))";
        sqLiteDatabase.execSQL(sql);
    }

    // 数据库版本升级时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 在这里实现数据库的升级逻辑
    }
}