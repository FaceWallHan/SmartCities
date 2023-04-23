package com.our.smart.ui.lawyer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.tabs.TabLayout;
import com.our.smart.R;
import com.our.smart.bean.Lawyer;
import com.our.smart.utils.ADUtil;

public class LawyerDetailsActivity extends AppCompatActivity {
    public static final String START_MSG = "start_msg";
    public static void startActivity(Context context , Lawyer lawyer){
        context.startActivity(new Intent(context, LawyerDetailsActivity.class)
                .putExtra(START_MSG,lawyer));
    }
    private Guideline guideline3;
    private ImageView ivBack;
    private TextView tvName;
    private Guideline guideline7;
    private ImageView ivAvatar;
    private TextView tvSpeciality;
    private TextView tvConsultCount;
    private TextView tvServiceCount;
    private TabLayout tabLayout;
    private ViewPager2 vpLayout;
    private Button btFreeConsult;
    private Lawyer lawyer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyer_details);
        initDate();
        initView();
        initAdapter();

    }

    private void initDate() {
        lawyer = (Lawyer) getIntent().getSerializableExtra(START_MSG);
    }

    private void initView() {
        guideline3 = findViewById(R.id.guideline3);
        ivBack = findViewById(R.id.iv_back);
        tvName = findViewById(R.id.tv_name);
        guideline7 = findViewById(R.id.guideline7);
        ivAvatar = findViewById(R.id.iv_avatar);
        tvSpeciality = findViewById(R.id.tv_speciality);
        tvConsultCount = findViewById(R.id.tv_consult_count);
        tvServiceCount = findViewById(R.id.tv_service_count);
        tabLayout = findViewById(R.id.tab_layout);
        vpLayout = findViewById(R.id.vp_layout);
        btFreeConsult = findViewById(R.id.bt_free_consult);
    }



    private void initAdapter() {
        ivBack.setOnClickListener(v->finish());
        tvName.setText(lawyer.getName());
        tvSpeciality.setText(getString(R.string.lay_speciality,lawyer.getLegalExpertiseName()));
        tvServiceCount.setText(getString(R.string.lay_service_count,lawyer.getFavorableCount()));
        tvConsultCount.setText(getString(R.string.lay_consult_count,lawyer.getServiceTimes()));
        Glide.with(this)
                .load(lawyer.getAvatarUrl())
                .transform(new CenterCrop(), new RoundedCorners(ADUtil.Spec.dp2px(3)))
                .into(ivAvatar);
    }
}
