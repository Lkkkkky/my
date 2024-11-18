package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.database.DBUser;
import com.example.shop.entity.User;

import java.util.Random;

public class Login extends AppCompatActivity {
    public static User user=new User();
    Button login;
    CheckBox remember;
    Button register;
    TextView test;
    EditText name,psw,number;
    String num="1234";
    SharedPreferences sharedPreferences;
    String getNumber(){
        String str="abcdefghijklmnopqrstuvwxyz0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<4;i++){
            int number=random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        remember=findViewById(R.id.remember);
        psw=findViewById(R.id.psw_);
        test=findViewById(R.id.test);
        name=findViewById(R.id.name_);
        number=findViewById(R.id.number);
        register=findViewById(R.id.register);

        sharedPreferences = getSharedPreferences("MyAccount",MODE_PRIVATE);
        name.setText(sharedPreferences.getString("username",null));
        psw.setText(sharedPreferences.getString("password",null));
        if(  name.getText().toString().isEmpty()){
            remember.setChecked(false);
        }else{
            remember.setChecked(true);
        }

        user=new User();
        DBUser db=new DBUser(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(!number.getText().toString().equals(num)){
//                    Toast.makeText(Login.this, "验证码错误", Toast.LENGTH_SHORT).show();
//                    return ;
//                }
                String n=name.getText().toString();
                String p=psw.getText().toString();
                if(db.check(n,p)){
                    //Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    user=db.get(n);
                    if(remember.isChecked()){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",name.getText().toString());
                        editor.putString("password",psw.getText().toString());
                        editor.commit();
                    }
                    else{
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",null);
                        editor.putString("password",null);
                        editor.commit();
                    }
                    Intent intent = new Intent();
                    intent.setClass(Login.this ,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this, "登录失败，用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Login.this ,Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        num=getNumber();
        test.setText("验证码："+num);
    }
}