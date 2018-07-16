package cn.mrlong.basicframework.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by BINGO on 2017/06/07.
 */
public class AppSettingUtils {
    public static void toPermissionSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", AppUtils.getAppProcessName(context), null));
        context.startActivity(intent);
    }
}