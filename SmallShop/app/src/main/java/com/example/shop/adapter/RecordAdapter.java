package com.example.shop.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.shop.R;
import com.example.shop.database.DBStuff;
import com.example.shop.entity.Record;
import com.example.shop.entity.Stuff;


import java.util.ArrayList;

public class RecordAdapter extends ArrayAdapter<Record> {
    public RecordAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
    }
    //每个子项被滚动到屏幕内的时候会被调用
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Record r=getItem(position);//得到当前项的 Fruit 实例

        View view= LayoutInflater.from(getContext()).inflate(R.layout.layout_record,parent,false);

        String id=r.getId();
        DBStuff db=new DBStuff(getContext());
        Stuff stuff=db.getById(Integer.valueOf(id));

        TextView title =view.findViewById(R.id.title);
        TextView address =view.findViewById(R.id.address);
        ImageView pic=view.findViewById(R.id.pic);
        TextView price =view.findViewById(R.id.price);

        price.setText("¥"+r.getPrice());
        title.setText(stuff.getTitle());
        address.setText("收货地址："+r.getAddress());

        Drawable d= ContextCompat.getDrawable(getContext(),stuff.getPic());
        pic.setImageDrawable(d);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), s.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
