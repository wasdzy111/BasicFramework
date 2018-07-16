package cn.mrlong.basicframework.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BINGO on 2018/07/13.
 */

public class ProjectPathUtil {
    public boolean creatFilePath(String packageName, boolean isGreendao) {

        if (packageName.length() == 0) {
            return false;
        }
        packageName = packageName.replace(".", "/");
        String rootUrl = "app/src/main/java/" + packageName;

        List<String> pathList = new ArrayList<>();
        pathList.add("/utils");
        if (isGreendao)
            pathList.add("/greendao");
        pathList.add("/ui");
        pathList.add("/ui/bean");
        pathList.add("/ui/main");
        pathList.add("/ui/main/adapter");
        pathList.add("/ui/main/ipresenter");
        pathList.add("/ui/main/presenter");
        pathList.add("/ui/main/iview");
        pathList.add("/ui/main/view");
        pathList.add("/ui/receiver");
        pathList.add("/ui/service");
        for (String path : pathList) {
            File file = new File(rootUrl + path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return true;
    }


}
