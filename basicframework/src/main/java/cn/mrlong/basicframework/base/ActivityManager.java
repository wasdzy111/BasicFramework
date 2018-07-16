package cn.mrlong.basicframework.base;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by BINGO on 2017/04/18.
 */

public class ActivityManager {
    private static ArrayList<Activity> activities;

    static {
        if (activities == null) {
            activities = new ArrayList<Activity>();
        }
    }

    /**
     * 添加activity到activity集合
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        int length = activities.size();
        if (length == 0) {
            activities.add(activity);
        } else {
            for (int i = 0; i < length; i++) {
                if (activity.getClass().equals(activities.get(i))) {
                    break;
                } else if (i == length - 1) {
                    activities.add(activity);
                }
            }
        }
    }

    /**
     * 关闭所有activity 退出应用
     */
    public static void exitApplicaion() {
        int length = activities.size();
        for (int i = 0; i < length; i++) {
            activities.get(i).finish();
        }
    }
}
