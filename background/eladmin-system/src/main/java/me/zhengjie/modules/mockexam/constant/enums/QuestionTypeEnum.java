package me.zhengjie.modules.mockexam.constant.enums;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 驾考题类型
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 15:30
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 15:30
 * @updateRemark :   修改内容
 **/
public enum QuestionTypeEnum {


    //小车手动挡
    C1(1, "c1"),
    //小车自动挡
    C2(2, "c2"),
    //货车
    B2(3, "b2"),
    //客车
    A1(4, "a1");
    private Integer code;

    private String msg;

    QuestionTypeEnum(Integer code, String msg) {
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
        QuestionTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getCode().equals(value)) {
                return crc[i].getMsg();
            }
        }
        return null;
    }
    public static Integer getEnumCode(String value) {
        QuestionTypeEnum[] crc = values();
        for (int i = 0; i < crc.length; ++i) {
            if (crc[i].getMsg().equals(value)) {
                return crc[i].getCode();
            }
        }
        return null;
    }
}
