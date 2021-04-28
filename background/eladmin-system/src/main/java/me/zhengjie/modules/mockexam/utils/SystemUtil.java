package me.zhengjie.modules.mockexam.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 系统工具类
 * @Telephone :      15135964789
 * @createDate :     2021/4/11 14:38
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/11 14:38
 * @updateRemark :   修改内容
 **/
@Component
public class SystemUtil {

    @Value("${fdfs.localPath.windows}")
    private String windowsPath;


    @Value("${fdfs.localPath.linux}")
    private String linuxPath;


    private final String LINUX = "linux";
    private final String WINDOWS = "windows";

    /**
     * 获取文件存储路径
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/11
     */

    public String getFilePrefix() {
        String s = null;
        //判断操作系统环境
        String environment = System.getProperty("os.name").toLowerCase();
        if (environment.contains(LINUX)) {
            s = linuxPath;
        } else if (environment.contains(WINDOWS)) {
            s = windowsPath;
        }
        return s;
    }


}
