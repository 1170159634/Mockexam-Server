package me.zhengjie;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description:
 * @Telephone :      15135964789
 * @createDate :     2021/4/11 11:55
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/11 11:55
 * @updateRemark :   修改内容
 **/
public class testUtil {
    public static void main(String[] args) {
        ArrayList<Long> list = new ArrayList<Long>();
        for(int i=0;i<90000;i++)
        {
            list.add(1L);
            list.add(2L);
            list.add(3L);
            list.add(4L);
            list.add(5L);
        }

        Collections.shuffle(list);
        System.out.println(list.toString());

    }
}
