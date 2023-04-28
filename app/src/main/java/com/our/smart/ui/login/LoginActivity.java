package com.our.smart.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.our.smart.bean.LoginResponse;
import com.our.smart.bean.post.Login;
import com.our.smart.databinding.LoginLayoutBinding;
import com.our.smart.net.EndUrlUtil;
import com.our.smart.net.HttpUtil;
import com.our.smart.net.NetStateListener;
import com.our.smart.ui.MainActivity;
import com.our.smart.utils.LocalKeyUtil;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private LoginLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginLayoutBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.loginBt.setOnClickListener(loginClick);
        boolean hasToken = !LocalKeyUtil.getToken().isEmpty();
        Login login = LocalKeyUtil.getLogin();
        boolean loginSucceeded = !login.isEmpty();
        if (hasToken && loginSucceeded) {
            goMain();
        }
    }

    private void goToLogin(Login login, NetStateListener<LoginResponse> action) {
        new HttpUtil()
                .inflateEndUrl(EndUrlUtil.Login)
                .inflatePostMsg(login)
                .inflateContentTypeJSON()
                .startRealRequest(this, LoginResponse.class, new NetStateListener<LoginResponse>() {
                    @Override
                    public void onSuccess(@NonNull LoginResponse response) {
                        action.onSuccess(response);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        action.onFailure(e);
                    }
                });
    }

    private void goMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private final View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LocalKeyUtil.setToken("");
            String user = Objects.requireNonNull(binding.usernameEt.getText()).toString().trim();
            String pass = Objects.requireNonNull(binding.passwordEt.getText()).toString().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "用户名或密码不能为空！！！", Toast.LENGTH_SHORT).show();
                return;
            }
            Login login = new Login(user, pass);
            goToLogin(login, new NetStateListener<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse response) {
                    LocalKeyUtil.setLogin(login);
                    LocalKeyUtil.setToken(response.getToken());
                    goMain();
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Exception e) {
                    binding.usernameEt.setText("");
                    binding.passwordEt.setText("");
                    Toast.makeText(LoginActivity.this, e.toString() + "\n登录失败，请检查账号密码", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}