package com.example.wangsz;

/**
 * Created by asus on 2017/6/10.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wangsz.util.Util;
import com.example.wangsz.service.UserService;

import com.example.wangsz.R;

public class LoginActivity extends Activity {
    EditText username;
    EditText password;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViews();
    }

    private void findViews() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        login.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                //Log.i("TAG",name+"_"+pass);
                UserService uService = new UserService(LoginActivity.this);
                boolean flag = uService.login(name, pass);
                if (flag) {
                    Log.i("TAG", "登录成功");
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    Util.StartActivity(LoginActivity.this, MainActivity.class);

                } else {
                    Log.i("TAG", "登录失败");
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}

