package cn.mrlong.basicframework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by Administrator on 2016/8/4 0004.
 */
public class ActivityUtils {

    private static Intent intent ;
    private static Intent getIntent(){
        if (intent == null){
            intent = new Intent() ;
        }
        return  intent ;
    }

    /**
     * activity页面跳转
     * @param context
     * @param clazz
     */
    public static void startActivityIntent(Context context, Class clazz){
        Activity activity = (Activity) context ;
        Intent intent = getIntent() ;
        intent.setClass(context,clazz) ;
        context.startActivity(intent);
    }
    /**
     * activity页面跳转
     * @param context
     * @param clazz
     * @param bundle
     */
    public static void startActivityIntent(Context context, Class clazz, Bundle bundle){
        Intent intent = getIntent() ;
        intent.setClass(context,clazz) ;
        if(bundle != null){
            intent.putExtras(bundle) ;
        }
        context.startActivity(intent);
    }

    /**
     * activity页面跳转返回
     * @param requestCode
     */
    public static void startActivityForResult(Context context, Intent intent, int requestCode) {
        Activity activity = (Activity) context ;
        activity.startActivityForResult(intent,requestCode);
    }
}
