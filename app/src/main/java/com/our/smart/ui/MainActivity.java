package com.our.smart.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.our.smart.R;
import com.our.smart.ui.lawyer.LawyerListFragment;
import com.our.smart.bean.ImageCarouselItem;
import com.our.smart.bean.ImageCarouselResponse;
import com.our.smart.net.EndUrlUtil;
import com.our.smart.net.HttpUtil;
import com.our.smart.net.NetStateListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewFlipper flipperBanner;
    private LinearLayout selectGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flipperBanner = findViewById(R.id.flipper_banner);
        flipperBanner.getInAnimation().setAnimationListener(inAnimationListener);
        selectGroup = findViewById(R.id.select_group);
        requestBanner();
    }

    private void requestBanner() {
        HttpUtil.getInstance()
                .inflateContentTypeUrl()
                .inflateEndUrl(EndUrlUtil.LegalAllBanner)
                .inflateGetMap(null)
                .startRealRequest(this, ImageCarouselResponse.class, new NetStateListener<ImageCarouselResponse>() {
                    @Override
                    public void onSuccess(ImageCarouselResponse response) {
                        if (!response.isSuccess()) {
                            Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<ImageCarouselItem> data = response.getData();
                        for (int i = 0; i < data.size(); i++) {
                            ImageCarouselItem item = data.get(i);
                            flipperBanner.addView(getFlipperItem(item, i));
                            selectGroup.addView(getLinearCircle(i));
                        }


                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private final Animation.AnimationListener inAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            ImageView img = flipperBanner.getCurrentView().findViewById(R.id.flipper_item_iv);
            for (int i = 0; i < selectGroup.getChildCount(); i++) {
                ImageView circle = (ImageView) selectGroup.getChildAt(i);
                int circleDraw = ((int) img.getTag() == i) ? R.drawable.select_circle : R.drawable.un_select_circle;
                circle.setBackgroundResource(circleDraw);
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 获取ViewFlipper的填充布局
     */
    private View getFlipperItem(ImageCarouselItem item, int i) {
        View view = View.inflate(flipperBanner.getContext(), R.layout.flipper_item_layout, null);
        ImageView img = view.findViewById(R.id.flipper_item_iv);
        Glide.with(MainActivity.this).load(item.getImgUrl()).into(img);
        img.setTag(i);
        TextView text = view.findViewById(R.id.flipper_item_tv);
        text.setText(item.getTitle());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return view;
    }

    /**
     * 动态添加圆
     */
    private View getLinearCircle(int i) {
        ImageView circle = new ImageView(selectGroup.getContext());
        int circleDraw = i == 0 ? R.drawable.select_circle : R.drawable.un_select_circle;
        circle.setBackgroundResource(circleDraw);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
        layoutParams.rightMargin = 5;
        layoutParams.leftMargin = 5;
        circle.setLayoutParams(layoutParams);
        return circle;
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