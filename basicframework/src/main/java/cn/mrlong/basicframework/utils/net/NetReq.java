package cn.mrlong.basicframework.utils.net;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.mrlong.basicframework.utils.StringUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by BINGO on 2017/08/24.
 */

public class NetReq {
    private static int connectTimeOut = 10 * 1000;

    /**
     * restfull 提交post
     * @param action
     * @param json
     * @param callback
     */
    public static void post(String action, String json, final HttpCallback callback) {
        Observable.just("")
                .subscribeOn(Schedulers.io())//子线程查询数据
                .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String res) throws Exception {
                        callback.onStart();
                    }
                });

        final RequestParams entity = new RequestParams(action);
        entity.setAsJsonContent(true);
        entity.setBodyContent(json);
        Log.e("xutils=>", "---------Start---------");
        Log.e("xutils=>", "URL: " + entity.getUri() + ":参数:" + json);
        if (entity != null) {
            entity.setConnectTimeout(connectTimeOut);
        }
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Observable.just(result)
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                callback.onSuccess(s);
                                Log.e("xutils=>", "URL: " + entity.getUri() + ":返回:" + s);
                                Log.e("xutils=>", "----------End----------");
                            }
                        });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Observable.just(ex)
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable e) throws Exception {
                                callback.onFailure(e);
                                e.printStackTrace();
                                Log.e("xutils=>", "URL: " + entity.getUri() + ":返回:" + e.getMessage());
                                Log.e("xutils=>", "----------End----------");
                            }
                        });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Observable.just("")
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(@NonNull String res) throws Exception {
                                callback.onFinish();
                            }
                        });
            }
        });

    }
    /**
     * form表单提交方式post
     * @param entity
     * @param callback
     */

    public static void post(final RequestParams entity, final HttpCallback callback) {
        Observable.just("")
                .subscribeOn(Schedulers.io())//子线程查询数据
                .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String res) throws Exception {
                        callback.onStart();
                    }
                });
        String param = "";
        if (entity != null) {
            if (entity.getStringParams().size() > 0) {
                for (KeyValue keyValue : entity.getStringParams()) {
                    param += "&" + keyValue.key + "=" + keyValue.value;
                }
            }
            if (StringUtils.isNotBlank(param)) {
                param = param.replaceFirst("&", "?");
            }
        }
        Log.e("xutils=>", "---------Start---------");
        Log.e("xutils=>", "URL: " + entity.getUri() + ":参数:" + param);
        if (entity != null) {
            entity.setConnectTimeout(connectTimeOut);
        }
        x.http().post(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Observable.just(result)
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                callback.onSuccess(s);
                                Log.e("xutils=>", "URL: " + entity.getUri() + ":返回:" + s);
                                Log.e("xutils=>", "----------End----------");
                            }
                        });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Observable.just(ex)
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable e) throws Exception {
                                callback.onFailure(e);
                                Log.e("xutils=>", "URL: " + entity.getUri() + ":返回:" + e.getMessage());
                                Log.e("xutils=>", "----------End----------");
                            }
                        });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Observable.just("")
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(@NonNull String res) throws Exception {
                                callback.onFinish();
                            }
                        });
            }
        });
    }

    /**
     * form表单提交方式get
     * @param entity
     * @param callback
     */
    public static void get(final RequestParams entity, final HttpCallback callback) {
        Observable.just("")
                .subscribeOn(Schedulers.io())//子线程查询数据
                .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String res) throws Exception {
                        callback.onStart();
                    }
                });
        String param = "";
        if (entity != null) {
            if (entity.getStringParams().size() > 0) {
                for (KeyValue keyValue : entity.getStringParams()) {
                    param += "&" + keyValue.key + "=" + keyValue.value;
                }
            }
            if (StringUtils.isNotBlank(param)) {
                param = param.replaceFirst("&", "?");
            }
        }
        Log.e("xutils=>", "---------Start---------");
        Log.e("xutils=>", "URL: " + entity.getUri() + ":参数:" + param);
        if (entity != null) {
            entity.setConnectTimeout(connectTimeOut);
        }
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Observable.just(result)
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                callback.onSuccess(s);
                                Log.e("xutils=>", "URL: " + entity.getUri() + ":返回:" + s);
                                Log.e("xutils=>", "----------End----------");
                            }
                        });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Observable.just(ex)
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable e) throws Exception {
                                callback.onFailure(e);
                                Log.e("xutils=>", "URL: " + entity.getUri() + ":返回:" + e.getMessage());
                                Log.e("xutils=>", "----------End----------");
                            }
                        });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Observable.just("")
                        .subscribeOn(Schedulers.io())//子线程查询数据
                        .observeOn(AndroidSchedulers.mainThread())//主线程更新ui
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(@NonNull String res) throws Exception {
                                callback.onFinish();
                            }
                        });
            }
        });
    }
}