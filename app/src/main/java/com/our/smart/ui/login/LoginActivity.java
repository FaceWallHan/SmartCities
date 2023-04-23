package com.our.smart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.our.smart.R;
import com.our.smart.bean.LoginResponse;
import com.our.smart.bean.post.Login;
import com.our.smart.net.EndUrlUtil;
import com.our.smart.net.HttpUtil;
import com.our.smart.net.NetStateListener;
import com.our.smart.ui.MainActivity;
import com.our.smart.utils.LocalKeyUtil;

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
//        if (!LocalKeyUtil.getToken().isEmpty()){
//            goMain();
//            return;
//        }
        MaterialButton loginBt=findViewById(R.id.login_bt);
        loginBt.setOnClickListener(view -> {
            String user = userNameEt.getText().toString().trim();
            String pass = passWordEt.getText().toString().trim();
            if (user.isEmpty()||pass.isEmpty()){
                Toast.makeText(LoginActivity.this,"用户名或密码不能为空！！！",Toast.LENGTH_SHORT).show();
                return;
            }
            goToLogin(user,pass);
        });

    }

    private void goToLogin(String user,String pass){
        Login login = new Login(user,pass);
        HttpUtil.getInstance()
                .inflateEndUrl(EndUrlUtil.Login)
                .inflatePostMsg(login)
                .inflateContentTypeJSON()
                .startRealRequest(this, LoginResponse.class, new NetStateListener<LoginResponse>() {
                    @Override
                    public void onSuccess(@NonNull LoginResponse response) {
                        String desc;
                        if (response.isSuccess()){
                            desc="登录成功";
                            LocalKeyUtil.setToken(response.getToken());
                            goMain();
                        }else {
                            desc="登录失败，请检查账号密码";
                            userNameEt.setText("");
                            passWordEt.setText("");
                        }
                        Toast.makeText(LoginActivity.this, desc, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(LoginActivity.this, "请求失败，请检查网络状态", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goMain(){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}