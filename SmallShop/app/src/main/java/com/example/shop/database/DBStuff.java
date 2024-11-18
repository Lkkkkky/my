package com.example.shop.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shop.entity.Stuff;

import java.util.ArrayList;

// 定义一个继承自SQLiteOpenHelper的数据库帮手类，用于管理Stuff（物品）数据的数据库操作
public class DBStuff extends SQLiteOpenHelper {
    // 构造函数，初始化数据库配置
    public DBStuff(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private SQLiteDatabase dp;

    // 重载的构造函数，用于应用中直接创建或打开数据库
    public DBStuff(Context context){
        // 调用父类的构造方法来设置数据库名称和版本
        super(context,"Stuffs.dp",null,1);
        // 获取数据库的写权限
        dp=this.getWritableDatabase();
    }

    // 向数据库中添加一个Stuff（物品）对象
    public boolean add(Stuff s){
        ContentValues values=new ContentValues();
        // 将Stuff对象的各属性值放入ContentValues中
        values.put("name",s.getName());
        values.put("title",s.getTitle());
        values.put("kind",s.getKind());
        values.put("price",s.getPrice());
        // 执行插入操作
        long i=dp.insert("Stuff",null,values);
        // 根据插入结果返回成功或失败
        if(i>0){
            Log.d("","插入成功");
            return true;
        }
        Log.d("","插入失败");
        return false;
    }

    // 从数据库中获取所有Stuff（物品）记录的列表
    public ArrayList<Stuff> getAll(){
        ArrayList<Stuff> array=new ArrayList();
        // 查询Stuff表
        Cursor cursor=dp.query("Stuff",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            // 从查询结果中提取Stuff记录的各项值
            @SuppressLint("Range") int id=cursor.getInt( cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name=cursor.getString( cursor.getColumnIndex("name"));
            @SuppressLint("Range") String title=cursor.getString( cursor.getColumnIndex("title"));
            @SuppressLint("Range") String kind=cursor.getString( cursor.getColumnIndex("kind"));
            @SuppressLint("Range") String price=cursor.getString( cursor.getColumnIndex("price"));

            // 创建Stuff对象并添加到列表中
            Stuff u=new Stuff(String.valueOf(id),name,title,kind,price);
            array.add(u);
        }
        return array;
    }

    // 根据特定的id值获取相应的Stuff（物品）对象
    public Stuff getById(int _id){
        Stuff s=new Stuff();
        // 查询Stuff表
        Cursor cursor=dp.query("Stuff",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            // 从查询结果中提取Stuff记录的各项值
            @SuppressLint("Range") int id=cursor.getInt( cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name=cursor.getString( cursor.getColumnIndex("name"));
            @SuppressLint("Range") String title=cursor.getString( cursor.getColumnIndex("title"));
            @SuppressLint("Range") String kind=cursor.getString( cursor.getColumnIndex("kind"));
            @SuppressLint("Range") String price=cursor.getString( cursor.getColumnIndex("price"));
            // 如果id值与查询结果中的id值匹配，则构造Stuff对象并返回
            if(id==_id){
                s=new Stuff(String.valueOf(id),name,title,kind,price);
                break;
            }
        }
        return s;
    }

    // 当数据库首次创建时调用，用于定义数据库表结构
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建Stuff表的SQL语句
        String sql="create table Stuff(id INTEGER primary key AUTOINCREMENT,name text not null,title text not null,kind text not null,price text not null)";
        // 执行SQL语句创建表
        sqLiteDatabase.execSQL(sql);
    }

    // 数据库版本升级时调用，用于处理数据库的升级逻辑
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 通常用于处理数据库结构的变更
    }
}