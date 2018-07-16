package cn.mrlong.basicframework;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import org.xutils.x;

/**
 * Created by BINGO on 2018/07/12.
 */

public class XApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void xUtilsOpen() {
        //初始化xUtils 文件上传
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    // 实现分包 解决64k
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
