package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyInfo extends AppCompatActivity {
    TextView name,psw,address;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        name= findViewById(R.id.name);
        psw= findViewById(R.id.psw);
        address= findViewById(R.id.address);

        name.setText(Login.user.getName());
        psw.setText(Login.user.getPsw());
        address.setText(Login.user.getAddress());
        back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}