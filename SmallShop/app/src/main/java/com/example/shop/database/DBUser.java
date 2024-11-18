package com.example.shop.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shop.entity.User;

// 定义一个继承自SQLiteOpenHelper的数据库帮手类，用于管理用户数据的数据库操作
public class DBUser extends SQLiteOpenHelper {
    // 构造函数，初始化数据库配置
    public DBUser(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private SQLiteDatabase dp;

    // 重载的构造函数，用于应用中直接创建或打开数据库
    public DBUser(Context context){
        // 调用父类的构造方法来设置数据库名称和版本
        super(context,"users.dp",null,1);
        // 获取数据库的写权限
        dp=this.getWritableDatabase();
    }

    // 向数据库中添加一个用户对象
    public boolean add(User u ){
        ContentValues values=new ContentValues();
        // 将用户对象的属性值放入ContentValues中
        values.put("name",u.getName());
        values.put("psw",u.getPsw());
        values.put("address",u.getAddress());
        // 执行插入操作
        long i=dp.insert("users",null,values);
        // 根据插入结果返回成功或失败
        if(i>0){
            Log.d("","插入成功");
            return true;
        }
        return false;
    }

    // 更新用户信息（例如地址）
    public  boolean change(User u){
        ContentValues values=new ContentValues();
        // 更新操作所要更新的字段值
        values.put("address",u.getAddress());
        // 执行更新操作
        long i=dp.update("users",values,"name=?",new String[]{u.getName()});
        // 根据更新结果返回成功或失败
        if(i>0){
            Log.d("","修改成功");
            return true;
        }
        return false;
    }

    // 检查用户名和密码是否匹配，用于登录验证
    public boolean check(String n,String p){
        // 查询用户数据
        Cursor cursor=dp.query("users",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            // 从查询结果中提取用户名和密码
            @SuppressLint("Range") String name=cursor.getString( cursor.getColumnIndex("name"));
            @SuppressLint("Range") String psw=cursor.getString( cursor.getColumnIndex("psw"));
            // 如果提供的用户名和密码匹配，则返回true
            if(n.equals(name) && p.equals(psw)){
                return true;
            }
        }
        return false;
    }

    // 根据用户名获取用户对象
    public User get(String n ){
        User u=new User();
        // 查询用户数据
        Cursor cursor=dp.query("users",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            // 从查询结果中提取用户信息
            @SuppressLint("Range") String name=cursor.getString( cursor.getColumnIndex("name"));
            @SuppressLint("Range") String psw=cursor.getString( cursor.getColumnIndex("psw"));
            @SuppressLint("Range") String address=cursor.getString( cursor.getColumnIndex("address"));
            // 如果提供的用户名匹配，则创建并返回User对象
            if(n.equals(name) ){
                u=new User(name,psw,address);
                break;
            }
        }
        return u;
    }

    // 当数据库首次创建时调用，用于定义数据库表结构
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建users表的SQL语句
        String sql="create table users(name text primary key,psw text not null,address text not null)";
        // 执行SQL语句创建表
        sqLiteDatabase.execSQL(sql);
    }

    // 数据库版本升级时调用，用于处理数据库的升级逻辑
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 通常用于处理数据库结构的变更
    }
}