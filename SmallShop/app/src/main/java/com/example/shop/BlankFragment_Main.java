package com.example.shop;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shop.adapter.StuffAdapter;
import com.example.shop.database.DBStuff;
import com.example.shop.entity.Stuff;

import java.util.ArrayList;

/*
 * 主要用于展示商品列表的Fragment
 */
public class BlankFragment_Main extends Fragment {

    /*
     * 这个Fragment的无参构造方法，是Fragment实例化的要求
     */
    public BlankFragment_Main() {
        // 需要一个空的公共构造函数
    }

    /*
     * 创建BlankFragment_Main的一个新实例，方便在需要时调用
     * @return 一个新的Fragment实例
     */
    public static BlankFragment_Main newInstance() {
        BlankFragment_Main fragment = new BlankFragment_Main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 定义界面上的一些视图元素
    ListView listview;
    EditText content;
    ImageView search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate布局文件，并为视图元素赋值
        View v = inflater.inflate(R.layout.fragment_blank__main, container, false);
        listview = v.findViewById(R.id.list_view);
        content = v.findViewById(R.id.content);
        search = v.findViewById(R.id.search);

        // 给搜索图标设置点击事件监听器
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取搜索内容并执行搜索方法
                String str = content.getText().toString();
                setSearch(str);
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Fragment启动时加载商品数据
        set();
    }

    /*
     * 根据用户输入的字符串进行搜索
     * @param str 搜索框输入的内容
     */
    void setSearch(String str) {
        // 与数据库交互，获取所有商品信息
        DBStuff db = new DBStuff(getContext());
        ArrayList<Stuff> arrayTemp = db.getAll();
        ArrayList<Stuff> array = new ArrayList<>();
        // 筛选符合搜索条件的商品
        for (Stuff t : arrayTemp) {
            if (t.getName().contains(str) || t.getTitle().contains(str)) {
                array.add(t);
            }
        }
        // 判断搜索结果是否为空
        if (array.size() == 0) {
            Toast.makeText(getContext(), "未搜索到关键字商品", Toast.LENGTH_SHORT).show();
            array = arrayTemp;
        }
        // 将搜索结果展示到ListView中
        StuffAdapter adapter = new StuffAdapter(getContext(), R.layout.layout_stuff, array);
        listview.setAdapter(adapter);
        ArrayList<Stuff> finalArray = array;

        // 设置列表项的点击事件，跳转到商品详情页面
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("id", (finalArray.get(i)).getId());
                intent.setClass(getContext(), Detail.class);
                startActivity(intent);
            }
        });
    }

    /*
     * 加载所有商品信息并展示到ListView中
     */
    void set() {
        DBStuff db = new DBStuff(getContext());
        ArrayList<Stuff> array = db.getAll();
        // 打印输出所有商品信息到日志
        for (Stuff t : array) {
            Log.d("--------", t.toString());
        }
        StuffAdapter adapter = new StuffAdapter(getContext(), R.layout.layout_stuff, array);
        listview.setAdapter(adapter);

        // 设置列表项的点击事件，跳转到商品详情页面
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("id", (array.get(i)).getId());
                intent.setClass(getContext(), Detail.class);
                startActivity(intent);
            }
        });
    }
}