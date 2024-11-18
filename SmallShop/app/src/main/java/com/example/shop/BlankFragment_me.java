package com.example.shop;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.database.DBUser;

public class BlankFragment_me extends Fragment {


    public BlankFragment_me() {
        // Required empty public constructor
    }


    public static BlankFragment_me newInstance( ) {
        BlankFragment_me fragment = new BlankFragment_me();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static RequestQueue requestQueue;//请求队列声明
    TextView myname,sentence;
    LinearLayout myrecord,change_address,myinfo,about,contact;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_blank_me, container, false);
        myrecord=v.findViewById(R.id.myrecord);
        myname=v.findViewById(R.id.myname);
        sentence=v.findViewById(R.id.sentence);
        change_address=v.findViewById(R.id.change_address);
        myinfo=v.findViewById(R.id.myinfo);
        about=v.findViewById(R.id.about);
        contact=v.findViewById(R.id.contact);

        myname.setText("欢迎使用商城，尊敬的："+Login.user.getName());

        about.setOnClickListener(this::onClick_);
        change_address.setOnClickListener(this::onClick_);
        myrecord.setOnClickListener(this::onClick_);
        contact.setOnClickListener(this::onClick_);
        myinfo.setOnClickListener(this::onClick_);

        //sentence.setText(getNeed());
        //创建请求队列
        requestQueue= Volley.newRequestQueue(getContext());
//        reqString();
        return v;
    }
    void onClick_(View v){
        switch (v.getId()){
            case R.id.myrecord:
                //Toast.makeText(getContext(), "record", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),MyRecord.class);
                startActivity(intent);
                break;
            case R.id.contact:
                joinQQ();
                break;
            case R.id.myinfo:
                Intent intent1=new Intent(getActivity(),MyInfo.class);
                startActivity(intent1);
                break;
            case R.id.change_address:
                changeAddress();
                break;
            case R.id.about:
                Intent intent3=new Intent(getActivity(),About.class);
                startActivity(intent3);
                break;
        }
    }
    //修改收货地址
    void changeAddress(){
        View view= LayoutInflater.from(getActivity() )
                .inflate(R.layout.layout_change_address, null);
        AlertDialog.Builder customBuilder= new AlertDialog.Builder(getContext())
                .setTitle("修改收货地址")
                .setView(view) ;
        AlertDialog dialog= customBuilder.create();
        dialog.show();
        //自定义按钮并注册监听器
        EditText ed_number=view.findViewById(R.id.ed_number);

        ed_number.setHint(Login.user.getAddress());
        Button btn_ok=view.findViewById(R.id.btn_ok);
        Button btn_cancel=view.findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), ed_number.getText().toString(), Toast.LENGTH_LONG).show();
                DBUser db=new DBUser(getActivity());
                Login.user.setAddress(ed_number.getText().toString());
                if(db.change(Login.user)){
                    Toast.makeText(getContext(), "修改成功！", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(), "修好失败！", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    void joinQQ() {

    }
    String address = "http://api.tianapi.com/tianqi/index?key=1475202419d304f2b1a0376fd24619a5";
    String city="北京市";
    //得到api返回的数据
    void reqString(){
        //String url=address+"&city="+city;
        String url="https://v1.hitokoto.cn/";
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("***********",s.toString());
                sentence.setText(getNeed(s));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("--------",volleyError.toString());
            }
        });//字符串
        requestQueue.add(stringRequest);
    }
    String getNeed(String jsonString){
        String n="";
        int times=0;
        char[] myCharArray;
        myCharArray = jsonString.toCharArray();
        for(int i=0;i<myCharArray.length;i++){
            if(myCharArray[i]==':' ){
                times++;
                continue;
            }
            if(times==3 && myCharArray[i]==',' ){
                break;
            }
            if(times==3){
                n+=myCharArray[i];
            }
        }
        Log.d("sentence =",n);
        return n;
    }
}