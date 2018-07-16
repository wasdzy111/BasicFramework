package cn.mrlong.basicframework.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

/**
 * Created by BINGO on 2017/07/08.
 */
public class KeyUtils {
    public static void openKey(Context context, List<String> key) {
        if (key.size() != 0) {
            for (int i = 0; i < key.size(); i++) {
                Intent mIntent = new Intent("android.action.concox.ACTION_CONTROL_STATUS");
                Bundle mBundle = new Bundle();
                mBundle.putString("KEY_TYPE", key.get(i));//按键值与之前定义一样
                mBundle.putInt("KEY_ENABLE", 0);//1点击无效果 0点击有效果
                mBundle.putString("KEY_STRING", "");// 值为Toast内容
                mIntent.putExtras(mBundle);
                context.sendBroadcast(mIntent);
            }
        }
    }

    public static void closeKey(Context context, List<String> key) {
        if (key.size() != 0) {
            for (int i = 0; i < key.size(); i++) {
                Intent mIntent = new Intent("android.action.concox.ACTION_CONTROL_STATUS");
                Bundle mBundle = new Bundle();
                mBundle.putString("KEY_TYPE", key.get(i));//按键值与之前定义一样
                mBundle.putInt("KEY_ENABLE", 1);//1点击无效果 0点击有效果
                mBundle.putString("KEY_STRING", "当前状态不允许此操作！");// 值为Toast内容
                mIntent.putExtras(mBundle);
                context.sendBroadcast(mIntent);
            }
        }
    }
}
