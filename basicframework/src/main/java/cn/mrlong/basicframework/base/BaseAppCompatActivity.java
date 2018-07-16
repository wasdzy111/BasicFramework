package cn.mrlong.basicframework.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.mrlong.basicframework.utils.AppUtils;
import cn.mrlong.basicframework.utils.StaticConstUtils;


/**
 * Created by BINGO on 2017/04/18.
 */

public abstract class BaseAppCompatActivity<G extends ViewDataBinding> extends Rx2Activity implements View.OnClickListener {

    /**
     * 设置activity布局
     */
    public abstract int setContentView();

    /**
     * 初始化组件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化监听
     */
    public abstract void initListener();

    public boolean doubleKeyExit = false;//是否双击推出应用
    public Context context;//上下文对象
    private long firstTime = 0;//第一次按下返回键
    private long secondTime = 0;//第二次按下返回键
    public final static int GONE = View.GONE;
    public final static int VISIBLE = View.VISIBLE;
    public final static int INVISIBLE = View.INVISIBLE;
    private Toast toast;
    public boolean isAlph = false;
    public boolean isTop = false;
    public View parentView;
    public int page = 0;
    public int pageSize = 10;
    public LayoutInflater inflater;
    public static BaseAppCompatActivity activity;
    public boolean isActive;
    public G viewBinding;
    public Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        this.savedInstanceState= savedInstanceState;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Logger.init(AppUtils.getAppName(context));
        this.activity = this;
        parentView = View.inflate(context, setContentView(), null);
        try {
            viewBinding = DataBindingUtil.bind(parentView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!isTop) {
            parentView.setFitsSystemWindows(true);
        }
        setContentView(parentView);
        inflater = LayoutInflater.from(context);
        //titleAlph();
        ActivityManager.addActivity(this);
        setContentView();
        initView();
        initData();
        initListener();
    }

    public final void titleAlph() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            if (!isAlph) {
                //沉浸式状态栏

            }
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits =
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && doubleKeyExit) {
            if (firstTime == 0) {
                firstTime = System.currentTimeMillis();
                showShortToast("再次点击返回键退出应用");
            } else {
                secondTime = System.currentTimeMillis();
                long interval = secondTime - firstTime;
                if (interval <= StaticConstUtils.DOUBLE_CLICK_EXIT_DENY) {
                    ActivityManager.exitApplicaion();
                } else {
                    firstTime = 0;
                    secondTime = 0;
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public final <T extends View> T findView(int id) {
        return (T) super.findViewById(id);
    }

    /**
     * 弹出short toast提示
     *
     * @param msg
     */
    public void showShortToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    toast.cancel();
                } catch (Exception e) {
                }
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    /**
     * 弹出long toast提示
     *
     * @param msg
     */
    public void showLongToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    toast.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    @Override
    protected void onPause() {
        if (toast != null) {//toast随页面消失而消失
            try {
                toast.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onPause();
    }


    /**
     * 判断当前应用程序处于前台还是后台
     */
    public boolean isBackground() {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        String packageName = getApplicationContext().getPackageName();
        List<android.app.ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

        if (appProcesses == null)
            return false;

        for (android.app.ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {

            if (appProcess.processName.equals(packageName) && appProcess.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

}

