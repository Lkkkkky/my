package com.example.shop.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shop.entity.Record;

import java.util.ArrayList;

// 定义一个继承自SQLiteOpenHelper的数据库帮手类，用于管理记录数据的数据库操作
public class DBRecord extends SQLiteOpenHelper {
    // 构造函数，初始化数据库配置
    public DBRecord(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private SQLiteDatabase dp;

    // 重载的构造函数，用于应用中直接创建或打开数据库
    public DBRecord(Context context){
        // 调用父类的构造方法来设置数据库名称和版本
        super(context,"Records.dp",null,1);
        // 获取数据库的写权限
        dp=this.getWritableDatabase();
    }

    // 向数据库添加一条记录
    public boolean add(Record s){
        ContentValues values=new ContentValues();
        // 将数据模型对象的属性值放入ContentValues中
        values.put("username",s.getUsername());
        values.put("id",s.getId());
        values.put("name",s.getName());
        values.put("price",s.getPrice());
        values.put("address",s.getAddress());
        // 执行插入操作
        long i=dp.insert("Record",null,values);
        // 根据插入结果返回成功或失败
        if(i>0){
            Log.d("","插入成功");
            return true;
        }
        Log.d("","插入失败");
        return false;
    }

    // 获取特定用户的所有记录
    public ArrayList<Record> getAll(String user){
        ArrayList<Record> array=new ArrayList();
        // 查询全部记录
        Cursor cursor=dp.query("Record",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            // 从查询结果中提取记录的各项值
            @SuppressLint("Range") String username=cursor.getString( cursor.getColumnIndex("username"));
            @SuppressLint("Range") String id=cursor.getString( cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name=cursor.getString( cursor.getColumnIndex("name"));
            @SuppressLint("Range") String address=cursor.getString( cursor.getColumnIndex("address"));
            @SuppressLint("Range") String price=cursor.getString( cursor.getColumnIndex("price"));
            // 如果记录的用户名与指定的用户名匹配，则将此记录加入返回列表
            if(user.equals(username)){
                Record u=new Record(username,id,name ,price,address);
                array.add(u);
            }
        }
        return array;
    }

    // 当数据库首次创建时调用，用于定义数据库表结构
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建Record表的SQL语句
        String sql="create table Record(username text not null,id text not null,name text not null,price text not null,address text not null)";
        // 执行SQL语句创建表
        sqLiteDatabase.execSQL(sql);
    }

    // 数据库版本升级时调用，用于处理数据库的升级逻辑
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 通常用于处理数据库结构的变更
    }
}