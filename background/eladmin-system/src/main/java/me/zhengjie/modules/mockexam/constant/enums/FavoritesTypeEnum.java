package me.zhengjie.modules.mockexam.constant.enums;

import io.swagger.models.auth.In;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 用户收藏/取消收藏问题
 * @Telephone :      15135964789
 * @createDate :     2021/4/12 13:47
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/12 13:47
 * @updateRemark :   修改内容
 **/
public enum FavoritesTypeEnum {


    CONFIRM(0, "确认收藏"),
    CANCEL(1, "取消收藏");
    private Integer code;

    private String msg;

    FavoritesTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getEnumMessage(String value) {
        FavoritesTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(value)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }

    public static Integer getEnumCode(String value) {
        FavoritesTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }
}
