package com.example.shop.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.shop.R;
import com.example.shop.entity.Stuff;

import java.util.ArrayList;

public class StuffAdapter extends ArrayAdapter<Stuff> {
    public StuffAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
    }
    //每个子项被滚动到屏幕内的时候会被调用
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Stuff s=getItem(position);//得到当前项的 Fruit 实例

        View view= LayoutInflater.from(getContext()).inflate(R.layout.layout_stuff,parent,false);

        TextView kind =view.findViewById(R.id.kind);
        TextView title =view.findViewById(R.id.title);
        TextView name =view.findViewById(R.id.name);
        ImageView pic=view.findViewById(R.id.pic);
        TextView price =view.findViewById(R.id.price);

        price.setText("¥"+s.getPrice());
        kind.setText("分类："+s.getKind());
        title.setText(s.getTitle());
        name.setText("名称："+s.getName());

        Drawable d=ContextCompat.getDrawable(getContext(),s.getPic());
        pic.setImageDrawable(d);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), s.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}
