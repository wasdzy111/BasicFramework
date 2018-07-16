package cn.mrlong.basicframework.utils;

/**
 * Created by ly343 on 2018/4/25.
 */
public class StringUtils {

    public static boolean isBlank(String str) {
        return (null == str || str.trim().length() == 0 || "".equals(str));
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0 || "".equals(str));
    }

    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }


    public static boolean isEquals(Object actual, String expected) {
        if ((actual + "").equals(expected)) {
            return true;
        } else {
            return false;
        }
    }

    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String) str : str.toString()));
    }
}