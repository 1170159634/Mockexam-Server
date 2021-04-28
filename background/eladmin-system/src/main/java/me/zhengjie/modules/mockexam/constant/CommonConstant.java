package me.zhengjie.modules.mockexam.constant;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 通用常量
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 15:32
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 15:32
 * @updateRemark :   修改内容
 **/
public class CommonConstant {


    public static final Integer DEFAULT_COUNT = 0;

    private static final Integer QUERY_PAGE = 1;

    private static final Integer QUERY_SIZE = 20;


    /**
     * 给用户推荐题阀值
     */
    public static final Integer RECOMMEND_TOPIC_COUNT = 50;

    /**
     * 题型推荐因子
     */
    public static final Double LOAD_FACTOR_PERCENTAGE = 0.50d;


    /**
     * 默认查询构造
     *
     * @return:
     * @Author: Mingxuan_X
     * @Date: 2021/4/12
     */
    public static Page<?> queryLimitBuild(Integer page, Integer size) {
        if (null == page || null == size) {
            page = QUERY_PAGE;
            size = QUERY_SIZE;
        }
        return new Page<>(page, size);
    }

    public static Long getUniqueId() {
        return Long.parseLong(IdWorker.getIdStr().substring(6, 19));
    }


}
