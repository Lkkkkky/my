package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.database.DBCart;
import com.example.shop.database.DBRecord;
import com.example.shop.database.DBStuff;
import com.example.shop.entity.Record;
import com.example.shop.entity.Stuff;

public class Detail extends AppCompatActivity {
    TextView title,name,price,kind;
    ImageView image,back;
    Button buy,addcart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title=findViewById(R.id.title);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        kind=findViewById(R.id.kind);
        image=findViewById(R.id.image);
        buy=findViewById(R.id.buy);
        addcart=findViewById(R.id.add);
        back=findViewById(R.id.back);
        Intent intent=getIntent();
        String id=intent.getStringExtra("id" );
        DBStuff db=new DBStuff(this);
        Stuff s=db.getById(Integer.valueOf(id));
        if(s.getId().equals("-1")){
            Toast.makeText(this, "有bug", Toast.LENGTH_LONG).show();
        }else{
            title.setText(s.getTitle());
            name.setText("名称："+s.getName());
            price.setText("价格：¥"+s.getPrice());
            image.setBackground(getDrawable(s.getPic()));
            kind.setText("分类："+s.getKind());
        }
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBCart db=new DBCart(getBaseContext());
                if(  db.add( s.getId() , Login.user.getName())  ){
                    Toast.makeText(Detail.this, "加入购物车成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Detail.this, "加入购物车失败，或已在购物车中", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBRecord db=new DBRecord(getBaseContext());
                //String address="广城理";
                Record r=new Record(Login.user.getName(),s.getId(),name.getText().toString(),s.getPrice(),Login.user.getAddress());
                if(  db.add(r)  ){
                    Toast.makeText(Detail.this, "价格"+s.getPrice()+"元，购买成功！", Toast.LENGTH_SHORT).show();
                    Log.d("-----",r.toString());
                }else{
                    Toast.makeText(Detail.this, "购买失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}