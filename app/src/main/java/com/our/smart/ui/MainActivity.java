package com.our.smart.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
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
import com.our.smart.bean.LawSpecialtyItem;
import com.our.smart.bean.LawSpecialtyResponse;
import com.our.smart.databinding.ActivityMainBinding;
import com.our.smart.ui.lawyer.LawyerListFragment;
import com.our.smart.bean.ImageCarouselItem;
import com.our.smart.bean.ImageCarouselResponse;
import com.our.smart.net.EndUrlUtil;
import com.our.smart.net.HttpUtil;
import com.our.smart.net.NetStateListener;
import com.our.smart.ui.specialty.LawPagerAdapter;
import com.our.smart.ui.specialty.LawSpecialtyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.flipperBanner.getInAnimation().setAnimationListener(inAnimationListener);
        binding.specialPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changePointStatus(binding.specialPointGroup,position);
            }
        });
        requestBanner();
        loadFragment();
        requestLawSpecialty();
    }

    private void requestLawSpecialty(){
        HttpUtil.getInstance()
                .inflateContentTypeUrl()
                .inflateEndUrl(EndUrlUtil.LegalExpertise)
                .inflateGetMap(null)
                .startRealRequest(this, LawSpecialtyResponse.class, new NetStateListener<LawSpecialtyResponse>() {
                    @Override
                    public void onSuccess(LawSpecialtyResponse response) {
                        if (!response.isSuccess()) {
                            Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ArrayList<Fragment> fragments = new ArrayList<>();
                        int total=response.getRows().size();
                        int pageCount = total / 8;
                        //余数
                        int remainder = total % 8;
                        if (remainder != 0) {
                            pageCount += 1;
                        }
                        for (int i = 0; i < pageCount; i++) {
                            boolean isLast = (i == pageCount - 1 && remainder != 0);
                            //kotlin用习惯了，，，
                            Pair<Integer, Integer> flag = isLast ? new Pair<>(8 * (i), total) : new Pair<>(8 * i, 8 * (i + 1));
                            ArrayList<LawSpecialtyItem> subList = new ArrayList<>(response.getRows().subList(flag.first, flag.second));
                            fragments.add(LawSpecialtyFragment.newInstance(subList));
                            binding.specialPointGroup.addView(getLinearCircle(i));
                        }
                        binding.specialPager.setAdapter(new LawPagerAdapter(MainActivity.this,fragments));
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
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
                        Log.d("4144444444444444", "onSuccess: _____________"+response);
//                        List<ImageCarouselItem> data = response.getData();
//                        for (int i = 0; i < data.size(); i++) {
//                            ImageCarouselItem item = data.get(i);
//                            binding.flipperBanner.addView(getFlipperItem(item, i));
//                            binding.selectGroup.addView(getLinearCircle(i));
//                        }
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
            ImageView img = binding.flipperBanner.getCurrentView().findViewById(R.id.flipper_item_iv);
            changePointStatus(binding.selectGroup, (int) img.getTag());
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
        View view = View.inflate(binding.flipperBanner.getContext(), R.layout.flipper_item_layout, null);
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
        ImageView circle = new ImageView(this);
        int circleDraw = i == 0 ? R.drawable.select_circle : R.drawable.un_select_circle;
        circle.setBackgroundResource(circleDraw);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
        layoutParams.rightMargin = 5;
        layoutParams.leftMargin = 5;
        circle.setLayoutParams(layoutParams);
        return circle;
    }

    private void changePointStatus(LinearLayout layout, int position){
        for (int i = 0; i < layout.getChildCount(); i++) {
            ImageView circle = (ImageView) layout.getChildAt(i);
            int circleDraw = (position == i) ? R.drawable.select_circle : R.drawable.un_select_circle;
            circle.setBackgroundResource(circleDraw);
        }
    }

    //加载fragment
    private void loadFragment() {
        //加载fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, LawyerListFragment.newInstance(LawyerListFragment.LawyerType.GOOD_RATE))
                .commit();
    }

}