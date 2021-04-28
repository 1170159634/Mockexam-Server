package me.zhengjie.modules.mockexam.constant.enums;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 获取试题类型
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 17:39
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 17:39
 * @updateRemark :   修改内容
 **/
public enum ModelTypeEnum {

    //顺序
    ORDER(1, "order"),
    //随机
    RANDOM(2, "random");
    private Integer code;

    private String msg;

    ModelTypeEnum(Integer code, String msg) {
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

    public static String getEnumMessage(Integer value) {
        ModelTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(value)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }
    public static Integer getEnumCode(String value) {
        ModelTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }
}
