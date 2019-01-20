package me.jessyan.armscomponent.commonsdk.utils;

import android.util.Log;
import android.view.View;

/**
 * ================================================
 * Created by Watson on 01/19/2019
 * ================================================
 */

public class ClickUtils {
    private static long lastClickTime;

    /** 防止按钮重复点击 */
    public synchronized static boolean isFastClick() {
        return isFastClick(300);
    }

    /**
     * 防止按钮重复点击
     *
     * @param spaceTime
     *            间隔时间
     */
    public synchronized static boolean isFastClick(long spaceTime) {
        long time = System.currentTimeMillis();
        Log.d("ClickUtils", "currentTimeMillis:"+time+"--lastClickTime:"+lastClickTime);
        if (Math.abs(time - lastClickTime) < spaceTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 点击处理完成， 设置为下次不验证快速点击
     */
    public synchronized static void setNoFastClick() {
        lastClickTime = 0;
    }

    /**
     * 设置按钮是否可以点击,如果是设置为不可点击,则延时修改为可点击
     *
     * @param view
     * @param isClickable
     */
    public static void setViewClickableDelayed(final View view, boolean isClickable) {
        setViewClickableDelayed(view, isClickable, 1500);
    }

    /**
     * 设置按钮是否可以点击,如果是设置为不可点击,则延时修改为可点击
     *
     * @param view
     * @param isClickable
     */
    public static void setViewClickableDelayed(final View view, boolean isClickable, long delayedTime) {
        if (!isClickable) {
            view.postDelayed(() -> {
                if (view != null) {
                    view.setClickable(true);
                }
            }, delayedTime);
        }
        view.setClickable(isClickable);
    }
}

