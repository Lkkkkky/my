package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.shop.database.DBStuff;
import com.example.shop.entity.Stuff;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
//select *from Record
//        select *from users
//        select *from cart
//        select *from  Stuff
//theme="@style/Theme.SmallShop">
public class MainActivity extends AppCompatActivity {
    FrameLayout main;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        main= findViewById(R.id.main);
        navigation=findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        BlankFragment_Main fragment3=BlankFragment_Main.newInstance();
                        FragmentTransaction tran3=getSupportFragmentManager().beginTransaction();
                        tran3.replace(R.id.main ,fragment3);
                        tran3.commit();
                        return true;
                    case R.id.cart:
                        BlankFragment_Cart fragment2=BlankFragment_Cart.newInstance();
                        FragmentTransaction tran2=getSupportFragmentManager().beginTransaction();
                        tran2.replace(R.id.main ,fragment2);
                        tran2.commit();

                        return true;
//                    case R.id.news:
//
////                        BlankFragment_like fragment2=BlankFragment_like.newInstance();
////                        FragmentTransaction tran2=getSupportFragmentManager().beginTransaction();
////                        tran2.replace(R.id.mainFrag ,fragment2);
////                        tran2.commit();
//                        return true;
                    case R.id.me:
                        BlankFragment_me fragment1=BlankFragment_me.newInstance();
                        FragmentTransaction tran1=getSupportFragmentManager().beginTransaction();
                        tran1.replace(R.id.main ,fragment1);
                        tran1.commit();
                        return true;
                }
                return false;
            }
        });
        navigation.setSelectedItemId(R.id.home);
    }


    void init(){
        DBStuff db=new DBStuff(this);
        ArrayList<Stuff> list=db.getAll();
        for (Stuff t:
                list) {
            Log.d("--------",t.toString());
        }
        if(list.size()!=0){
            return;
        }

        Stuff s=new Stuff("本子","哈利波特的笔记本","笔记本","4.5");
        db.add(s);
        s=new Stuff("黑笔","马良的神笔","笔","3" );
        db.add(s);
        s=new Stuff("保温杯","运动保温杯","杯子","25" );
        db.add(s);
        s=new Stuff("公园同款垃圾桶","超级无敌大号垃圾桶","垃圾桶","100" );
        db.add(s);
        s=new Stuff("红轴机械键盘","红轴茶轴便携机械键盘","键盘","290" );
        db.add(s);


    }
}