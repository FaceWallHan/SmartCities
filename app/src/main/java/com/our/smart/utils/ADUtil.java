package com.our.smart.utils;

import android.util.DisplayMetrics;

public class ADUtil {
    public static class Spec {

        private static DisplayMetrics sDisplayMetrics;

        /**
         * 获取DisplayMetrics
         *
         * @return
         */
        public static DisplayMetrics getDisplayMetrics() {
            if (sDisplayMetrics == null) {
                synchronized (Spec.class) {
                    if (sDisplayMetrics == null) {
                        sDisplayMetrics = new DisplayMetrics();
                        sDisplayMetrics.setToDefaults();
                    }
                }
            }
            return sDisplayMetrics;
        }

        /**
         * 获取density
         *
         * @return
         */
        public static float getDensity() {
            return getDisplayMetrics().density;
        }

        /**
         * 获取densityDpi
         *
         * @return
         */
        public static float getDensityDpi() {
            return getDisplayMetrics().densityDpi;
        }

        /**
         * 像素转换
         *
         * @param pxValue
         * @return
         */
        public static int px2dp(float pxValue) {
            final float scale = getDensity();
            return (int) (pxValue / scale + 0.5f);
        }

        /**
         * 像素转换
         *
         * @param dpValue
         * @return
         */
        public static int dp2px(float dpValue) {
            final float scale = getDensity();
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 像素转换
         *
         * @param pxValue
         * @return
         */
        public static int px2sp(float pxValue) {
            final float fontScale = getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        }

        /**
         * 像素转换
         *
         * @param spValue
         * @return
         */
        public static int sp2px(float spValue) {
            final float fontScale = getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }

        /**
         * 像素转换
         *
         * @param dpValue
         * @return
         */
        public static int dp2sp(float dpValue) {
            float density = getDisplayMetrics().density;
            final float fontScale = getDisplayMetrics().scaledDensity;
            return (int) ((dpValue * density + 0.5) / fontScale + 0.5f);
        }

        /**
         * 像素转换
         *
         * @param spValue
         * @return
         */
        public static int sp2dp(float spValue) {
            final float scale = getDensity();
            final float fontScale = getDisplayMetrics().scaledDensity;
            return (int) ((spValue * fontScale + 0.5f) / scale + 0.5f);
        }
    }
}
