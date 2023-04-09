package com.our.smart;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.our.smart.bean.BaseBean;
import com.our.smart.net.EndUrlUtil;
import com.our.smart.net.HttpUtil;
import com.our.smart.net.NetStateListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", "test01");
            jsonObject.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpUtil.getInstance()
                .inflateEndUrl(EndUrlUtil.Login)
                .inflatePostMsg(jsonObject)
                .startRealRequest(this, BaseBean.class, new NetStateListener<BaseBean>() {
                    @Override
                    public void onSuccess(@NonNull BaseBean response) {
                        Toast.makeText(MainActivity.this, "____________"+response, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }
}