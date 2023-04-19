package com.our.smart.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

import com.our.smart.R;


/**
 * 自定义圆角矩形ImageView:
 * 显示圆角图片，并且触摸图片时图片前面显示一个黑色的阴影效果
 * 说明：
 * 1)默认是有触摸阴影的，因为是否画阴影是通过监听触摸事件的down和up、cancel等事件来做，所以需要该View拦截事件，
 * 默认添加了clickable为true，所以如果是在列表中使用该View并且给item设置了点击事件，但是在点击图片时由于设置了
 * clickable为true，所以item的点击事件不会响应，需要另外给该View添加点击事件保持与item的点击事件一致。
 * <p>
 * 2)如果不需要触摸图片显示阴影，则调用方法setShowShadow(false)
 * <p>
 * Created by daihuazhi
 */
public class RoundRectImageView extends AppCompatImageView {

    private static final float DEFAULT_CORNER = 10;
    private Paint mPaint;
    private int mCorner;
    // 触摸图片时是否画阴影
    private boolean mShowShadow = false;
    // 是否正在触摸图片
    private boolean mTouch = false;
    private int mTouchShadowColor = getResources().getColor(R.color.touch_shadow_color);

    public RoundRectImageView(Context context) {
        this(context, null);
    }

    public RoundRectImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        setClickable(true);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView);
        mCorner = (int) typedArray.getDimension(R.styleable.RoundRectImageView_corner, dip2px(context, DEFAULT_CORNER));
        mShowShadow = typedArray.getBoolean(R.styleable.RoundRectImageView_showShadow, mShowShadow);
        mTouchShadowColor = typedArray.getColor(R.styleable.RoundRectImageView_shadowColor, mTouchShadowColor);
        typedArray.recycle();
    }

    /**
     * 设置圆角大小
     *
     * @param corner 单位为dp
     */
    public void setCorner(int corner) {
        this.mCorner = dip2px(getContext(), corner);
        invalidate();
    }

    /**
     * 将dip或dp值转换为px值
     */
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 设置触摸图片后是否显示选中的颜色
     */
    public void setShowShadow(boolean showShadow) {
        this.mShowShadow = showShadow;
    }

    /**
     * 设置触摸阴影颜色
     */
    public void setTouchShadowColor(int color) {
        this.mTouchShadowColor = color;
    }

    /**
     * 绘制圆角矩形图片
     */
    @Override
    protected void onDraw(Canvas canvas) {
        final Drawable drawable = getDrawable();
        // 画正常加载图片和异常加载图片的占位图
        if (drawable == null) { // 无法获取Drawable
            drawPlaceHolder(canvas);
        } else {
            if (getBitmap() != null && getBitmap().isRecycled() == false) { // 正常加载图片
                drawRoundImageView(canvas);
//                drawRoundImageViewWithBorder(canvas);
            } else { // 加载图片异常
                drawPlaceHolder(canvas);
            }
        }
        // 是否需要添加触摸图片时的阴影
        if (mShowShadow && mTouch) {
            drawShadow(canvas);
        }
    }

    /**
     * 加载异常时画占位图
     */
    private void drawPlaceHolder(Canvas canvas) {
        final RectF rect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mPaint.setColor(Color.parseColor("#dcdcdc"));
        canvas.drawRoundRect(rect, mCorner, mCorner, mPaint);
    }

    /**
     * 正常加载时，画出需要的圆角图片
     */
    private void drawRoundImageView(Canvas canvas) {
        final Bitmap roundBitmap = getRoundBitmap(getBitmap(), mCorner);
        final Rect rectSrc = new Rect(0, 0, roundBitmap.getWidth(), roundBitmap.getHeight());
        final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
        mPaint.reset();
        canvas.drawBitmap(roundBitmap, rectSrc, rectDest, mPaint);
    }

    /**
     * 正常加载时，画出需要的圆角图片
     */
    private void drawRoundImageViewWithBorder(Canvas canvas) {
        final Bitmap roundBitmap = getRoundBitmap(getBitmap(), mCorner);
        final Bitmap roundBitmapWithBorder = addBorder(roundBitmap, 10);
        final Rect rectSrc = new Rect(0, 0, roundBitmapWithBorder.getWidth(), roundBitmapWithBorder.getHeight());
        final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
        mPaint.reset();
        canvas.drawBitmap(roundBitmapWithBorder, rectSrc, rectDest, mPaint);
    }

    /**
     * 画触摸阴影
     */
    private void drawShadow(Canvas canvas) {
        mPaint.setColor(mTouchShadowColor);
        final RectF rect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRoundRect(rect, mCorner, mCorner, mPaint);
    }

    /**
     * 根据Drawable获取Bitmap
     */
    private Bitmap getBitmap() {
        final Drawable drawable = getDrawable();
        Log.d("drawable", drawable + "");
        Bitmap bitmap = null;
        if (drawable instanceof ColorDrawable) {
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
            bitmap.eraseColor(((ColorDrawable) drawable).getColor());
        } else if (drawable instanceof BitmapDrawable) {
            // 正常加载图片
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        return bitmap;
    }

    /**
     * 获取圆角图片
     */
    private Bitmap getRoundBitmap(Bitmap bitmap, int corner) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (getWidth() == getHeight()){
            width = height = Math.min(width, height);
        }
        final Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
//        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//                bitmap.getHeight(), Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
//        final Rect rect = new Rect(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom());
//        final RectF rectF = new RectF(rect);
        int offsetX = (bitmap.getWidth() - width) / 2;
        int offsetY = (bitmap.getHeight() - height) / 2;
        final Rect rect = new Rect(getPaddingLeft() + offsetX, getPaddingTop() + offsetY, offsetX + width - getPaddingRight() , offsetY + height - getPaddingBottom());
        final RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom());
        mPaint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        mPaint.setColor(Color.WHITE);
//        if (corner >= bitmap.getWidth() / 2 && corner >= bitmap.getHeight() / 2) {
//            float cx = bitmap.getWidth() / 2;
//            float cy = bitmap.getHeight() / 2;
//            float radius = Math.min(cx, cy);
//            canvas.drawCircle(cx, cy, radius, mPaint);
//        } else {
//
//            canvas.drawRoundRect(rectF, corner, corner, mPaint);
//        }
            canvas.drawRoundRect(rectF, corner, corner, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, mPaint);
        return output;
    }

    /**
     * 增加边框
     */
    private Bitmap addBorder(Bitmap bitmap, int borderWidth) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth() + borderWidth * 2,
                bitmap.getHeight() + borderWidth * 2, Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final Rect rect = new Rect(borderWidth, borderWidth, bitmap.getWidth() - borderWidth, bitmap.getHeight() - borderWidth);
//        final RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        mPaint.setAntiAlias(true);
//        canvas.drawARGB(0, 255, 255, 255);
        mPaint.setColor(Color.WHITE);
        float radius = Math.min(output.getWidth() / 2, bitmap.getHeight() / 2);
        canvas.drawCircle(output.getWidth() / 2, bitmap.getHeight() / 2, radius + borderWidth, mPaint);
//        mPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//        canvas.drawBitmap(bitmap, rect, rect, mPaint);
        return output;
    }

    /**
     * 监听触摸与离开
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final Drawable drawable = getDrawable();
        // 如果图片没有设置点击事件，则触摸没有反馈
        if (!mShowShadow || drawable == null || !isClickable()) {
            return super.dispatchTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouch = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                mTouch = false;
                break;
        }
        invalidate();
        return super.dispatchTouchEvent(event);
    }
}
