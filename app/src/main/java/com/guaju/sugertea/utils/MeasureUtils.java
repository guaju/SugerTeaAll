package com.guaju.sugertea.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by guaju on 2017/8/16.
 */

public class MeasureUtils {

    private static final String TAG = "MeasureUtils";
    //测量状态栏高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }
    public static int getScreenHeight1(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        Log.e(TAG, "getScreenHeight: "+heightPixels );
        return heightPixels;
    }

    public static int getScreenHeight2(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int heightPixels = displayMetrics.heightPixels;
        Log.e(TAG, "setRecyclerViewListener: "+heightPixels);
        return  heightPixels;
    }

}
