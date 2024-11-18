package com.example.shop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.adapter.CartAdapter;
import com.example.shop.database.DBCart;
import com.example.shop.database.DBRecord;
import com.example.shop.database.DBStuff;
import com.example.shop.entity.Record;
import com.example.shop.entity.Stuff;

import java.util.ArrayList;


public class BlankFragment_Cart extends Fragment {


    public BlankFragment_Cart() {
        // Required empty public constructor
    }

    public static BlankFragment_Cart newInstance( ) {
        BlankFragment_Cart fragment = new BlankFragment_Cart();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static ArrayList<Record> record=new ArrayList<>();
    ListView listview;
    Button buy;
    TextView empty;
    public static TextView _total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_blank__cart, container, false);
        listview=v.findViewById(R.id.list);
        empty=v.findViewById(R.id.empty);
        _total=v.findViewById(R.id.total);
        _total.setText("0.00");
        buy=v.findViewById(R.id.buy_cart);
        record=new ArrayList<>();
        set();
        //点击购买
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BlankFragment_Cart.record.size()==0){
                    Toast.makeText(getContext(), "你还未选择任何商品", Toast.LENGTH_SHORT).show();
                    return ;
                }
                DBRecord db=new DBRecord(getContext());
                DBCart dbcart=new DBCart(getContext());
                //String address="广城理";
                double total=0;
                for (Record r:
                        BlankFragment_Cart.record) {
                    //添加购买记录到数据库
                    if(  db.add(r)  ){
                        total+=Double.valueOf(r.getPrice());

                        Log.d("成功添加购买记录-----",r.toString());
                        //从购物车数据库中删除已经购买的商品
                        if(dbcart.del(r.getId(),r.getUsername())){
                            Log.d("成功从购物车中删除-----",r.toString());
                        }
                    }else{
                        Log.d("失败添加购买记录-----",r.toString());
                    }
                }
                record=new ArrayList<>();
                _total.setText("0.00");
                Toast.makeText(getContext(), "价格总计"+String.format("%.2f",total)+"元，购买成功！", Toast.LENGTH_SHORT).show();
                set();

            }
        });
        return v;
    }
    //从数据库中读取加入购物车的商品
    void set(){

        DBCart db=new DBCart(getContext());
        DBStuff dbStuff=new DBStuff(getContext());
        ArrayList<Stuff> array=new ArrayList<>();
        //获取购物车中商品的所有id
        ArrayList<String> allId=db.getLikesTitle(Login.user.getName());
        if(allId.size()==0){
            empty.setVisibility(View.VISIBLE);
        }else{
            empty.setVisibility(View.GONE);
        }
        //通过id依次获取商品信息
        for (String s:
                allId) {
            Stuff stuff=dbStuff.getById(Integer.valueOf(s));
            array.add(stuff);
        }
        for (Stuff t:
                array) {
            Log.d("--------",t.toString());
        }
        CartAdapter adapter=new CartAdapter(getContext(),R.layout.layout_cart,array);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s=( array.get(i)).getName();
                Toast.makeText(getContext(), "name："+s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}