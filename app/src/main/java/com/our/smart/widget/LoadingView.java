package com.our.smart.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.our.smart.databinding.LayoutLoadingBinding;


public class LoadingView extends FrameLayout {

    private LayoutLoadingBinding binding;
    private ObjectAnimator rotation;

    public LoadingView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {

        binding = LayoutLoadingBinding.inflate(LayoutInflater.from(context), this, true);
        binding.getRoot().
                addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        startAnim();
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {

                        stopAnim();
                    }
                });
    }

    public void setLoadingText(CharSequence msg) {
        binding.tvLoading.setText(msg);
    }

    public void setLoadingText(@StringRes int strId) {
        binding.tvLoading.setText(strId);
    }

    public void hide() {
        stopAnim();
        setVisibility(GONE);
    }

    public void show() {
        startAnim();
        setVisibility(VISIBLE);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }


    public void stopAnim() {
        if (rotation != null) {
            rotation.cancel();
        }
    }

    public void startAnim() {
        if (rotation == null) {
            rotation = ObjectAnimator.ofFloat(binding.ivLoading, "rotation", 0, 180)
                    .setDuration(1000);
            rotation.setStartDelay(1000);
            rotation.setRepeatMode(ValueAnimator.RESTART);
            rotation.setRepeatCount(ValueAnimator.INFINITE);
            rotation.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        rotation.start();
    }
}
