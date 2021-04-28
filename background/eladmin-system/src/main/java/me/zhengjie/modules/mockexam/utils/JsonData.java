package me.zhengjie.modules.mockexam.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description:
 * @Telephone :      15135964789
 * @createDate :     2021/1/22 17:38
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/1/22 17:38
 * @updateRemark :   修改内容
 **/
@Data
@AllArgsConstructor //会⽣成⼀个包含所有变量
@NoArgsConstructor //⽣成⼀个⽆参数的构造⽅法
public class JsonData {
    /**
     * 状态码 0 表示成功，1表示处理中，-1表示失败
     */
    private Integer code;
    /**
     * 数据
     */
    private Object data;
    /**
     * 描述
     */
    private String msg;

    // 成功，传⼊数据
    public static JsonData buildSuccess() {
        return new JsonData(0, null, null);
    }

    // 成功，传⼊数据
    public static JsonData
    buildSuccess(Object data) {
        return new JsonData(0, data, null);
    }

    // 失败，传⼊描述信息
    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg);
    }

    // 失败，传⼊描述信息,状态码
    public static JsonData buildError(String
                                              msg, Integer code) {
        return new JsonData(code, null, msg);
    }
}
