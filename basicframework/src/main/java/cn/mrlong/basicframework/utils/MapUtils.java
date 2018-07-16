package cn.mrlong.basicframework.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.widget.Toast;

import java.net.URISyntaxException;


/**
 * Created by BINGO on 2017/06/06.
 */

public class MapUtils {

    /**
     * cat=android.intent.category.DEFAULT
     * dat=androidamap://navi?sourceApplication=appname&poiname=fangheng&lat=36.547901&lon=104.258354&dev=1&style=2
     * pkg=com.autonavi.minimap
     * <p>
     * API地址http://lbs.amap.com/api/amap-mobile/guide/android/navigation
     *
     * @param context
     * @param poiName 目的地名称 （可以为null）
     * @param la      纬度
     * @param lo      经度
     */
    public static void toGaoDeMap(final Context context, final String poiName,
                                  final double la, final double lo) {
        //com.autonavi.amapautolite 车镜班
        //com.autonavi.amapauto 车机班
        //API :http://lbs.amap.com/api/amap-auto/guide/route#t3
        if (AppUtils.isAvilible(context, "com.autonavi.amapautolite")) {
            int startTime = 1 * 1000;
            try {
                //启动车镜版

                Intent launchIntent = new Intent();
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                launchIntent.setComponent(new ComponentName("com.autonavi.amapautolite",
                        "com.autonavi.auto.remote.fill.UsbFillActivity"));
                context.startActivity(launchIntent);

                //通过广播启动导航
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent();
                        intent.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
                        intent.putExtra("KEY_TYPE", 10038);
                        intent.putExtra("POINAME", poiName);
                        intent.putExtra("LAT", la);
                        intent.putExtra("LON", lo);
                        intent.putExtra("DEV", 0);
                        intent.putExtra("STYLE", 0);
                        intent.putExtra("SOURCE_APP", AppUtils.getAppName(context));
                        context.sendBroadcast(intent);
                    }
                }, startTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (AppUtils.isAvilible(context, "com.autonavi.minimap")) {
            //高德地图
            //com.autonavi.minimap
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=" + AppUtils.getAppName(context)
                        + "&poiname=" + poiName + "&lat=" + la + "&lon=" + lo + "&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "你尚未安装高德地图!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 关闭高德地图
     * @param context
     */
    public static void offGaoDeMap(Context context){
        Intent intents = new Intent();
        intents.setAction("AUTONAVI_STANDARD_BROADCAST_RECV");
        intents.putExtra("KEY_TYPE", 10021);
        context.sendBroadcast(intents);
    }

    /**
     * API: http://lbsyun.baidu.com/index.php?title=uri/api/android
     *
     * @param context
     * @param location
     */
    public static void toBaiDuMap(Context context, Location location) {
        if (AppUtils.isAvilible(context, "com.baidu.BaiduMap")) {//传入指定应用包名

            try {
                // Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                Intent intent = Intent.getIntent("intent://map/direction?" +
                        //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                        "destination=latlng:" + location.getLatitude() + "," + location.getLongitude() + "|name:我的目的地" +        //终点
                        "&mode=driving&" +          //导航路线方式
                        "region=北京" +           //
                        "&src=慧医#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 计算方位角pab，
     其中lat_a, lng_a是A的纬度和经度； lat_b, lng_b是B的纬度和经度。
     * @param lat_a
     * @param lng_a
     * @param lat_b
     * @param lng_b
     * @return
     */
    private double gps2d(double lat_a, double lng_a, double lat_b, double lng_b) {
        double d = 0;
        lat_a=lat_a* Math.PI/180;
        lng_a=lng_a* Math.PI/180;
        lat_b=lat_b* Math.PI/180;
        lng_b=lng_b* Math.PI/180;

        d= Math.sin(lat_a)* Math.sin(lat_b)+ Math.cos(lat_a)* Math.cos(lat_b)* Math.cos(lng_b-lng_a);
        d= Math.sqrt(1-d*d);
        d= Math.cos(lat_b)* Math.sin(lng_b-lng_a)/d;
        d= Math.asin(d)*180/ Math.PI;

//     d = Math.round(d*10000);
        return d;
    }

    /**
     * 1.Lat1 Lung1 表示A点经纬度，Lat2 Lung2 表示B点经纬度；
     2.a=Lat1 – Lat2 为两点纬度之差  b=Lung1 -Lung2 为两点经度之差；
     3.6378.137为地球半径，单位为千米；
     计算出来的结果单位为千米。
     */
    private final double EARTH_RADIUS = 6378137.0;
    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
