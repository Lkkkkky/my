package com.example.shop.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.shop.BlankFragment_Cart;
import com.example.shop.Login;
import com.example.shop.R;
import com.example.shop.entity.Record;
import com.example.shop.entity.Stuff;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<Stuff> {
    public CartAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
    }
    //每个子项被滚动到屏幕内的时候会被调用
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stuff s=getItem(position);//得到当前项的 Stuff 实例

        View view= LayoutInflater.from(getContext()).inflate(R.layout.layout_cart,parent,false);

        TextView title =view.findViewById(R.id.title);
        TextView price =view.findViewById(R.id.price);
        ImageView pic=view.findViewById(R.id.pic);
        CheckBox box=view.findViewById(R.id.box);
        title.setText(s.getTitle());
        price.setText("¥"+s.getPrice());

        Drawable d= ContextCompat.getDrawable(getContext(),s.getPic());
        pic.setImageDrawable(d);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), s.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        /*
        点击多选框，把此商品的价格加去或减去，显示在购物车页面的总价total中
        并且添加或删除，商品到暂时的购买记录中。
        */
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Double total=Double.valueOf(BlankFragment_Cart._total.getText().toString());
                if(box.isChecked()){
                    //Toast.makeText(getContext(), "选中了", Toast.LENGTH_SHORT).show();
                    Record r=new Record(Login.user.getName(), s.getId(), s.getName(),s.getPrice(),Login.user.getAddress());
                    BlankFragment_Cart.record.add(r);
                    total+=Double.valueOf(r.getPrice());
                }else{
                    for (Record r:
                            BlankFragment_Cart.record) {
                        Log.d("未删除时--------",r.toString());
                    }

                    int i=0;
                    for (Record r:
                            BlankFragment_Cart.record) {
                        if( r.getId().equals(s.getId())  ){
                            total-=Double.valueOf(r.getPrice());
                            BlankFragment_Cart.record.remove(i);
                            break;
                        }
                        i++;
                    }

                    for (Record r:
                            BlankFragment_Cart.record) {
                        Log.d("已删除后***************",r.toString());
                    }

                }
                BlankFragment_Cart._total.setText( String.format("%.2f",total));
            }
        });
        return view;
    }

}

