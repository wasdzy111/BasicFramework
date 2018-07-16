package cn.mrlong.basicframework.utils.net;

/**
 * Created by BINGO on 2017/08/24.
 */

public interface HttpCallback {
    /**
     * @param result 回调参数
     */
    abstract void onSuccess(String result);

    /**
     * 回调标记位
     */
    abstract void onFailure(Throwable e);

    /**
     * 回调标记位
     */
    abstract void onStart();

    /**
     * 完成标记位
     */
    abstract void onFinish();
}
