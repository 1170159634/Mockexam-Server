package me.zhengjie.modules.mockexam.utils;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 交互管理模块附件处理工具类
 * @Telephone :      15135964789
 * @createDate :     2020/8/19 12:30
 * @updateUser :     Mingxuan_x
 * @updateDate :     2020/8/19 12:30
 * @updateRemark :   修改内容
 **/
@Slf4j
public class DockingFileUtils {


    /**
     * @param fileName: 目录名称
     * @Description: 删除并重新创建目录
     * @return: void
     * @Author: Mingxuan_X
     * @Date: 2020/8/28
     */
    public static void rebuildFileDirectory(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return;
        }
        if (FileUtil.exist(fileName)) {
            FileUtil.del(fileName);
        }
        FileUtil.mkdir(fileName);
    }


    /**
     * @param fileName: 目录名称
     * @Description: 判断文件是否存在，如果没有存在测重新创建
     * @return: void
     * @Author: Mingxuan_X
     * @Date: 2020/8/28
     */
    public static void judgeFileDirectoryWithMkdir(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return;
        }
        if (!FileUtil.exist(fileName)) {
            FileUtil.mkdir(fileName);
        }

    }

    /**
     * @param fileName: 删除文件
     * @Description: 判断文件是否存在，如果没有存在测重新创建
     * @return: void
     * @Author: Mingxuan_X
     * @Date: 2020/10/13
     */
    public static boolean deleteFile(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        if (FileUtil.exist(fileName)) {
            FileUtil.del(fileName);
            return true;
        }
        return false;

    }

    /**
     * @param saveUrl: 目录名称数组
     * @Description: 删除并重新创建目录（按照数组）
     * @return: void
     * @Author: Mingxuan_X
     * @Date: 2020/8/28
     */
    public static void rebuildFileDirectoryByArray(String[] saveUrl) {
        if (saveUrl.length == 0) {
            return;
        }
        for (String s : saveUrl) {
            rebuildFileDirectory(s);
        }
    }

    /**
     * @param fileName: 文件名称
     * @Description: 删除并重新创建文件
     * @return: void
     * @Author: Mingxuan_X
     * @Date: 2020/8/28
     */
    public static void rebuildFile(String fileName) {
        if (FileUtil.exist(fileName)) {
            FileUtil.del(fileName);
        }
        FileUtil.touch(fileName);
    }

    /**
     * @param map       附件信息  key: 服务器文件存储路径 value:用户自定义文件名称
     * @param uploadUrl 服务器上传文件路径
     * @param saveUrl   保存文件地址
     * @Description: 下载所有附件到服务器一个目录下
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2020/8/19
     */
    public static void copyFileToDesc(Map<String, String> map, String uploadUrl, String saveUrl) {
        if (map == null || map.size() == 0) {
            return;
        }
        Set<String> strings = map.keySet();
        StringBuilder uploadBuilder = new StringBuilder();
        StringBuilder saveBuilder = new StringBuilder();
        for (String key : strings) {
            // 附件 服务器存储路径
            uploadBuilder.append(uploadUrl);
            uploadBuilder.append(key);
            //需要 copy到的路径
            saveBuilder.append(saveUrl);
            String filename = getNameFromFileUrl(key, map.get(key));
            saveBuilder.append(filename);
            //源文件，目标文件 ，是否覆盖
            try {
                if (FileUtil.exist(uploadBuilder.toString())) {
                    FileUtil.copy(uploadBuilder.toString(), saveBuilder.toString(), true);
                }
            } catch (Exception e) {
                log.error("copy文件时失败！", e);
            } finally {
                uploadBuilder.setLength(0);
                saveBuilder.setLength(0);
            }


        }

    }


    /**
     * @param map       附件信息  key: 服务器文件存储路径 value:用户自定义文件名称
     * @param uploadUrl 服务器上传文件路径
     * @param saveUrl   保存文件地址
     * @Description: uploadUrl+fileUrl(map.key)   copy附件到    saveUrl+fileName(map.value)
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2020/9/18
     */
    public static void copyTopicFileToDesc(Map<String, String> map, String uploadUrl, String saveUrl) {
        if (map == null || map.size() == 0) {
            return;
        }
        Set<String> strings = map.keySet();
        StringBuilder uploadBuilder = new StringBuilder();
        StringBuilder saveBuilder = new StringBuilder();
        for (String key : strings) {
            // 附件 服务器存储路径
            uploadBuilder.append(uploadUrl);
            uploadBuilder.append(key);
            //需要 copy到的路径
            saveBuilder.append(saveUrl);
            String filename = map.get(key);
            saveBuilder.append("/");
            saveBuilder.append(filename);
            //源文件，目标文件 ，是否覆盖
            try {
                if (FileUtil.exist(uploadBuilder.toString())) {
                    FileUtil.copy(uploadBuilder.toString(), saveBuilder.toString(), true);
                }
            } catch (Exception e) {
                log.error("copy文件时失败！", e);
            } finally {
                uploadBuilder.setLength(0);
                saveBuilder.setLength(0);
            }


        }

    }

    /**
     * 附件名称： 路径/服务器存储文件名称 =》  路径/用户上传时的文件名称
     *
     * @param key
     * @param s
     * @return
     */
    public static String getNameFromFileUrl(String key, String s) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(s)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        String[] split = key.split("/");
        for (int c = 0; c <= split.length - 2; c++) {
            builder.append(split[c]);
            if (c <= split.length - 2) {
                builder.append("/");
            }
        }
        builder.append(redressFileName(s));
        return builder.toString();

    }

    /**
     * @param: FileName
     * @Description: 去掉文件路径，只保留文件名称
     * @return: s
     * @Author: Mingxuan_X
     * @Date: 2020/8/28
     */
    public static String redressFileName(String s) {
        if (StringUtils.isBlank(s)) {
            return "";
        }
        if (s.contains("\\")) {
            String[] split1 = s.split("\\\\");
            s = split1[split1.length - 1];
        } else if (s.contains("/")) {
            String[] split1 = s.split("/");
            s = split1[split1.length - 1];
        }
        return s;
    }




}
