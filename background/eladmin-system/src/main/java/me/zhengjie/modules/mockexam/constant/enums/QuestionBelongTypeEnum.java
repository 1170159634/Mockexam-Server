package me.zhengjie.modules.mockexam.constant.enums;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 试题所属车类型
 * @Telephone :      15135964789
 * @createDate :     2021/4/11 13:18
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/11 13:18
 * @updateRemark :   修改内容
 **/
public enum QuestionBelongTypeEnum {


    //小车
    CAR1(CarTypeEnum.CAR.getCode(), QuestionTypeEnum.C1.getMsg()),
    CAR2(CarTypeEnum.CAR.getCode(), QuestionTypeEnum.C2.getMsg()),
    //货车
    TRUCK(CarTypeEnum.TRUCK.getCode(), QuestionTypeEnum.B2.getMsg()),
    //客车
    PASSENGER(CarTypeEnum.PASSENGER.getCode(), QuestionTypeEnum.A1.getMsg());

    private Integer code;

    private String msg;

    QuestionBelongTypeEnum(Integer code, String msg) {
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
        QuestionBelongTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(value)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }

    public static Integer getEnumCode(String value) {
        QuestionBelongTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }
}
