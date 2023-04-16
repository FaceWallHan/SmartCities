package com.our.smart.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.our.smart.R;
import com.our.smart.ui.lawyer.LawyerListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment();
    }

    //加载fragment
    private void loadFragment() {
        //加载fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, LawyerListFragment.newInstance(LawyerListFragment.LawyerType.GOOD_RATE))
                .commit();
    }

}