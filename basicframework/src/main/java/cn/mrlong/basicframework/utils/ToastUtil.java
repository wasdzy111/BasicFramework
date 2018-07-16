package cn.mrlong.basicframework.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 优化Toast，功能防止用户短时间内多次触发Toast而发生的Toast泛滥
 *
 * @author Tiny
 *
 */
@SuppressLint("ShowToast")
public class ToastUtil {
    private static Toast toast = null;
    private static int textSize = 25;
    private ToastUtil() {
    }
//    public static void showMsg(String text){
//        toast = getInstance(MyApp.getInstance(), text, Toast.LENGTH_SHORT);
//        toast.show();
//    }
    private static Toast getInstance(Context context, String msg, int during) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, during);
            // // 加入个人偏好布局设置
            //toast.getView().setBackgroundResource(R.drawable.toast_bg);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            //textView.setTextSize(textSize);
            //textView.setPadding(5, 5, 5, 5);
            textView.setTextColor(Color.WHITE);
        }
        toast.setDuration(during);
        toast.setText(msg);
        return toast;
    }

    private static Toast getInstance(Context context, int msg, int during) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, during);
            // // 加入个人偏好布局设置
            //toast.getView().setBackgroundResource(R.drawable.toast_bg);
            TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
            //textView.setTextSize(15);
            //textView.setPadding(5, 5, 5, 5);
            textView.setTextColor(Color.WHITE);
        }
        toast.setDuration(during);
        toast.setText(context.getResources().getString(msg));
        return toast;
    }

    public static void showMessage(final Context context, final String msg) {
        toast = getInstance(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showMessage(final Context context, final int msg) {
        toast = getInstance(context, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showMessage(final Context context, final int msg, final int len) {
        toast = getInstance(context, msg, len);
        toast.show();
    }

    public static void showMessage(final Context context, final String msg, final int during) {
        toast = getInstance(context, msg, during);
        toast.show();
    }

    /**
     * 连续展示，仅改变原生的样式
     *
     * @param context
     * @param msg
     * @param during
     */
    public static void showOrderMessage(final Context context, final int msg, final int during) {
        Toast myToast = Toast.makeText(context, msg, during);
        //myToast.getView().setBackgroundResource(R.drawable.toast_bg);
        TextView textView = (TextView) myToast.getView().findViewById(android.R.id.message);
        //textView.setTextSize(15);
        //textView.setPadding(5, 5, 5, 5);
        textView.setTextColor(Color.WHITE);
        myToast.setDuration(during);
        myToast.setText(msg);
        myToast.show();
    }

    /**
     * 连续展示，仅改变原生的样式
     *
     * @param context
     * @param msg
     * @param during
     */
    public static void showOrderMessage(final Context context, final String msg, final int during) {
        Toast myToast = Toast.makeText(context, msg, during);
        //myToast.getView().setBackgroundResource(R.drawable.toast_bg);
        TextView textView = (TextView) myToast.getView().findViewById(android.R.id.message);
        //textView.setTextSize(15);
        //textView.setPadding(5, 5, 5, 5);
        textView.setTextColor(Color.WHITE);
        myToast.setDuration(during);
        myToast.setText(msg);
        myToast.show();
    }

}