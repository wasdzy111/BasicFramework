package cn.mrlong.basicframework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * SharedPreferences工具类 可以操作 String, boolean, Integer三种类型
 */
public class SharedPreferencesUtils {
    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context, String filename) {
        SharedPreferences sp = context.getSharedPreferences(filename,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {

            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    /**
     * 获取一个字符串
     *
     * @param context
     * @param filename
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String filename,
                                   String key, String defValue) {
        Object object = getObject(context, filename, key, defValue);
        if (object != null) {
            return (String) object;
        }
        return defValue;

    }

    public static int getInt(Context context, String filename, String key,
                             int defValue) {
        Object object = getObject(context, filename, key, defValue);
        if (object != null) {
            return (Integer) object;
        }
        return defValue;

    }

    /**
     * 获取一个boolean类型的数据
     *
     * @param context
     * @param filename
     * @param key
     * @param defValue
     * @return
     */
    public static Boolean getBoolean(Context context, String filename,
                                     String key, Boolean defValue) {
        Object object = getObject(context, filename, key, defValue);
        if (object != null) {
            return (Boolean) object;
        }
        return defValue;
    }

    /**
     * 获取 Boolean, String 类型的数据
     *
     * @param context  接收一个上下文
     * @param filename 接收一个文件名
     * @param key      key
     * @param defValue value
     * @return
     */
    public static Object getObject(Context context, String filename,
                                   String key, Object defValue) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                filename, Context.MODE_PRIVATE);

        if (defValue instanceof String || defValue == null) {
            return sharedPreferences.getString(key, (String) defValue);
        }
        if (defValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        }
        if (defValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        }

        return null;
    }

    /**
     * 存储String ，Boolean类型的数据
     *
     * @param context  接收一个上下文
     * @param filename 文件名
     * @param key      key
     * @param defValue value
     */
    public static void putObject(Context context, String filename, String key,
                                 Object defValue) {
        if (defValue==null){
            defValue="";
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                filename, Context.MODE_PRIVATE);

        if (defValue instanceof String) {
            sharedPreferences.edit().putString(key, (String) defValue).commit();
        }
        if (defValue instanceof Boolean) {
            sharedPreferences.edit().putBoolean(key, (Boolean) defValue)
                    .commit();
        }
        if (defValue instanceof Integer) {
            sharedPreferences.edit().putInt(key, (Integer) defValue).commit();
        }
    }

    public static long getLong(Context context, String filename, String key,
                               Long defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                filename, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defValue);

    }

    public static void putLong(Context context, String filename, String key,
                               Long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                filename, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).commit();
    }
}
