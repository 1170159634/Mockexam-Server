package me.zhengjie.modules.mockexam.constant.enums;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 车类型
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 15:21
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 15:21
 * @updateRemark :   修改内容
 **/

public enum CarTypeEnum {

    //小车
    CAR(1, "小车"),
    //货车
    TRUCK(2, "货车"),
    //客车
    PASSENGER(3, "客车");


    private Integer code;

    private String msg;

    CarTypeEnum(Integer code, String msg) {
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
        CarTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(value)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }

    public static Integer getEnumCode(String value) {
        CarTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }
}
