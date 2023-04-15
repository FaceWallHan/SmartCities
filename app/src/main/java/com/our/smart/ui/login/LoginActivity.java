package com.our.smart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.our.smart.R;
import com.our.smart.bean.LoginResponse;
import com.our.smart.net.EndUrlUtil;
import com.our.smart.net.HttpUtil;
import com.our.smart.net.NetStateListener;
import com.our.smart.ui.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText userNameEt,passWordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        userNameEt = findViewById(R.id.username_et);
        passWordEt = findViewById(R.id.password_et);
        MaterialButton loginBt=findViewById(R.id.login_bt);
        loginBt.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            String user = userNameEt.getText().toString().trim();
//            String pass = passWordEt.getText().toString().trim();
//            if (user.isEmpty()||pass.isEmpty()){
//                Toast.makeText(LoginActivity.this,"用户名或密码不能为空！！！",Toast.LENGTH_SHORT).show();
//                return;
//            }
//            goToLogin(user,pass);
        });

    }

    private void goToLogin(String user,String pass){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", user);
            jsonObject.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.getInstance()
                .inflateEndUrl(EndUrlUtil.Login)
                .inflatePostMsg(jsonObject)
                .startRealRequest(this, LoginResponse.class, new NetStateListener<LoginResponse>() {
                    @Override
                    public void onSuccess(@NonNull LoginResponse response) {
                        String text;
                        if (response.isSuccess()){
                            text="登录成功";
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else {
                            text="登录失败，请检查账号密码";
                            userNameEt.setText("");
                            passWordEt.setText("");
                        }
                        Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(LoginActivity.this, "请求失败，请检查网络状态", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}